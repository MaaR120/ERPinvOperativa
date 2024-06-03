package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "DetalleVenta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetalleVenta extends Base{
    @NotNull
    private int cantidad;

    @NotNull
    @Column(name = "subtotal")
    private double subtotal;

    @ManyToOne()
    @JoinColumn(name = "Articulo")
    private Articulo Articulo;
}
