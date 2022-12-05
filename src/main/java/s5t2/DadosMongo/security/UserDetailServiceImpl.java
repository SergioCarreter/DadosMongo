package s5t2.DadosMongo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import s5t2.DadosMongo.domain.Jugador;
import s5t2.DadosMongo.repository.JugadorRepository;

//Se encarga de cargar el usuario desde la BD
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Jugador jugador = jugadorRepository
                .findByNombre(nombre)
                .orElseThrow( () -> new UsernameNotFoundException( "El usuario con con nombre: " + nombre + " no existe" ));

        return new UserDetailsImpl( jugador );
    }
}
