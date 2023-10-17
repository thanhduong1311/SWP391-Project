package com.demo.homemate.dtos.password;

import com.demo.homemate.dtos.email.EmailDetails;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class RecoverPassword {
    String email;
    String token;
    int expiration;
    EmailDetails emailDetails;
}
