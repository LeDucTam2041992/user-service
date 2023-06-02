package pro.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pro.userservice.model.Address;
import pro.userservice.model.Certificate;

import java.time.LocalDate;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailUserDto extends UpsertUserDto {
    private String cif;
    private Address address;
    private List<Certificate> recipeList;
}
