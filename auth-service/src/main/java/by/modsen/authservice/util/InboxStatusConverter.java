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
        return InboxStatus.fromCode(dbData);
    }
}