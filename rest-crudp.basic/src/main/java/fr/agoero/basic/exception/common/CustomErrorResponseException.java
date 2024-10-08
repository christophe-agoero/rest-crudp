package fr.agoero.basic.exception.common;

import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import static fr.agoero.basic.exception.common.CustomErrorResponseUtil.addTimestamp;


/**
 * Exception custom qui extends ErrorResponseException
 * Pas besoin de définir un @ExceptionHandler dans GlobalExceptionHandler
 */
public class CustomErrorResponseException extends ErrorResponseException {

    public CustomErrorResponseException(ErrorResponseExceptionEnum errorResponseExceptionEnum, String detail) {
        super(errorResponseExceptionEnum.getHttpStatus(), buildProblemDetail(errorResponseExceptionEnum, detail), null);
    }

    private static ProblemDetail buildProblemDetail(ErrorResponseExceptionEnum errorResponseExceptionEnum, String detail) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                errorResponseExceptionEnum.getHttpStatus(),
                detail
        );
        problemDetail.setTitle(errorResponseExceptionEnum.getTitle());
        if (errorResponseExceptionEnum.getType() != null) {
            problemDetail.setType(errorResponseExceptionEnum.getType());
        }
        addTimestamp(problemDetail);
        return problemDetail;
    }

}
