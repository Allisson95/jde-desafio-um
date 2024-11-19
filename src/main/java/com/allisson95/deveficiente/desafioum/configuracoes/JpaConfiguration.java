package com.allisson95.deveficiente.desafioum.configuracoes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.allisson95.deveficiente.desafioum.DesafioUmApplication;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import jakarta.validation.Validator;

@Configuration
@EnableJpaRepositories(//
        basePackageClasses = {
                DesafioUmApplication.class
        }, //
        repositoryBaseClass = BaseJpaRepositoryImpl.class)
public class JpaConfiguration {

    @Bean
    Validator defaultValidator() {
        return new LocalValidatorFactoryBean();
    }

}
