package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Entity
@Table(name= "articulo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Articulo extends Base{

    @NotNull
    @Column
    private String NombreArticulo;

    @Column
    private Double CostoAlmacenamiento;

    @Column
    private int Stock;

    @Column
    private int StockSeguridad;

    @Column
    private int PuntoPedido;

    @Column
    private int LoteOptimo;

    @Column
    @NotNull
    private double precio;

    @Column
    private Date fechaBaja;

    @ManyToOne
    @JoinColumn(name="FamiliaArticulo")
    private FamiliaArticulo familiaArticulo;


    // Métodos para calcular stockSeguridad, puntoPedido, loteOptimo
    public int calcularStockSeguridad() {
        // retornar el calculo
        return 0;
    }

    public int calcularPuntoPedido() {
        // retornar el calculo
        return 0;
    }

    public int calcularLoteOptimo() {
        // retornar el calculo
        return 0;
    }
}