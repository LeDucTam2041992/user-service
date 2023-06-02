package pro.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "address")
@Data
public class Address extends CommonEntity {
    private String code;
    private String name;
    private String description;
}
