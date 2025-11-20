package com.shopitto.authservice.dto;

//public class LoginRequest {
//}
//src/main/java/com/shopitto/authservice/dto/LoginRequest.java
//package com.shopitto.authservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    private String username;
    private String password;
}
