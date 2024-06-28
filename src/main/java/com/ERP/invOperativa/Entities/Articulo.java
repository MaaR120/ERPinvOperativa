package com.ERP.invOperativa.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
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



    @Column(name = "Stock")
    private int Stock;

    @Column(name = "StockSeguridad")
    private double StockSeguridad;

    @Column(name = "PuntoPedido")
    private double PuntoPedido;

    @Column(name = "LoteOptimo")
    private double LoteOptimo;

    @Column(name = "precio")
    @NotNull
    private double precio;

    @Column(name = "fechaBaja")
    private Date fechaBaja;

    @Column(name = "CantidadPreparacion")
    private Integer cantidadPreparacion;



    @OneToMany(mappedBy = "articulo",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "Prediccion")

    private Prediccion prediccion=null;

}
