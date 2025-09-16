package co.com.crediya.loan.model.template.gateways;

import co.com.crediya.loan.model.template.models.Template;
import reactor.core.publisher.Mono;

public interface TemplateRepositoryPort {
    Mono<Template> findByName(String name);
}
