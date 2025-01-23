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
}