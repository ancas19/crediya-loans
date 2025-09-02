package co.com.crediya.loan.r2dbc.repository;

import co.com.crediya.loan.r2dbc.entity.LoanTypeEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanTypeRepository extends ReactiveCrudRepository<LoanTypeEntity, UUID>, ReactiveQueryByExampleExecutor<LoanTypeEntity> {
    Mono<LoanTypeEntity> findByName(String name);
}
