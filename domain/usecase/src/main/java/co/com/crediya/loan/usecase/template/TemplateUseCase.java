package co.com.crediya.loan.usecase.template;

import co.com.crediya.loan.model.commons.enums.ErrorMessages;
import co.com.crediya.loan.model.commons.exception.NotFoundException;
import co.com.crediya.loan.model.template.gateways.TemplateRepositoryPort;
import co.com.crediya.loan.model.template.models.Template;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TemplateUseCase {
    private final TemplateRepositoryPort templateRepositoryPort;

    public Mono<Template> findByName(String name){
        return this.templateRepositoryPort.findByName(name)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.ERROR_MESSAGE_TEMPLATE_NOT_FOUND.getMessage().formatted(name))));
    }
}
