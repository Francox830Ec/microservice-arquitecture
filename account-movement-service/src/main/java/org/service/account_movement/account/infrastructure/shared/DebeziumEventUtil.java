package org.service.account_movement.account.infrastructure.shared;

import io.debezium.data.Envelope;
import io.debezium.engine.RecordChangeEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.CaseUtils;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.HashMap;
import java.util.Map;

import static io.debezium.data.Envelope.FieldName.*;
import static java.util.stream.Collectors.toMap;

@Log4j2
public class DebeziumEventUtil {
    private Envelope.Operation operation;
    private Map<String, Object> payload = new HashMap<>();

    public DebeziumEventUtil(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        payload().clear();
        init(sourceRecordRecordChangeEvent);
    }

    public Map<String, Object> payload() {
        return payload;
    }

    private void setPayload(Struct struct) {
        payload = struct.schema().fields().stream()
                .map(Field::name)
                .filter(fieldName -> struct.get(fieldName) != null)
                .map(fieldName -> Pair.of(CaseUtils.toCamelCase(fieldName, false, '_'),
                        struct.get(fieldName)))
                .collect(toMap(Pair::getKey, Pair::getValue));
    }

    public Envelope.Operation operation() {
        return operation;
    }

    private void init(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent){
        var sourceRecordChangeValue= (Struct) sourceRecordRecordChangeEvent.record().value();

        if (sourceRecordChangeValue != null) {
            operation = Envelope.Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));
            String record = operation() == Envelope.Operation.DELETE ? BEFORE : AFTER; // Handling Update & Insert Ops
            Struct struct = (Struct) sourceRecordChangeValue.get(record);
            setPayload(struct);

            if(operation() != Envelope.Operation.READ) {
                log.info("***** CDC Payload: {} with Operation: {}", payload(), operation().name());
            }
        }
    }
}
