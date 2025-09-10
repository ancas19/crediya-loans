package co.com.crediya.loan.model.userwebclient.models;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserIdentifications {
    private Set<String> identifications;
}
