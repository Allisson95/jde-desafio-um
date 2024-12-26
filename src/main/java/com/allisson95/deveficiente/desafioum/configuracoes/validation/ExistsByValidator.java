package com.allisson95.deveficiente.desafioum.configuracoes.validation;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsByValidator implements ConstraintValidator<ExistsBy, Object> {

    private final EntityManager entityManager;

    private Class<?> entity;
    private String field;

    public ExistsByValidator(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(final ExistsBy constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (context instanceof HibernateConstraintValidatorContext) {
            context.unwrap(HibernateConstraintValidatorContext.class).addMessageParameter("value", value);
        }

        final var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        final var criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final var root = criteriaQuery.from(this.entity);
        criteriaQuery.select(criteriaBuilder.literal(1L))
                .where(criteriaBuilder.equal(root.get(this.field), value));

        final var query = this.entityManager.createQuery(criteriaQuery);
        query.setMaxResults(1);

        return !query.getResultList().isEmpty();
    }

}
