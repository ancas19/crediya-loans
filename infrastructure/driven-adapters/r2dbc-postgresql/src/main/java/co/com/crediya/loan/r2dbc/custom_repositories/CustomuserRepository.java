package co.com.crediya.loan.r2dbc.custom_repositories;

import co.com.crediya.loan.model.loans.models.LoanInformation;
import co.com.crediya.loan.model.loans.models.LoanNotificationInformation;
import co.com.crediya.loan.model.loans.models.LoanSearch;
import co.com.crediya.loan.model.loans.models.LoansPaginated;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomuserRepository {
    private final DatabaseClient databaseClient;

    public Mono<LoansPaginated> searchLoans(LoanSearch loanSearch) {
        Integer offset = (loanSearch.getPage()) * loanSearch.getSize();
        Mono<List<LoanInformation>> loansDataFound=searchLoans(loanSearch,offset);
        Mono<Integer> quantity=this.countData(loanSearch);
        return Mono.zip(loansDataFound,quantity)
                .map(tuple->LoansPaginated.builder()
                        .page(loanSearch.getPage())
                        .size(loanSearch.getSize())
                        .loansQuantity(tuple.getT2())
                        .loans(tuple.getT1())
                        .build()
                );
    }

    private Mono<List<LoanInformation>> searchLoans(LoanSearch loanSearch, Integer offset){
        return databaseClient.sql("""
                            select
                                s.id AS ID,
                                s.monto AS AMOUNT,
                                s.plazo AS TERM,
                                s.identification AS IDENTIFICATION,
                                e.descripcion AS STATE_NAME,
                                tp.nombre AS LOAN_TYPE_NAME,
                                tp.tasa_interes AS INTEREST_RATE
                            from solicitud s
                            inner join estado e on s.id_estado=e.id
                            inner join tipo_prestamo tp on tp.id=s.tipo_prestamos
                            where s.identification like :identifiaction
                            and e."name" like :state
                            LIMIT :limit OFFSET :offset
                        """)
                .bind("identifiaction", "%%%s%%".formatted(loanSearch.getIdentification()))
                .bind("state", "%%%s%%".formatted(loanSearch.getState()))
                .bind("limit", loanSearch.getSize())
                .bind("offset", offset)
                .map((row, meta) ->LoanInformation.builder()
                        .id(row.get("ID",UUID.class))
                        .amount(row.get("AMOUNT", BigDecimal.class))
                        .term(row.get("TERM", Integer.class))
                        .identification(row.get("IDENTIFICATION",String.class))
                        .loanTypeName(row.get("LOAN_TYPE_NAME",String.class))
                        .stateName(row.get("STATE_NAME", String.class))
                        .interestRate(row.get("INTEREST_RATE", BigDecimal.class))
                        .build()
                ).all()
                .collectList();
    }

    private Mono<Integer> countData(LoanSearch loanSearch){
        return databaseClient.sql("""
                        select count(*)
                        from solicitud s
                        inner join estado e on s.id_estado=e.id
                        inner join tipo_prestamo tp on tp.id=s.tipo_prestamos
                        where s.identification like :identification
                        and e.name like :state
                    """)
                .bind("identification", "%%%s%%".formatted(loanSearch.getIdentification()))
                .bind("state", "%%%s%%".formatted(loanSearch.getState()))
                .map(row -> row.get(0, Long.class).intValue())
                .one();
    }

    public Mono<LoanNotificationInformation> findLoanInformationById(UUID id) {
        return databaseClient.sql(
                """
                select
                    s.id AS ID,
                    s.monto AS AMOUNT,
                    s.plazo AS TERM,
                    s.identification AS IDENTIFICATION,
                    e.descripcion AS STATUS,
                    e.name AS STATUS_NAME,
                    tp.nombre AS LOAN_TYPE_NAME,
                    tp.tasa_interes AS INTEREST_RATE
                from solicitud s
                inner join estado e on s.id_estado=e.id
                inner join tipo_prestamo tp on tp.id=s.tipo_prestamos
                where s.id = :id
                """
        ).bind("id", id)
                .map((row, meta) -> LoanNotificationInformation.builder()
                        .id(row.get("ID", UUID.class))
                        .amount(row.get("AMOUNT", BigDecimal.class))
                        .term(row.get("TERM", Integer.class))
                        .identification(row.get("IDENTIFICATION", String.class))
                        .loanType(row.get("LOAN_TYPE_NAME", String.class))
                        .status(row.get("STATUS_NAME", String.class))
                        .statusDescription(row.get("STATUS", String.class))
                        .interest(row.get("INTEREST_RATE", BigDecimal.class))
                        .build()
                ).one();
    }
}
