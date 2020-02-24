package com.bemedica.springboot.app.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.bemedica.springboot.app.models.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {  // se creo este repositorio para ocuparlo en Rol

}
