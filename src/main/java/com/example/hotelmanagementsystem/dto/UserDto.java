package com.example.hotelmanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "Please enter valid name")
    private String name;

    @NotEmpty(message = "Please enter valid email")
    @Email
    private String email;

    @NotEmpty(message = "Please enter valid password")
    @Size(min = 8,max = 25)
    private String password;

    @NotEmpty(message = "Please enter valid lastname")
    private String lastName;

    private String middleName;

    @NotEmpty(message = "Please enter valid address")
    private String residential;

    @NotEmpty(message = "Please enter valid postal")
    private String postal;
}
