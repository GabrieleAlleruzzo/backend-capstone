package Capstone.backend.model;

import Capstone.backend.enumeration.TipoPorgettoCommissione;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name="ProgettoCommissioni")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgettoCommissioni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String tel;
    private String email;
    @Enumerated(EnumType.STRING)
    private TipoPorgettoCommissione tipoProg;
    private String descrizione;
    private int budget;
    // da cambiare in time stamp
    private Date data;
}
