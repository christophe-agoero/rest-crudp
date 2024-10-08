package fr.agoero.pro.controller.util;

import fr.agoero.pro.exception.common.CustomErrorResponseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;

import static fr.agoero.pro.exception.common.ErrorResponseExceptionEnum.INVALID_PAGINATION_ATTRIBUT;
import static fr.agoero.pro.exception.common.ErrorResponseExceptionEnum.INVALID_PAGINATION_VALUE;
import static fr.agoero.pro.exception.common.InvalidPaginationExceptionConstants.INVALID_PAGINATION_ATTRIBUT_DETAIL;
import static fr.agoero.pro.exception.common.InvalidPaginationExceptionConstants.INVALID_PAGINATION_VALUE_DETAIL;


/**
 * Classe utilitaire pour page
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageUtil {

    // from PageableHandlerMethodArgumentResolverSupport
    private static final String DEFAULT_PAGE_PARAMETER = "page";
    private static final String DEFAULT_SIZE_PARAMETER = "size";
    // from SortHandlerMethodArgumentResolverSupport
    private static final String DEFAULT_PARAMETER = "sort";

    private static final List<String> PAGE_PARAMETER_LIST = List.of(DEFAULT_PAGE_PARAMETER, DEFAULT_SIZE_PARAMETER, DEFAULT_PARAMETER);

    public static boolean isPageParameter(String parameter) {
        return PAGE_PARAMETER_LIST.contains(parameter);
    }

    /**
     * Permet de vérifier les attributs de tri
     *
     * @param sort
     * @param aClass
     * @param attributeList
     * @throws CustomErrorResponseException si les attributs de pagination de tri sont invalides
     */
    public static void checkSortAttribut(Sort sort, Class<?> aClass, List<String> attributeList) {
        sort.stream().iterator().forEachRemaining(order -> {
            if (!attributeList.contains(order.getProperty())) {
                throw new CustomErrorResponseException(
                        INVALID_PAGINATION_ATTRIBUT,
                        String.format(
                                INVALID_PAGINATION_ATTRIBUT_DETAIL,
                                order.getProperty(),
                                aClass.getSimpleName()
                        ));
            }
        });
    }

    /**
     * Permet de vérifier et de renvoyer la valeur des attributs de filtre
     *
     * @param attribute
     * @param value
     * @param toClassFunction
     * @param entityClass
     * @param valueClass
     * @param <T>
     * @return
     * @throws CustomErrorResponseException si la valeur est invalide
     */
    public static <T> T checkValueAndGet(String attribute, String value, StringToClassFunction<T> toClassFunction,
                                         Class<?> entityClass, Class<?> valueClass) {
        try {
            return toClassFunction.build(value);
        } catch (Exception e) {
            throw new CustomErrorResponseException(
                    INVALID_PAGINATION_VALUE,
                    String.format(
                            INVALID_PAGINATION_VALUE_DETAIL,
                            attribute,
                            entityClass.getSimpleName(),
                            valueClass.getSimpleName(),
                            value

                    ));
        }
    }

    public static Long stringToLong(String s) {
        return "null".equals(s) ? null : Long.valueOf(s);
    }

    public static long stringToLongPrimitive(String s) {
        return Long.parseLong(s);
    }

    public static int stringToIntPrimitive(String s) {
        return Integer.parseInt(s);
    }

    public static boolean stringToBoolean(String s) {
        return Boolean.parseBoolean(s);
    }

}
