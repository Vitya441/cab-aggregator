package by.modsen.promocodeservice.service.impl;

import by.modsen.promocodeservice.dto.PaginatedResponse;
import by.modsen.promocodeservice.dto.PromoCodeRequest;
import by.modsen.promocodeservice.dto.PromoCodeResponse;
import by.modsen.promocodeservice.dto.PromoCodeResponseList;
import by.modsen.promocodeservice.entity.PromoCode;
import by.modsen.promocodeservice.mapper.PromoCodeMapper;
import by.modsen.promocodeservice.repository.PromoCodeRepository;
import by.modsen.promocodeservice.service.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {

    private final PromoCodeRepository repository;
    private final PromoCodeMapper mapper;

    @Override
    public PromoCodeResponse getById(String id) {
        PromoCode promoCode = getPromoCodeByIdOrThrow(id);

        return mapper.toDto(promoCode);
    }

    @Override
    public PromoCodeResponse getByCode(String code) {
        PromoCode promoCode = repository
                .findByCode(code)
                .orElseThrow(() -> new RuntimeException("Promo code not found"));

        return mapper.toDto(promoCode);
    }

    @Override
    public PromoCodeResponseList getAll() {
        List<PromoCode> promoCodes = repository.findAll();
        List<PromoCodeResponse> promoCodeResponses = mapper.toDtoList(promoCodes);

        return new PromoCodeResponseList(promoCodeResponses);
    }

    public PaginatedResponse getAllPaginated(int pageNumber, int pageSize, String sortField) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortField));
        Page<PromoCode> promoCodePage = repository.findAll(pageRequest);
        List<PromoCodeResponse> responseList = mapper.toDtoList(promoCodePage.getContent());

        return PaginatedResponse.builder()
                .promoCodes(responseList)
                .currentPage(promoCodePage.getNumber())
                .totalPages(promoCodePage.getTotalPages())
                .totalElements(promoCodePage.getTotalElements())
                .build();
    }

    @Override
    public PromoCodeResponse create(PromoCodeRequest promoCodeRequest) {
        if (repository.existsByCode(promoCodeRequest.code())) {
            throw new RuntimeException("Promo code with this code already exists");
        }
        PromoCode promoCode = mapper.toEntity(promoCodeRequest);
        promoCode = repository.save(promoCode);

        return mapper.toDto(promoCode);
    }

    @Override
    public PromoCodeResponse update(String id, PromoCodeRequest promoCodeRequest) {
        PromoCode promoCode = getPromoCodeByIdOrThrow(id);
        if (!promoCodeRequest.code().equals(promoCode.getCode())
                && repository.existsByCode(promoCodeRequest.code())) {
            throw new RuntimeException("Promo code with this code already exists");
        }
        mapper.updateEntityFromDto(promoCodeRequest, promoCode);
        promoCode = repository.save(promoCode);

        return mapper.toDto(promoCode);
    }

    @Override
    public void deleteById(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Promo code with this ID does not exist");
        }
        repository.deleteById(id);
    }

    private PromoCode getPromoCodeByIdOrThrow(String id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Promo code not found"));
    }
}