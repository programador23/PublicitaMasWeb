package cl.christian.demo.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.christian.demo.modelo.CampaniaPublicitaria;
@Repository
public interface ICampaniaPublicitaria extends CrudRepository<CampaniaPublicitaria, Integer>{

	
}
