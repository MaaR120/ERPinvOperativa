package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import lombok.Builder;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "articulo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Articulo extends Base{

    @Column(name = "NombreArticulo")
    private String NombreArticulo;

    @Column(name = "CostoAlmacenamiento")
    private Double CostoAlmacenamiento;

    @Column(name = "Stock")
    private int Stock;

    @Column(name = "StockSeguridad")
    private int StockSeguridad;

    @Column(name = "PuntoPedido")
    private int PuntoPedido;

    @Column(name = "LoteOptimo")
    private int LoteOptimo;

    @Column(name = "precio")
    @NotNull
    private double precio;

    @Column(name = "fechaBaja")
    private Date fechaBaja;

    @ManyToOne
    @JoinColumn(name="FamiliaArticulo")
    private FamiliaArticulo familiaArticulo;


    public Double getPrecio() {
        return precio;
    }

    public String getNombreArticulo(){
        return NombreArticulo;
    }
    public void setNombreArticulo(String nombreArticulo) {
        this.NombreArticulo = nombreArticulo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

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
