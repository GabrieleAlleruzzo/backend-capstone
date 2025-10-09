package Capstone.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class ProgettoPortfolioDto {

    // non ho bisogno dei @NotEmpty
    private  int id;
    private String nomeProgetto;
    private String imgCopertina;
    private String descrizione;
    private String img1;
    private String img2;
    private String img3;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date data;

}
