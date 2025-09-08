package co.com.crediya.loan.api.utils;


import co.com.crediya.loan.api.request.LoanRequest;
import co.com.crediya.loan.api.request.LoanSearchRequest;
import co.com.crediya.loan.api.response.LoanResponse;
import co.com.crediya.loan.api.response.LoansPaginatedResponse;
import co.com.crediya.loan.model.loans.models.LoanInformation;
import co.com.crediya.loan.model.loans.models.LoanSearch;
import co.com.crediya.loan.model.loans.models.Loans;
import co.com.crediya.loan.model.loans.models.LoansPaginated;

import java.util.List;

public class Mapper {

    public static Loans toLoanModel(LoanRequest loanRequest){
        return Loans.builder()
                .id(loanRequest.getId())
                .amount(loanRequest.getAmount())
                .term(loanRequest.getTerm())
                .identification(loanRequest.getIdentification())
                .loanTypeName(loanRequest.getLoanType())
                .build();
    }

    public static LoanResponse toResponse(Loans loans){
        return LoanResponse.builder()
                .id(loans.getId())
                .amount(loans.getAmount())
                .term(loans.getTerm())
                .identification(loans.getIdentification())
                .loanTypeName(loans.getLoanTypeName())
                .stateName(loans.getStateName())
                .build();
    }

    public static LoanSearch toLoanSearModel(LoanSearchRequest loanSearcRequest, Integer page, Integer size){
        return LoanSearch.builder()
                .identification(loanSearcRequest.getIdentification())
                .state(loanSearcRequest.getState())
                .page(page)
                .size(size)
                .build();
    }

    public static LoansPaginatedResponse toLoanPagiantionResponse(LoansPaginated loansPaginated) {
        return LoansPaginatedResponse.builder()
                .page(loansPaginated.getPage())
                .size(loansPaginated.getSize())
                .loansQuantity(loansPaginated.getLoansQuantity())
                .loans(loansPaginated.getLoans())
                .build();
    }
}
