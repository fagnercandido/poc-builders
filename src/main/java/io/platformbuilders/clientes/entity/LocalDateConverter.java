package io.platformbuilders.clientes.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        return sqlDate == null ? null : sqlDate.toLocalDate();
    }

    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
        return locDate == null ? null : Date.valueOf(locDate);
    }
 

}
