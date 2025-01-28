package by.modsen.authservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InboxStatus {
    PROCESSED(1),
    FAILED(2),
    PENDING(0);

    private final Integer code;

    public static InboxStatus fromCode(Integer code) {
        if (code == null) {
            throw new IllegalArgumentException("InboxStatus code can't be null");
        }
        for (InboxStatus status : InboxStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException(
                String.format("Failed to convert code '%s' to an Enum value for InboxStatus. Please check the database values or possible changes to the Enum definition.", code)
        );
    }
}