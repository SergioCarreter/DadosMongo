package s5t2.DadosMongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import s5t2.DadosMongo.domain.Jugador;

import java.util.Optional;

@Repository
public interface JugadorRepository extends MongoRepository<Jugador, String> {

    Optional<Jugador> findByNombre(String nombre);

}
