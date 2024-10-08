package fr.agoero.pro.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.agoero.pro.exception.common.CustomErrorResponseException;
import fr.agoero.pro.exception.common.ErrorResponseExceptionEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ReflectionUtils;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtil {

    public static final String DELIMITER = "; ";
    public static final String PREFIX = "[";
    public static final String SUFFIX = "]";
    public static final String CONCAT = " : ";
    public static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();


    /**
     * Permet de vérifier qu'un objet est valide, throw si invalide
     *
     * @param errorResponseExceptionEnum
     * @param object
     * @throws CustomErrorResponseException
     */
    public static void checkObjectAndThrow(ErrorResponseExceptionEnum errorResponseExceptionEnum, Object object) {
        var constraintViolationSet = VALIDATOR.validate(object);
        if (isNotEmpty(constraintViolationSet)) {
            throw new CustomErrorResponseException(
                    errorResponseExceptionEnum,
                    formattedString(constraintViolationSet)
            );
        }
    }

    /**
     * Permet de vérifier qu'un attribut est valide, throw si invalide
     *
     * @param errorResponseExceptionEnum
     * @param aClass
     * @param attribut
     * @param value
     * @throws CustomErrorResponseException
     */
    public static <T> void checkAttributAndThrow(ErrorResponseExceptionEnum errorResponseExceptionEnum,
                                                 Class<T> aClass, String attribut, Object value) {
        var constraintViolationSet = VALIDATOR.validateValue(aClass, attribut, value);
        if (isNotEmpty(constraintViolationSet)) {
            throw new CustomErrorResponseException(
                    errorResponseExceptionEnum,
                    formattedString(constraintViolationSet)
            );
        }
    }

    /**
     * Permet de formatter le Set d'erreur en un string représentant un tableau
     *
     * @param constraintViolationException
     * @param <T>
     * @return un string représentant un tableau
     */
    public static <T> String formattedString(Set<ConstraintViolation<T>> constraintViolationException) {
        return constraintViolationException
                .stream()
                .map(getConstraintViolationStringFunction())
                .collect(getJoining());
    }

    /**
     * Permet de formatter le Set d'erreur en un string représentant un tableau
     *
     * @param constraintViolationException
     * @return un string représentant un tableau
     */
    public static String formattedStringNotParameterized(Set<ConstraintViolation<?>> constraintViolationException) {
        return constraintViolationException
                .stream()
                .map(getConstraintViolationStringFunction())
                .collect(getJoining());
    }

    public static String getAnnotationNameIfExist(String attributName, Class<?> attributClass) {
        var attribut = ReflectionUtils.findField(attributClass, attributName);
        if (attribut != null && attribut.isAnnotationPresent(JsonProperty.class)) {
            var annotation = attribut.getAnnotation(JsonProperty.class);
            attributName = annotation.value();
        }
        return attributName;
    }

    private static Collector<CharSequence, ?, String> getJoining() {
        return joining(DELIMITER, PREFIX, SUFFIX);
    }

    private static Function<ConstraintViolation<?>, String> getConstraintViolationStringFunction() {
        return propertyConstraintViolation -> {
            var attributName = propertyConstraintViolation.getPropertyPath().toString();
            attributName = getAnnotationNameIfExist(attributName, propertyConstraintViolation.getRootBeanClass());
            return attributName + CONCAT + propertyConstraintViolation.getMessage();
        };
    }

}
