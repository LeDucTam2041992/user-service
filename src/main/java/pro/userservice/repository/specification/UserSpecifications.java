package pro.userservice.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import pro.userservice.model.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserSpecifications {
    private UserSpecifications() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<User> hasCif(String cif) {
        return (root, query, cb) -> cb.equal(root.get("cif"), cif);
    }


    public static Specification<User> hasIdIns(List<UUID> ids) {
        return (root, query, cb) -> root.get("id").in(ids);
    }

    public static Specification<User> hasFromDates(LocalDateTime from) {

        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (from != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), from.toLocalDate().atTime(LocalTime.MIN)));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public static Specification<User> hasToDates(LocalDateTime to) {

        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (to != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), to.toLocalDate().atTime(LocalTime.MAX)));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
