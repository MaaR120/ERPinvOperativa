package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;


@Table(name = "detalleVenta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class DetalleVenta extends Base {
    @NotNull
    private int cantidad;

    @NotNull
    @Column(name = "subtotal")
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;



}
