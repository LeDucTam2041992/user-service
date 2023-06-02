package pro.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "address_code")
    private String addressCode;

    @OneToOne(fetch = FetchType.LAZY) // @ManyToOne is also possible
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "address_code", referencedColumnName = "code", insertable = false, updatable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Address address;

    @OneToMany
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(
            name = "id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<Certificate> recipeList;

}
