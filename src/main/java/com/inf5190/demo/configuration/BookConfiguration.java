package com.inf5190.demo.configuration;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import com.inf5190.demo.utils.IdGenerator;

/**
 * L'annotation @Configuration indique à Spring que cette classe définit des Beans.
 * 
 * Les méthodes annotées de @Bean permettent à Spring de créer les instances voulues pour
 * l'injection de dépendance.
 */
@Configuration
/**
 * L'annotation @PropertySource indique à Spring le fichier properties qui contient les valeurs
 * utilisées dans cette classe.
 */
@PropertySource("classpath:application.properties")
public class BookConfiguration {

    /**
     * @Value fait référence à la clé qui se trouve dans le fichier properties.
     */
    @Value("${books.idGenerator.initialValue}")
    Long initialValue = 0L;

    /**
     * Ici on définit un Bean nommé 'atomicIdGenerator' de type IdGenerator et qui a une scope
     * 'prototype' qui signifie qu'une nouvelle instance sera créée à chaque fois qu'une instance du
     * Bean est demandée.
     */
    @Bean(name = "atomicIdGenerator")
    @Scope("prototype")
    public IdGenerator atomicIdGenerator() {
        return new IdGenerator() {
            private final AtomicLong idGenerator =
                    new AtomicLong(BookConfiguration.this.initialValue);

            @Override
            public long nextId() {
                System.out.println("atomique");
                return this.idGenerator.incrementAndGet();
            }

        };
    }

    /**
     * Ici on définit un autre Bean nommé 'idGenerator' de type IdGenerator et qui a aussi une scope
     * 'prototype'.
     */
    @Bean(name = "idGenerator")
    @Scope("prototype")
    public IdGenerator idGenerator() {
        return new IdGenerator() {
            private long id = BookConfiguration.this.initialValue;

            @Override
            public long nextId() {
                System.out.println("non-atomique");
                return this.id++;
            }

        };
    }
}
