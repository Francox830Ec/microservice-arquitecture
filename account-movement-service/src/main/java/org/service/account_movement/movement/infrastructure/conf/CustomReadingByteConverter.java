package org.service.account_movement.movement.infrastructure.conf;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.sql.Timestamp;

@Component
@WritingConverter
public class CustomReadingByteConverter implements Converter<Timestamp, byte[]> {
    @Override
    public byte[] convert(Timestamp source) {
        long timestamp = source.getTime();
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(timestamp);
        return buffer.array();
    }
}
