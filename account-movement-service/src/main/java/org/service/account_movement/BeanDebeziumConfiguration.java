package org.service.account_movement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;

@Configuration
public class BeanDebeziumConfiguration {
    @Bean
    public io.debezium.config.Configuration accountMovementConnector(Environment env) throws IOException {
        return io.debezium.config.Configuration.create()
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", File.createTempFile("offsets_", ".dat").getAbsolutePath())
                .with("offset.flush.interval.ms", "60000")
                .with("database.hostname", env.getProperty("spring.jpa.properties.hostname"))
                .with("database.port", env.getProperty("spring.jpa.properties.port"))
                .with("database.user", env.getProperty("spring.datasource.username"))
                .with("database.password", env.getProperty("spring.datasource.password"))
                .with("database.dbname", env.getProperty("spring.datasource.name"))
                .with("transforms", "TimestampConverter")
                .with("transforms.TimestampConverter.type", "org.apache.kafka.connect.transforms.TimestampConverter$Value")
                .with("transforms.TimestampConverter.field", "mov_date")
                .with("transforms.TimestampConverter.format", "yyyy-MM-dd HH:mm:ss'Z'")
                .with("transforms.TimestampConverter.target.type", "Timestamp")
                .with("time.precision.mode", "connect")
                .with("transforms", "convertTimezone")
                .with("transforms.convertTimezone.type", "io.debezium.transforms.TimezoneConverter")
                .with("transforms.convertTimezone.converted.timezone", "America/Guayaquil")
                .build();
    }
}
