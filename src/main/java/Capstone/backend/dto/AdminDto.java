package Capstone.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AdminDto {
    @NotEmpty(message = "inserisci nome")
    private String name;
    @NotEmpty(message = "inserisci password")
    private String pswd;
}
