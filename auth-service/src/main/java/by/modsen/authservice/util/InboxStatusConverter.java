package by.modsen.authservice.util;

import by.modsen.authservice.entity.InboxStatus;
import jakarta.persistence.AttributeConverter;

public class InboxStatusConverter implements AttributeConverter<InboxStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(InboxStatus attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public InboxStatus convertToEntityAttribute(Integer dbData) {
        for (InboxStatus inboxStatus : InboxStatus.values()) {
            if (inboxStatus.getCode().equals(dbData)) {
                return inboxStatus;
            }
        }
        throw new IllegalArgumentException("Неизвестный ID:" + dbData);
    }
}