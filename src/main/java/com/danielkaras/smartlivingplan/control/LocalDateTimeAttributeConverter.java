package com.danielkaras.smartlivingplan.control;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date dbData) {
        return dbData == null ? null : dbData.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
