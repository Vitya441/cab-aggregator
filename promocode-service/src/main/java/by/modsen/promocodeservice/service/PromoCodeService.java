package by.modsen.promocodeservice.service;

import by.modsen.promocodeservice.dto.PaginatedResponse;
import by.modsen.promocodeservice.dto.PromoCodeRequest;
import by.modsen.promocodeservice.dto.PromoCodeResponse;
import by.modsen.promocodeservice.dto.PromoCodeResponseList;

public interface PromoCodeService {

    PromoCodeResponse getById(String id);

    PromoCodeResponse getByCode(String code);

    PromoCodeResponseList getAll();

    PaginatedResponse getAllPaginated(int pageNumber, int pageSize, String sortField);

    PromoCodeResponse create(PromoCodeRequest promoCodeRequest);

    PromoCodeResponse update(String id, PromoCodeRequest promoCodeRequest);

    void deleteById(String id);

    PromoCodeResponse applyPromoCode(String code);
}