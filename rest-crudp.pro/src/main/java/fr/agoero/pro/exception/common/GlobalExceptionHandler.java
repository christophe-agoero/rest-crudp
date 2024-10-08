package fr.agoero.pro.exception.common;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

import static fr.agoero.pro.exception.common.CustomErrorResponseUtil.*;
import static fr.agoero.pro.exception.common.ExceptionConstants.*;
import static fr.agoero.pro.util.ValidationUtil.*;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String EMPTY_BODY_MESSAGE = "body : ne doit pas être vide";
    private static final String INVALID_FORMAT_MESSAGE = "%s : doit être du type %s";
    private static final String ARGUMENT_NOT_VALID_MESSAGE = PREFIX + "%s" + CONCAT + "%s" + SUFFIX;
    private static final String REQUIRED_REQUEST_PARAM_MESSAGE = "Le paramètre de requête obligatoire %s n'est pas présent";

    private static final URI UNKNOWN_TYPE = URI.create(SUFFIX_TYPE + "unknown");
    private static final String UNKNOWN_TITLE = "UNKNOWN";


    /**
     * Handler de l'exception {@link MissingServletRequestParameterException}
     *
     * @param missingServletRequestParameterException
     * @param headers
     * @param status
     * @param request
     * @return ResponseEntity
     */
    @Override
    @SuppressWarnings("NullableProblems")
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException missingServletRequestParameterException,
                                                                          HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var responseEntity = super.handleMissingServletRequestParameter(missingServletRequestParameterException, headers, status, request);
        try {
            if (responseEntity != null) {
                var responseBody = responseEntity.getBody();
                if (responseBody instanceof ProblemDetail problemDetail) {
                    addTimestamp(problemDetail);
                    var requiredRequestParam = missingServletRequestParameterException.getParameterName();
                    if (isNotBlank(requiredRequestParam)) {
                        problemDetail.setDetail(String.format(REQUIRED_REQUEST_PARAM_MESSAGE, requiredRequestParam));
                    }
                }
            }
        } catch (Exception e) {
            // on garde l'original
            log.warn("Error during handleMissingServletRequestParameter", e);
        }
        return responseEntity;
    }

    /**
     * Handler de l'exception {@link MethodArgumentNotValidException}
     *
     * @param methodArgumentNotValidException
     * @param headers
     * @param status
     * @param request
     * @return ResponseEntity
     */
    @Override
    @SuppressWarnings("NullableProblems")
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var responseEntity = super.handleMethodArgumentNotValid(methodArgumentNotValidException, headers, status, request);
        try {
            if (responseEntity != null) {
                var responseBody = responseEntity.getBody();
                if (responseBody instanceof ProblemDetail problemDetail) {
                    addTimestamp(problemDetail);
                    var fieldError = methodArgumentNotValidException.getFieldError();
                    if (fieldError != null) {
                        problemDetail.setDetail(buildArgumentNotValidMessage(fieldError, problemDetail.getDetail(),
                                methodArgumentNotValidException.getBindingResult()));
                        problemDetail.setType(buildConstraintViolationType(fieldError.getObjectName()));
                        problemDetail.setTitle(buildConstraintViolationTitle(fieldError.getObjectName()));
                    }
                }
            }
        } catch (Exception e) {
            // on garde l'original
            log.warn("Error during handleMethodArgumentNotValid", e);
        }
        return responseEntity;
    }

    /**
     * Handler  de l'exception {@link HttpMessageNotReadableException}
     *
     * @param httpMessageNotReadableException
     * @param headers
     * @param status
     * @param request
     * @return ResponseEntity
     */
    @Override
    @SuppressWarnings("NullableProblems")
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException httpMessageNotReadableException,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var responseEntity = super.handleHttpMessageNotReadable(httpMessageNotReadableException, headers, status, request);
        try {
            if (responseEntity != null) {
                var responseBody = responseEntity.getBody();
                if (responseBody instanceof ProblemDetail problemDetail) {
                    addTimestamp(problemDetail);
                    if (httpMessageNotReadableException.getMessage().contains("body is missing")) {
                        problemDetail.setDetail(EMPTY_BODY_MESSAGE);
                    } else if (httpMessageNotReadableException.getCause() instanceof InvalidFormatException invalidFormatException) {
                        problemDetail.setDetail(buildInvalidFormatMessage(invalidFormatException, problemDetail.getDetail()));
                    } else if (httpMessageNotReadableException.getCause() instanceof JsonParseException jsonParseException
                            && (isNotBlank(jsonParseException.getOriginalMessage()))) {
                        problemDetail.setDetail(jsonParseException.getOriginalMessage());

                    }
                }
            }
        } catch (Exception e) {
            // on garde l'original
            log.warn("Error during handleHttpMessageNotReadable", e);
        }
        return responseEntity;
    }

    /**
     * Handler  de l'exception {@link ConstraintViolationException}
     *
     * @param constraintViolationException
     * @return ErrorResponse
     */
    @ExceptionHandler(ConstraintViolationException.class)
    ErrorResponse handleConstraintViolationException(ConstraintViolationException constraintViolationException,
                                                     WebRequest request) {
        var detail = BAD_REQUEST.getReasonPhrase();
        try {
            detail = formattedStringNotParameterized(constraintViolationException.getConstraintViolations());
        } catch (Exception e) {
            // on garde l'original
            log.warn("Error during handleConstraintViolationException", e);
        }
        return ErrorResponse.builder(constraintViolationException, BAD_REQUEST, detail)
                            .property(PROBLEM_DETAIL_TIMESTAMP_PROPERTY, getTimestamp())
                            .header(CONTENT_TYPE, APPLICATION_PROBLEM_JSON_VALUE)
                            .build();
    }


    /**
     * Handler de l'exception {@link Exception} ou des exceptions sans handler
     *
     * @param e
     * @return ErrorResponse
     */
    @ExceptionHandler(Exception.class)
    ErrorResponse handleOtherException(Exception e, WebRequest request) {
        log.warn("handleException", e);
        return ErrorResponse.builder(e, INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .title(UNKNOWN_TITLE)
                            .type(UNKNOWN_TYPE)
                            .property(PROBLEM_DETAIL_TIMESTAMP_PROPERTY, getTimestamp())
                            .header(CONTENT_TYPE, APPLICATION_PROBLEM_JSON_VALUE)
                            .build();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.addAll(headers);
        httpHeaders.add(CONTENT_TYPE, APPLICATION_PROBLEM_JSON_VALUE);
        return super.handleExceptionInternal(ex, body, httpHeaders, statusCode, request);
    }

    /**
     * Permet de construire un message de type TYPE_MISMATCH_MESSAGE depuis InvalidFormatException
     *
     * @param invalidFormatException
     * @param originalMsg
     * @return un message de type TYPE_MISMATCH_MESSAGE
     */
    private String buildInvalidFormatMessage(InvalidFormatException invalidFormatException, String originalMsg) {
        if (invalidFormatException.getTargetType() != null
                && isNotEmpty(invalidFormatException.getPath())
                && isNotBlank(invalidFormatException.getPath().get(0).getFieldName())
        ) {
            originalMsg = String.format(
                    INVALID_FORMAT_MESSAGE,
                    invalidFormatException.getPath().get(0).getFieldName(),
                    invalidFormatException.getTargetType().getSimpleName()
            );
        }
        return originalMsg;
    }


    /**
     * Permet de construire un message de type ARGUMENT_NOT_VALID_MESSAGE depuis MethodArgumentNotValidException
     *
     * @param fieldError
     * @param originalMsg
     * @param bindingResult
     * @return un message de type ARGUMENT_NOT_VALID_MESSAGE
     */
    @SuppressWarnings("squid:S2259")
    private String buildArgumentNotValidMessage(FieldError fieldError, String originalMsg, BindingResult bindingResult) {
        if (fieldError != null
                && isNotBlank(fieldError.getField())
                && isNotBlank(fieldError.getDefaultMessage())
        ) {
            var attributName = fieldError.getField();
            if (bindingResult != null && bindingResult.getTarget() != null && bindingResult.getTarget().getClass() != null) {
                attributName = getAnnotationNameIfExist(attributName, bindingResult.getTarget().getClass());
            }
            originalMsg = String.format(
                    ARGUMENT_NOT_VALID_MESSAGE,
                    attributName,
                    fieldError.getDefaultMessage()
            );
        }
        return originalMsg;
    }

}
