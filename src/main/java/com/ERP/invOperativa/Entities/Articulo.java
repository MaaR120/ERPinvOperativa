package com.ERP.invOperativa.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private String NombreArticulo;

    private Double CostoAlmacenamiento;

    private int Stock;

    private int StockSeguridad;

    private int PuntoPedido;

    private int LoteOptimo;

    @NotNull
    private double precio;

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