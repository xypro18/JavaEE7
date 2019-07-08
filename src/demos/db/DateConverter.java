/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demos.db;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Administrator
 */
@Converter( autoApply = true )
public class DateConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn( LocalDate value ) {
		return ( value == null ) ? null : Date.from( value.atStartOfDay( ZoneId.systemDefault() ).toInstant() );
	}

	@Override
	public LocalDate convertToEntityAttribute( Date value ) {
		return ( value == null ) ? null : Instant.ofEpochMilli( value.getTime() ).atZone( ZoneId.systemDefault() ).toLocalDate();
	}

}
