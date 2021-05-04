package cl.christian.demo.interfaceService;

import java.util.List;
import java.util.Optional;

import cl.christian.demo.modelo.CampaniaPublicitaria;

public interface ICampaniaPublicitariaService {

	public List<CampaniaPublicitaria>listarcampania();
	public Optional<CampaniaPublicitaria>listarId(int id);
	public int savecampania(CampaniaPublicitaria c);
	public void delete(int id);
	
}
