package com.ERP.invOperativa.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "PrediccionDetalle")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PrediccionDetalle extends Base{
    @Column(name = "mes")
    public String mes;

    @Column(name = "cantidad_real")
    public int cantidadReal;

    @Column(name = "cantidad_prediccion")
    public int cantidadPrediccion;

    @Column(name = "error")
    public double error;
}
