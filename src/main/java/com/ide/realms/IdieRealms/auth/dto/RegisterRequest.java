package com.ide.realms.IdieRealms.auth.dto;

import com.ide.realms.IdieRealms.shared.HeroClass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email must be provided")
    private String email;

    @NotBlank(message = "Nickname must be provided")
    private String nickname;

    @NotBlank(message = "Password must be provided")
    private String password;

    private String visualConfig;

    @NotNull(message = "You must select class")
    private HeroClass heroClass;

    @NotBlank(message = "Hero nickname must be provided")
    private String heroNickname;
}