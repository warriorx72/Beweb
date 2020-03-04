
package com.bemedica.springboot.app.Dao;
import java.util.List;
import com.bemedica.springboot.app.models.entity.ResultadosAnti;

public interface IResultadosAntiDao {
	
	public List<ResultadosAnti> findAll( Long id );
	
	public void save (ResultadosAnti resultados);
	
	public ResultadosAnti findOne (Long id );
	
	public void delete (Long id);
	
	public List <Object []> antibioticos( Long id );

	public List<ResultadosAnti> verficarExistencia( Long id );
	
	public List <Object []> AntibiogramaNombre ( Long id );
	
	public List <Object []> ResultadoCultivo ( Long linea , Long id  );
	
	public List <Object []> ResultadoAntibio ( Long linea , Long id  );
	
	public String NombreAntibiograma	 ( Long linea , Long id  );
}
