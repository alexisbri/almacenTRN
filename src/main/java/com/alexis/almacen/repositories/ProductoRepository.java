package com.alexis.almacen.repositories;

import com.alexis.almacen.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// @Repository - sirve para marcar una clase como un componente de acceso a datos (DAO - Data Access Object).
@Repository
/// Para usar Jpa se deben agregar dos campos
/// Entidad y tipo de dato "<Producto, Long>"
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /// Aqui se definen funciones espeficas a traves de Jpa?

}
