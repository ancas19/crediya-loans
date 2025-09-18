package co.com.crediya.loan.r2dbc.adapters;

import co.com.crediya.loan.model.loans.gateways.LoansRepositoryPort;
import co.com.crediya.loan.model.loans.models.*;
import co.com.crediya.loan.model.queuenotification.models.LoansApproved;
import co.com.crediya.loan.r2dbc.custom_repositories.CustomuserRepository;
import co.com.crediya.loan.r2dbc.entity.LoansEntity;
import co.com.crediya.loan.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.loan.r2dbc.repository.LoansRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class LoanReactiveRepositoryAdapter extends ReactiveAdapterOperations<Loans, LoansEntity, UUID, LoansRepository> implements LoansRepositoryPort {

    private final CustomuserRepository customuserRepository;

    public LoanReactiveRepositoryAdapter(LoansRepository loansRepository, org.reactivecommons.utils.ObjectMapper objectMapper, CustomuserRepository customuserRepository){
        super(loansRepository,objectMapper,d->objectMapper.map(d, Loans.class));
        this.customuserRepository = customuserRepository;
    }


    @Override
    public Mono<Loans> createLoan(Loans loanInformation) {
        return this.repository.save(toData(loanInformation))
                .map(loanCreated -> mapper.map(loanCreated, Loans.class));
    }

    @Override
    public Mono<LoansPaginated> searchLoans(LoanSearch loanSearch) {
        return this.customuserRepository.searchLoans(loanSearch);
    }

    @Override
    public Mono<Loans> findById(UUID id) {
        return this.repository.findById(id)
                .map(loanFound -> mapper.map(loanFound, Loans.class));
    }

    @Override
    public Mono<LoanNotificationInformation> findLoanInformationById(UUID id) {
        return this.customuserRepository.findLoanInformationById(id);
    }

    @Override
    public Mono<List<LoansApproved>> findApprovedLoansByClientId(String identification) {
        return this.customuserRepository.findApprovedLoansByClientId(identification);
    }
}
