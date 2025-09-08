package co.com.crediya.loan.api.reactive_controllers;

import co.com.crediya.loan.api.request.LoanRequest;
import co.com.crediya.loan.api.request.LoanSearchRequest;
import co.com.crediya.loan.api.services.LoansAppService;
import co.com.crediya.loan.api.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoansHandler {

    private final LoansAppService loansAppService;

    public Mono<ServerResponse> createLoan(ServerRequest request) {
        return request.bodyToMono(LoanRequest.class)
                .map(Mapper::toLoanModel)
                .flatMap(loansAppService::createLoan)
                .flatMap(loanResponse -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(loanResponse)
                );
    }

    public Mono<ServerResponse> searchLoans(ServerRequest request) {
        int page = request.queryParam("page").map(Integer::parseInt).orElse(0);
        int size = request.queryParam("size").map(Integer::parseInt).orElse(10);
        return request.bodyToMono(LoanSearchRequest.class)
                .map(loanSearchRequest -> Mapper.toLoanSearModel(loanSearchRequest, page, size))
                .flatMap(loansAppService::searchLoans)
                .flatMap(loanPaginationResponse->ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(loanPaginationResponse)
                );

    }
}
