package co.edu.javeriana.pedisoft.usermanager.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDeleteRequest {
    @NotNull
    @NotBlank
    private String username;
}
