package com.allisson95.deveficiente.desafioum.configuracoes.validation;

import java.util.Optional;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsByIdValidator implements ConstraintValidator<ExistsById, Object> {

    private final EntityManager entityManager;

    private Class<?> entity;

    public ExistsByIdValidator(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(final ExistsById constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (context instanceof HibernateConstraintValidatorContext) {
            context.unwrap(HibernateConstraintValidatorContext.class).addMessageParameter("value", value);
        }

        return Optional.ofNullable(this.entityManager.find(entity, value)).isPresent();
    }

}
