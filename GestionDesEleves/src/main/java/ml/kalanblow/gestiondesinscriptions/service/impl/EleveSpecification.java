package ml.kalanblow.gestiondesinscriptions.service.impl;

import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import org.springframework.data.jpa.domain.Specification;

public class EleveSpecification {

    public static Specification<Eleve> findIneNumber(String ineNumber) {

        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ineNumber"), ineNumber));
    }

    public static Specification<Eleve> recupererEleveParSonNomePrenom(String  nomComplete) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),"%"+  nomComplete + "%");
    }

    public static Specification<Eleve> recupererEleveParEmail(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"),"%" + email + "%");
    }

    public static Specification<Eleve> hasId(Long userId) {

        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), userId));
    }

    public static Specification<Eleve> all() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }


}
