package net.gecc.crudthymeleaf.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import net.gecc.crudthymeleaf.entities.cliente;

public interface ClienteRepo extends CrudRepository <cliente, Long> {
		List<cliente> findByNombre(String nombre);
		//List<cliente> findByAll(Long Id);
}
