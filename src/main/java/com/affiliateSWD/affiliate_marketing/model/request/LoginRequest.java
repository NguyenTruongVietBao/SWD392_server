package com.affiliateSWD.affiliate_marketing.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class LoginRequest {
    String username;
    String password;
}
