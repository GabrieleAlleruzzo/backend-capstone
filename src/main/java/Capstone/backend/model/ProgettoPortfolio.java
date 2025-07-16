package Capstone.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name="ProgettoPortfolio")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgettoPortfolio {
    @Id
    @GeneratedValue
    private int id;
    private String nomeProgetto;
    private String imgCopertinaId;
    private String imgCopertina;
    @Column(length = 1000)
    private String descrizione;
    private String img1Id;
    private String img1;
    private String img2Id;
    private String img2;
    private String img3Id;
    private String img3;
    private Date data;
}
