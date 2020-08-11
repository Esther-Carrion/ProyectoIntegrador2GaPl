package net.gecc.crudthymeleaf.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import net.gecc.crudthymeleaf.entities.juego;

public interface JuegoRepo extends CrudRepository <juego, Long> {
		List<juego> findByNombre(String nombre);
		List<juego> findByDescripcion(String descripcion);
}
