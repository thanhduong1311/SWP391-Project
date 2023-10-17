package com.demo.homemate.dtos.password;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class tokenEmailConfirm {
String email;
String code;
}
