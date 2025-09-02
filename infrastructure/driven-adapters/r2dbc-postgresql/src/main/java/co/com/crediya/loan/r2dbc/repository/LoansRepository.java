package co.com.crediya.loan.r2dbc.repository;

import co.com.crediya.loan.r2dbc.entity.LoansEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface LoansRepository extends ReactiveCrudRepository<LoansEntity, UUID>, ReactiveQueryByExampleExecutor<LoansEntity> {
}
