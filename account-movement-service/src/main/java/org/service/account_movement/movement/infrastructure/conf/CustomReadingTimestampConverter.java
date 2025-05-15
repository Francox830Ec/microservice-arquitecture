package org.service.account_movement.movement.infrastructure.conf;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@ReadingConverter
public class CustomReadingTimestampConverter implements Converter<byte[], Timestamp> {
    @Override
    public Timestamp convert(byte[] source) {
        String value = new String(source);
        return new Timestamp(Long.parseLong(value));
    }
}
