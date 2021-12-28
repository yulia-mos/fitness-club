package com.fitnessclub.serviceclass.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {
    private String username;
    private String role;

    private String firstName;
    private String lastName;
}
