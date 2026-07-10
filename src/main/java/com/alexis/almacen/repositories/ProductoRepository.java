package com.alexis.almacen.repositories;

import com.alexis.almacen.entities.Producto;
import com.alexis.almacen.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


// @Repository - sirve para marcar una clase como un componente de acceso a datos (DAO - Data Access Object).
@Repository
/// Para usar Jpa se deben agregar dos campos
/// Entidad y tipo de dato "<Producto, Long>"
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /// Aqui se definen funciones espeficas a traves de Jpa?


    @Query("SELECT p FROM Producto p WHERE " +
           "(:nombre IS NULL OR :nombre = '' OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:categoria IS NULL OR p.categoria = :categoria) AND " +
           "(:precioMin IS NULL OR p.precio >= :precioMin) AND " +
           "(:precioMax IS NULL OR p.precio <= :precioMax)")

    List<Producto> busquedaProductoDinamico(
            @Param("nombre") String nombre,
            @Param("categoria") Categoria categoria,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax
    );


}