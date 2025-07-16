package Capstone.backend.dto;


import Capstone.backend.enumeration.TipoPorgettoCommissione;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProgettoCommissioniDto {

    @NotEmpty(message = "non hai un nome?")
    private String name;

    @NotEmpty(message = "non hai un congnome?")
    private String surname;

    @NotEmpty(message = "non hai un numero di telefono?")
    private String tel;

    @NotEmpty(message = "non hai un email?")
    private String email;

    @NotNull(message = "seleziona il servizio di cui hai bisogno")
    @Enumerated(EnumType.STRING)
    private TipoPorgettoCommissione tipoProg;

    @NotEmpty(message = "descrivi il tuo progetto")
    private String descrizione;

    @NotNull(message = "seleziona il tuo budget")
    private int budget;

    @NotEmpty(message = "data?")
    private String data;


}
