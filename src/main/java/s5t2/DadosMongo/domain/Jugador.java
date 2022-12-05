package s5t2.DadosMongo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection="jugadores")
public class Jugador {

    @Id
    private String id;

    private String nombre;
    private String password;
    private String fechaReg;
    private List<Partida> partidas = new ArrayList<>();

}
