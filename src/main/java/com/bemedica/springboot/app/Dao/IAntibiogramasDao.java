
package com.bemedica.springboot.app.Dao;
import java.util.List;

import com.bemedica.springboot.app.models.entity.Antibiogramas;

public interface IAntibiogramasDao {

		
		public List<Antibiogramas> findAll();
		
		public void save (Antibiogramas perfiles);
		
		
		
		public Antibiogramas findOne (Long id );
		
		public void delete (Long id);
		

	}

