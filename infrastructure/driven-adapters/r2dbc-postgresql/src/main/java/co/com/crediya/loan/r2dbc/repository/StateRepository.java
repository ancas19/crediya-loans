package co.com.crediya.loan.r2dbc.repository;


import co.com.crediya.loan.r2dbc.entity.StateEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface StateRepository extends ReactiveCrudRepository<StateEntity, UUID>, ReactiveQueryByExampleExecutor<StateEntity> {
}
