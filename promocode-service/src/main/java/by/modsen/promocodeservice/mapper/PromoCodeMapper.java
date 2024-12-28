package by.modsen.promocodeservice.mapper;

import by.modsen.promocodeservice.dto.PromoCodeRequest;
import by.modsen.promocodeservice.dto.PromoCodeResponse;
import by.modsen.promocodeservice.entity.PromoCode;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PromoCodeMapper {

    PromoCode toEntity(PromoCodeRequest promoCodeRequest);

    PromoCodeResponse toDto(PromoCode promoCode);

    List<PromoCodeResponse> toDtoList(List<PromoCode> promoCodes);

    void updateEntityFromDto(PromoCodeRequest promoCodeRequest, @MappingTarget PromoCode promoCode);

}