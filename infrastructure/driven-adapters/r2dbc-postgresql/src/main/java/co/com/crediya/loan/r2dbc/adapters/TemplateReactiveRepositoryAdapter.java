package co.com.crediya.loan.r2dbc.adapters;

import co.com.crediya.loan.model.loans.models.LoanNotificationInformation;
import co.com.crediya.loan.model.loans.models.LoanSearch;
import co.com.crediya.loan.model.loans.models.Loans;
import co.com.crediya.loan.model.loans.models.LoansPaginated;
import co.com.crediya.loan.model.template.gateways.TemplateRepositoryPort;
import co.com.crediya.loan.model.template.models.Template;
import co.com.crediya.loan.r2dbc.entity.TemplateEntity;
import co.com.crediya.loan.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.loan.r2dbc.repository.TemplateRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class TemplateReactiveRepositoryAdapter extends ReactiveAdapterOperations<Template, TemplateEntity, UUID, TemplateRepository> implements TemplateRepositoryPort {

    public TemplateReactiveRepositoryAdapter(TemplateRepository templateRepository, ObjectMapper objectMapper){
        super(templateRepository,objectMapper,d->objectMapper.map(d, Template.class));
    }

    @Override
    public Mono<Template> findByName(String name) {
        return repository.findByName(name)
                .map(entity -> mapper.map(entity, Template.class));
    }
}
