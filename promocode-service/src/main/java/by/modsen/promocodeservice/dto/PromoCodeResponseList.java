package by.modsen.promocodeservice.dto;

import java.util.List;

public record PromoCodeResponseList(
        List<PromoCodeResponse> promoCodes
) {}