package co.com.crediya.loan.r2dbc.repository;


import co.com.crediya.loan.r2dbc.entity.TemplateEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TemplateRepository extends ReactiveCrudRepository<TemplateEntity, UUID>, ReactiveQueryByExampleExecutor<TemplateEntity> {
    Mono<TemplateEntity> findByName(String name);
}
