package by.modsen.promocodeservice.service.impl;

import by.modsen.promocodeservice.dto.PaginatedResponse;
import by.modsen.promocodeservice.dto.PromoCodeRequest;
import by.modsen.promocodeservice.dto.PromoCodeResponse;
import by.modsen.promocodeservice.dto.PromoCodeResponseList;
import by.modsen.promocodeservice.entity.PromoCode;
import by.modsen.promocodeservice.exception.CodeAlreadyExistsException;
import by.modsen.promocodeservice.exception.PromoCodeNotFoundException;
import by.modsen.promocodeservice.mapper.PromoCodeMapper;
import by.modsen.promocodeservice.repository.PromoCodeRepository;
import by.modsen.promocodeservice.service.PromoCodeService;
import by.modsen.promocodeservice.util.ExceptionMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {

    private final PromoCodeRepository repository;
    private final PromoCodeMapper promoCodeMapper;

    @Override
    public PromoCodeResponse getById(String id) {
        PromoCode promoCode = getPromoCodeByIdOrThrow(id);
        log.info("Getting promo code by id: {}", promoCode.getId());
        return promoCodeMapper.toDto(promoCode);
    }

    @Override
    public PromoCodeResponse applyPromoCode(String code) {
        PromoCode promoCode = getPromoCodeByCodeOrThrow(code);
        if (promoCode.getCount() <= 0) {
            throw new RuntimeException("Promo code is no longer available");
        }
        promoCode.setCount(promoCode.getCount() - 1);
        promoCode = repository.save(promoCode);
        log.info("Applying promo code with id: {}", promoCode.getId());

        return promoCodeMapper.toDto(promoCode);
    }

    @Override
    public PromoCodeResponse getByCode(String code) {
        PromoCode promoCode = getPromoCodeByCodeOrThrow(code);
        log.info("Getting promo code by code: {}", promoCode.getCode());
        return promoCodeMapper.toDto(promoCode);
    }

    @Override
    public PromoCodeResponseList getAll() {
        List<PromoCode> promoCodes = repository.findAll();
        List<PromoCodeResponse> promoCodeResponses = promoCodeMapper.toDtoList(promoCodes);

        return new PromoCodeResponseList(promoCodeResponses);
    }

    public PaginatedResponse getAllPaginated(int pageNumber, int pageSize, String sortField) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortField));
        Page<PromoCode> promoCodePage = repository.findAll(pageRequest);
        List<PromoCodeResponse> responseList = promoCodeMapper.toDtoList(promoCodePage.getContent());

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
            throw new CodeAlreadyExistsException(ExceptionMessageConstants.PROMOCODE_WITH_CODE_EXISTS, promoCodeRequest.code());
        }
        PromoCode promoCode = promoCodeMapper.toEntity(promoCodeRequest);
        promoCode = repository.save(promoCode);
        log.info("Creating promo code with id: {}", promoCode.getId());

        return promoCodeMapper.toDto(promoCode);
    }

    @Override
    public PromoCodeResponse update(String id, PromoCodeRequest promoCodeRequest) {
        PromoCode promoCode = getPromoCodeByIdOrThrow(id);
        checkPossibilityToUpdate(promoCode, promoCodeRequest);
        promoCodeMapper.updateEntityFromDto(promoCodeRequest, promoCode);
        promoCode = repository.save(promoCode);
        log.info("Updating promo code with id: {}", promoCode.getId());

        return promoCodeMapper.toDto(promoCode);
    }

    @Override
    public void deleteById(String id) {
        if (!repository.existsById(id)) {
            throw new PromoCodeNotFoundException(ExceptionMessageConstants.PROMOCODE_WITH_ID_NOT_FOUND, id);
        }
        repository.deleteById(id);
        log.info("Deleting promo code with id: {}", id);
    }

    private PromoCode getPromoCodeByIdOrThrow(String id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new PromoCodeNotFoundException(
                        ExceptionMessageConstants.PROMOCODE_WITH_ID_NOT_FOUND, id
                ));
    }

    private PromoCode getPromoCodeByCodeOrThrow(String code) {
        return repository
                .findByCode(code)
                .orElseThrow(() -> new PromoCodeNotFoundException(
                        ExceptionMessageConstants.PROMOCODE_WITH_CODE_NOT_FOUND, code
                ));
    }

    private void checkPossibilityToUpdate(PromoCode promoCode, PromoCodeRequest promoCodeRequest) {
        if (!promoCodeRequest.code().equals(promoCode.getCode())
                && repository.existsByCode(promoCodeRequest.code())) {
            throw new CodeAlreadyExistsException(ExceptionMessageConstants.PROMOCODE_WITH_CODE_EXISTS, promoCodeRequest.code());
        }
    }
}