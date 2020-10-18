package xyz.guqing.creek.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * @author guqing
 * @date 2020-07-16
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    /**
     * 构建格式化器，并赋予可选参数默认值，否则会出错
     */
    private static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd[ [HH][:mm][:ss][.SSS]]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

    @Override
    public LocalDateTime convert(@NonNull String s) {
        return LocalDateTime.parse(s, DATE_FORMAT);
    }
}
