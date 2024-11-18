package com.allisson95.deveficiente.desafioum.configuracoes;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.allisson95.deveficiente.desafioum.DesafioUmApplication;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;

@Configuration
@EnableJpaRepositories(//
        basePackageClasses = {
                DesafioUmApplication.class
        }, //
        repositoryBaseClass = BaseJpaRepositoryImpl.class)
public class JpaConfiguration {

}
