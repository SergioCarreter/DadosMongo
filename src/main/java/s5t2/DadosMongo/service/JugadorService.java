package s5t2.DadosMongo.service;

import s5t2.DadosMongo.dto.JugadorDTO;
import s5t2.DadosMongo.dto.PartidaDTO;
import s5t2.DadosMongo.dto.Resultado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface JugadorService {

    public ArrayList<Resultado> getWinsMediosJugadores();

    public JugadorDTO getJugadorPorId(String id);

    public List<PartidaDTO> getPartidasDeJugadorPorId(String JugadorId );

    public JugadorDTO delPartidasDeJugadorPorId(String jugadorId );

    public JugadorDTO guardarJugador( JugadorDTO jugadorDTO);

    public void eliminarJugadorPorId(String id);

    public JugadorDTO cambiaNombreJugador(String nombreViejo, String nombreNuevo);

    public PartidaDTO crearPartidaAJugadorPorId(String jugadorId);

}
