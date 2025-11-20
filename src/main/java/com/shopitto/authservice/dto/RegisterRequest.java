package com.shopitto.authservice.dto;

//public class RegisterRequest {
//}

//package com.shopitto.authservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String role;  // USER / SELLER / ADMIN
}

