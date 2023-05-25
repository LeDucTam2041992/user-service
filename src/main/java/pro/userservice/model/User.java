package pro.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "unique_cif", columnNames = { "cif" }) })
@Data
public class User extends CommonEntity {
    private String cif;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Integer old;
    private LocalDate dob;
    private String avatar;
    private String activeCode;
}
