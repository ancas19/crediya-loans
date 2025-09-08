package co.com.crediya.loan.api.reactive_controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoanRouterRest {
    private final LoansHandler loansHandler;

    public LoanRouterRest(LoansHandler loansHandler) {
        this.loansHandler = loansHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .POST("/solicitud",loansHandler::createLoan)
                .POST("/solicitud/detalles",loansHandler::searchLoans)
                .build();
    }
}
