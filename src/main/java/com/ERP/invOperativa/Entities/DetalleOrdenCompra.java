package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "DetalleOrdenCompra")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetalleOrdenCompra extends Base{
    @NotNull
    private int cantidad;

    @NotNull
    @Column(name = "subtotal")
    private double subtotal;

    @ManyToOne()
    @JoinColumn(name = "id_articulo")
    private Articulo articulo;

    @ManyToOne()
    @JoinColumn(name = "id_Orden")
    private OrdenCompra ordenCompra;


}
