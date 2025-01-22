package by.modsen.authservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InboxStatus {
    PENDING(0),
    PROCESSED(1),
    FAILED(2);

    private final int code;
}