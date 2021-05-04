package cl.christian.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.christian.demo.interfaceService.ICampaniaPublicitariaService;
import cl.christian.demo.interfaces.ICampaniaPublicitaria;
import cl.christian.demo.modelo.CampaniaPublicitaria;

@Service
public class CampaniaPublicitariaService implements ICampaniaPublicitariaService{

	@Autowired
	private ICampaniaPublicitaria data;
	@Override
	public List<CampaniaPublicitaria> listarcampania() {
		
		return (List<CampaniaPublicitaria>)data.findAll();
	}

	@Override
	public Optional<CampaniaPublicitaria> listarId(int id) {
		
		
		return data.findById(id);
	}

	@Override
	public int savecampania(CampaniaPublicitaria c) {
		//guarda 
		int res=0;
		CampaniaPublicitaria campaniapublicitaria=data.save(c);
		if(!campaniapublicitaria.equals(null)) {
			res=1;
		}
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

}
