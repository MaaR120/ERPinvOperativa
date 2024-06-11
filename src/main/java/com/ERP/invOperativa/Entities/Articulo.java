package com.ERP.invOperativa.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @NotNull
    private Double CostoAlmacenamiento;

    @NotNull
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



    @OneToMany(mappedBy = "articulo")
    @Builder.Default
    @JsonIgnore
    private List<ArticuloProveedor> articuloProveedores = new ArrayList<>();

    @OneToMany(mappedBy = "articulo")
    @Builder.Default
    @JsonIgnore
    private List<OrdenCompra> ordenesCompra = new ArrayList<>();

    // Método para agregar un ArticuloProveedor a la lista
    public void addArticuloProveedor(ArticuloProveedor articuloProveedor) {
        articuloProveedores.add(articuloProveedor);
        articuloProveedor.setArticulo(this);
    }

    // Método para agregar una Orden de compra a la lista
    public void addOrdenCompra(OrdenCompra ordenCompra) {
        ordenesCompra.add(ordenCompra);
        ordenCompra.setArticulo(this);
    }

    @ManyToOne
    @JoinColumn(name="FamiliaArticulo")
    private FamiliaArticulo familiaArticulo;


    //Relacion con proveedores
    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ArticuloProveedor> articuloProveedores = new ArrayList<>();



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