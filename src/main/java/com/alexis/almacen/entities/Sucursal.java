package com.alexis.almacen.entities;

import com.alexis.almacen.enums.Categoria;
import com.alexis.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/// Aqui solo se validan que las reglas de negocio se cumplan.


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "SUCURSALES")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL")
    private Long id;

    @Column(name = "NOMBRE", length = 50, unique = true, nullable = false)
    private String nombre;

    @Column(name = "DIRECCION", length = 150, nullable = false)
    private String direccion;


    public void actualizar(String nombre, String direccion) {

        validarDatos(nombre, direccion);

        this.nombre = nombre.trim();
        this.direccion = direccion.trim();

    }

    private void validarDatos(String nombre, String direccion) {

        StringCustomUtils.validarTamanio(nombre, 5, 50,
                "El nombre es requerido y debe tener entre 5 y 50 caracteres");

        StringCustomUtils.validarTamanio(direccion, 5, 150,
                "La dirección es requerido y debe tener entre 5 y 150 caracteres");

    }




}
