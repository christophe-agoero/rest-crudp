package fr.agoero.pro.exception.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ProblemDetail;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomErrorResponseUtil {

    public static final String PROBLEM_DETAIL_TIMESTAMP_PROPERTY = "timestamp";

    public static void addTimestamp(ProblemDetail problemDetail) {
        problemDetail.setProperty(PROBLEM_DETAIL_TIMESTAMP_PROPERTY, getTimestamp());
    }

    public static String getTimestamp() {
        return ZonedDateTime.now()
                            .withZoneSameInstant(ZoneOffset.UTC)
                            .format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

}
