# Démo Spring Boot

Nécessite l'installation de jdk21.

https://www.oracle.com/java/technologies/downloads/#java21

Puis

```
./graddlew bootRun
```

Plan de la démo:

1. Classe principale et @SpringBootApplication (config + scan)
2. BookConfiguration @Configuration et @PropertySource/@value
3. BookConfiguration @Bean et @Scope
4. model (record et sérialisation json automatique)
5. BooksRepository @Repository (injection de dépendances)
6. BooksController @RestController et @XMapping

Test et démo avec POSTMAN.
