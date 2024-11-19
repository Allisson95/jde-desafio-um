package com.allisson95.deveficiente.desafioum.configuracoes.validation;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private final EntityManager entityManager;

    private Class<?> entityClass;
    private String uniqueField;

    public UniqueValidator(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(final Unique constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
        this.uniqueField = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (context instanceof HibernateConstraintValidatorContext) {
            context.unwrap(HibernateConstraintValidatorContext.class).addMessageParameter("value", value);
        }

        final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<?> root = cq.from(this.entityClass);
        cq.select(cb.literal(1L))
                .where(cb.equal(root.get(this.uniqueField), value));

        final TypedQuery<Long> query = this.entityManager.createQuery(cq);
        return query.getResultList().isEmpty();
    }

}
