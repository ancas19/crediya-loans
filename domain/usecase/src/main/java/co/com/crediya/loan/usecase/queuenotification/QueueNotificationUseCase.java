package co.com.crediya.loan.usecase.queuenotification;

import co.com.crediya.loan.model.queuenotification.gateways.QueueNotificationPort;
import co.com.crediya.loan.model.queuenotification.models.CapacityCalculation;
import co.com.crediya.loan.model.queuenotification.models.EmailNotification;
import co.com.crediya.loan.model.loans.models.LoanNotificationInformation;
import co.com.crediya.loan.model.loans.models.Payments;
import co.com.crediya.loan.model.template.models.Template;
import co.com.crediya.loan.usecase.template.TemplateUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static co.com.crediya.loan.model.commons.enums.Constants.APPROVED;
import static co.com.crediya.loan.model.commons.enums.Constants.TABLE_LOAN_CONTENT;


@RequiredArgsConstructor
public class QueueNotificationUseCase {
    private final QueueNotificationPort queueNotificationPort;
    private final TemplateUseCase templateUseCase;

    public Mono<Void> sendEmailNotification(LoanNotificationInformation loanNotificationInformation) {
        return templateUseCase.findByName(loanNotificationInformation.getStatus())
                .flatMap(template -> buildEmailNotification(template, loanNotificationInformation))
                .flatMap(emailNotification -> this.queueNotificationPort.sendEmailNotification(
                                EmailNotification.builder()
                                        .to(loanNotificationInformation.getEmail())
                                        .subject(emailNotification.getSubject())
                                        .body(emailNotification.getBody())
                                        .build()
                        )
                );
    }

    public Mono<Void> calculateCapacity(CapacityCalculation capacityCalculation) {
        return this.queueNotificationPort.calculateCapacity(capacityCalculation);

    }

    private Mono<EmailNotification> buildEmailNotification(Template template, LoanNotificationInformation loanNotificationInformation) {
        String content = template.getContent()
                .replace("{{name}}", loanNotificationInformation.getNames())
                .replace("{{loanId}}", loanNotificationInformation.getId().toString())
                .replace("{{amount}}", loanNotificationInformation.getAmount().toString())
                .replace("{{term}}", loanNotificationInformation.getTerm().toString())
                .replace("{{monthlyPayment}}", loanNotificationInformation.getMonthlyPayment().toString())
                .replace("{{year}}", String.valueOf(LocalDate.now().getYear()));

        if (template.getName().equals(APPROVED.getValue())) {
            StringBuilder tableContent = new StringBuilder();
            for (Payments payment : loanNotificationInformation.getPayments()) {
                tableContent.append(
                        TABLE_LOAN_CONTENT.getValue().formatted(
                                payment.getId(),
                                payment.getAmount(),
                                payment.getInterest(),
                                payment.getCapital(),
                                payment.getBalance()
                        )
                );
            }
            content = content.replace("{{content}}", tableContent.toString());
        }
        return Mono.just(
                EmailNotification.builder()
                        .to(loanNotificationInformation.getEmail())
                        .subject(template.getSubject())
                        .body(content)
                        .build()
        );
    }
}
