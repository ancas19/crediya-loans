package co.com.crediya.loan.model.commons.models;


import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserRequestInfo {
    private Long startTime ;
    private AtomicInteger requestCount;
}
