package com.alexis.almacen.repositories;

import com.alexis.almacen.entities.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {


    /// Consultas usando JPA
    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    /*
    // SELECT COUNT() FROM SUCURSALES WHERE NOMBRE = ?;
    // SELECT COUNT() FROM SUCURSALES WHERE NOMBRE LIKE %?%;
    boolean existsByNombreIgnoreCase(String nombre);

    Optional<Sucursal> findByNombre(String nombre);
    @Query("SELECT s FRON Sucursal s WHERE s.nombre = :nombre")
    Optional<Sucursal> buscarPorNombre(@Param("nombre") String nombre);

    @Query(nativeQuery = true, value = "SELECT * FROM SUCURSALES WHERE NOMBRE = :nombre")
    Optional<Sucursal> buscarPorNombresQL(@Param("nombre") String nombre);
    */

}


