package s5t2.DadosMongo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s5t2.DadosMongo.domain.Jugador;
import s5t2.DadosMongo.domain.Partida;
import s5t2.DadosMongo.dto.JugadorDTO;
import s5t2.DadosMongo.dto.PartidaDTO;
import s5t2.DadosMongo.dto.Resultado;
import s5t2.DadosMongo.repository.JugadorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JugadorServiceImpl implements JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Override
    public ArrayList<Resultado> getWinsMediosJugadores() {

        ArrayList<Resultado> resultados = new ArrayList<>();
        Resultado resultado;
        List<Partida> partidas;
        double winsMedios=0;
        List<Jugador> listaJugadores = jugadorRepository.findAll();

        for (Jugador j : listaJugadores) {
            partidas = j.getPartidas();
            winsMedios = winsMediosJugador(partidas);
            if ( !j.getNombre().equalsIgnoreCase("Anonimo") ){
                resultado = new Resultado(j.getNombre(), winsMedios);
                resultados.add( resultado );
            }
        }
        return resultados;
    }

    @Override
    public JugadorDTO getJugadorPorId(String id) {

        JugadorDTO jugadorDTO = null;
        Jugador jugador;
        Optional<Jugador> optJugador = jugadorRepository.findById(id);

        if (optJugador != null) {
            jugador = optJugador.get();
            jugadorDTO  =  deJugadorADTO( jugador );
        }

        return jugadorDTO;
    }

    public List<PartidaDTO> getPartidasDeJugadorPorId(String jugadorId ){

        List<Partida> partidas= null;
        List<PartidaDTO> partidasDTO = null;
        Jugador jugador = jugadorRepository.findById(jugadorId).get();

        if (jugador != null){
            partidas = jugador.getPartidas();
            partidasDTO = partidas.stream().map( partida -> dePartidaADTO( partida ) ).collect(Collectors.toList());
        }
        return partidasDTO;
    }

    public JugadorDTO delPartidasDeJugadorPorId(String jugadorId ){

        List<Partida> partidas = null;
        JugadorDTO jugadorDTOSalvado = null;
        Jugador jugador = jugadorRepository.findById(jugadorId).get();

        if (jugador != null){
            partidas = jugador.getPartidas();
            partidas.clear();
            jugador = jugadorRepository.save(jugador);
            jugadorDTOSalvado = deJugadorADTO( jugador );
        }
        return jugadorDTOSalvado;
    }


    @Override
    public JugadorDTO guardarJugador(JugadorDTO jugadorDTO) {
        JugadorDTO jugadorDTOSalvado=null;
        boolean nombreYaExiste=false;
        int i=0;

        Jugador jugador = deDTOaJugador( jugadorDTO );
        List<Jugador> listaJugadores = jugadorRepository.findAll();

        while ( (i<=(listaJugadores.size()-1)) && !nombreYaExiste){
            if (listaJugadores.get(i).getNombre().equals( jugador.getNombre() )){
                nombreYaExiste = true;
            }
            i=i+1;
        }

        if (!nombreYaExiste){
            if ( jugador.getNombre() == null ){
                jugador.setNombre( "Anonimo" );
            }
            jugador = jugadorRepository.save(jugador);
            jugadorDTOSalvado = deJugadorADTO( jugador );
        }

        return jugadorDTOSalvado;
    }

    @Override
    public void eliminarJugadorPorId(String id) {

        jugadorRepository.deleteById(id);

    }

    public JugadorDTO cambiaNombreJugador(String nombreViejo, String nombreNuevo) {

        List<Jugador> jugadores = jugadorRepository.findAll();
        JugadorDTO newDTO = null;

        for (Jugador j : jugadores) {
            if (j.getNombre().equals(nombreViejo)) {
                j.setNombre(nombreNuevo);
                jugadorRepository.save(j);
                newDTO = deJugadorADTO(j);
            }
        }
        return newDTO;
    }

    private double winsMediosJugador( List<Partida> partidas ){
        int wins = 0;
        double media = 0.0;
        for (Partida p: partidas){
            if ( (p.getDado1() + p.getDado2()) == 7){
                wins = wins +1;
            }
        }
        if (partidas.size()>0){
            media = ((double)wins/(double)partidas.size())*100.0;
        }
        return media;
    }

    @Override
    public PartidaDTO crearPartidaAJugadorPorId(String jugadorId) {

        Partida partida = new Partida();
        partida.setDado1( generaTiradaAleatoria() );
        partida.setDado2( generaTiradaAleatoria() );
        Jugador jugador = jugadorRepository.findById(jugadorId).get();
        List<Partida> partidas = jugador.getPartidas();
        partidas.add( partida );
        jugador.setPartidas( partidas );
        jugadorRepository.save(jugador);

        return dePartidaAPartidaDTO(partida);

    }

    private int generaTiradaAleatoria(){
        return (int)((Math.random()*6)+1);
    }

    private PartidaDTO dePartidaAPartidaDTO(Partida partida){
        PartidaDTO partidaDTO = new PartidaDTO();
        partidaDTO.setDado1(partida.getDado1());
        partidaDTO.setDado2(partida.getDado2());
        return partidaDTO;
    }

    private Partida dePartidaDTOAPartida(PartidaDTO partidaDTO){
        Partida partida = new Partida();
        partida.setDado1(partidaDTO.getDado1());
        partida.setDado2(partidaDTO.getDado2());
        return partida;
    }


    private Jugador deDTOaJugador( JugadorDTO jugadorDTO ){
        Jugador jugador = new Jugador();
        jugador.setId(jugadorDTO.getId());
        jugador.setNombre(jugadorDTO.getNombre());
        jugador.setPassword(jugadorDTO.getPassword() );
        jugador.setFechaReg(jugadorDTO.getFechaReg() );
        return jugador;
    }

    private JugadorDTO deJugadorADTO( Jugador jugador ){
        JugadorDTO jugadorDTO = new JugadorDTO();
        jugadorDTO.setId(jugador.getId());
        jugadorDTO.setNombre(jugador.getNombre());
        jugadorDTO.setPassword(jugador.getPassword() );
        jugadorDTO.setFechaReg(jugador.getFechaReg() );
        return jugadorDTO;
    }

    private PartidaDTO dePartidaADTO(Partida partida){
        PartidaDTO partidaDTO = new PartidaDTO();
        partidaDTO.setDado1( partida.getDado1() );
        partidaDTO.setDado2( partida.getDado2() );
        return partidaDTO;
    }



}

