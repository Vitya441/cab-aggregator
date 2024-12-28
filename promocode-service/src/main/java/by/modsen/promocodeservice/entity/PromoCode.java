package by.modsen.promocodeservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "promocode")
@Getter @Setter
public class PromoCode {

    @Id
    private String id;

    private String name;

    private String code;

    private Long count;

    private Double percent;

}