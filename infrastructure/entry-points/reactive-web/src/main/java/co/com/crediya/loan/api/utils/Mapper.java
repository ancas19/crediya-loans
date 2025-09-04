package co.com.crediya.loan.api.utils;


import co.com.crediya.loan.api.request.LoanRequest;
import co.com.crediya.loan.api.response.LoanResponse;
import co.com.crediya.loan.model.loans.models.Loans;

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
}
