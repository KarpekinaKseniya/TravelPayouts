package com.self.education.travelpayouts.api;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @Email(message = "User email isn't valid", regexp = "^(?=.{1,64}@)\\w+(\\.\\w+)*@[^-][A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    @NotNull(message = "User email cannot be null")
    private String email;

    @NotBlank(message = "User name cannot be blank")
    private String name;
}
