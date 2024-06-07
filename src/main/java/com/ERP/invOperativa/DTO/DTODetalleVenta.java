package com.ERP.invOperativa.DTO;
import com.ERP.invOperativa.Entities.Articulo;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTODetalleVenta {
    private Long idArticulo;
    private int cantidad;
    private Articulo articulo;
    private Double subtotal;

    // Otros atributos y métodos

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    // Otros atributos y métodos

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
