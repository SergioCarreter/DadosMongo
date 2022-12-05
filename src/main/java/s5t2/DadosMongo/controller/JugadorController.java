package s5t2.DadosMongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s5t2.DadosMongo.dto.JugadorDTO;
import s5t2.DadosMongo.dto.PartidaDTO;
import s5t2.DadosMongo.dto.Resultado;
import s5t2.DadosMongo.service.JugadorService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/players")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    // Guardar Jugador
    @PostMapping
    public ResponseEntity<?> guardarJugador(@RequestBody JugadorDTO jugadorDTO ){
        ResponseEntity<?> re;
        JugadorDTO jugadorSalvadoDTO = jugadorService.guardarJugador(jugadorDTO);

        if (jugadorSalvadoDTO == null){
            re= ResponseEntity.status( 409 ).body("Usuario ya existente");
        } else {
            re = ResponseEntity.status(HttpStatus.CREATED).body( jugadorSalvadoDTO );
        }
        return re;
    }


    // Modificar nombre de Jugador
    @PutMapping
    public ResponseEntity<?> modificaNombreJugador(@RequestParam String nombreViejo, @RequestParam String nombreNuevo){

        ResponseEntity<?> re;
        JugadorDTO jugadorDTO = jugadorService.cambiaNombreJugador(nombreViejo, nombreNuevo);

        if (jugadorDTO == null){
            re = ResponseEntity.notFound().build();
        } else {
            re = ResponseEntity.status(HttpStatus.CREATED).body(jugadorDTO);
        }
        return re;
    }

    // Obten partidas de un jugador por jugadorId
    @GetMapping("/{id}/games")
    public ResponseEntity<?> getPartidasPorJugadorId( @PathVariable(value="id") String jugadorId ){

        ResponseEntity<?> re = null;
        List<PartidaDTO> partidasDTO = jugadorService.getPartidasDeJugadorPorId(jugadorId);

        if ( partidasDTO != null ){
            re = ResponseEntity.status(HttpStatus.OK).body( partidasDTO );
        } else {
            re = ResponseEntity.notFound().build();
        }
        return re;
    }

    // Borra partidas de un jugador por jugadorId
    @DeleteMapping("/{id}/games")
    public ResponseEntity<?> delPartidasPorJugadorId( @PathVariable(value="id") String jugadorId ){

        ResponseEntity<?> re = null;
        JugadorDTO jDTO = jugadorService.delPartidasDeJugadorPorId(jugadorId);

        if ( jDTO != null ){
            re = ResponseEntity.status(HttpStatus.OK).body( "Partidas del Jugador: " + jDTO.getNombre() + " Borradas!" );
        } else {
            re = ResponseEntity.notFound().build();
        }
        return re;
    }

    // Obten prob de exito medio de todos los jugadores registrados.
    @GetMapping({"", "/ranking"})
    public ResponseEntity<?> getWinsMediosJugadores(){

        ResponseEntity<?> re = null;
        ArrayList<Resultado> resultados = jugadorService.getWinsMediosJugadores();

        if ( resultados != null ){
            Collections.sort( resultados );
            re = ResponseEntity.status(HttpStatus.OK).body( resultados );
        } else {
            re = ResponseEntity.notFound().build();
        }
        return re;
    }

    // Obten prob de exito medio de todos los jugadores registrados.
    @GetMapping("/ranking/loser")
    public ResponseEntity<?> getLowerWinsMediosJugadores(){

        ResponseEntity<?> re = null;
        ArrayList<Resultado> resultados = jugadorService.getWinsMediosJugadores();

        if ( resultados != null ){
            Collections.sort( resultados );
            re = ResponseEntity.status(HttpStatus.OK).body( resultados.get(0) );
        } else {
            re = ResponseEntity.notFound().build();
        }
        return re;
    }

    // Obten prob de exito medio de todos los jugadores registrados.
    @GetMapping("/ranking/winner")
    public ResponseEntity<?> getHigherWinsMediosJugadores(){

        ResponseEntity<?> re = null;
        ArrayList<Resultado> resultados = jugadorService.getWinsMediosJugadores();

        if ( resultados != null ){
            Collections.sort( resultados );
            re = ResponseEntity.status(HttpStatus.OK).body( resultados.get( resultados.size()-1 ) );
        } else {
            re = ResponseEntity.notFound().build();
        }
        return re;
    }

    @PostMapping("/{id}/games")
    public ResponseEntity<?> addPartidaAJugadorPorId(@PathVariable(value="id") String jugadorId){

        ResponseEntity<?> re = null;
        PartidaDTO pDTO;
        pDTO = jugadorService.crearPartidaAJugadorPorId(jugadorId);

        if ( pDTO != null ){
            re = ResponseEntity.status(HttpStatus.OK).body( pDTO );
        } else {
            re = ResponseEntity.notFound().build();
        }
        return re;
    }

}
