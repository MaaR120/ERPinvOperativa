package com.ERP.invOperativa.Entities;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.Enum.MetodoPrediccion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Prediccion")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Prediccion extends Base{

    @NotNull
    @Column(name = "metodo_prediccion")
    @Enumerated(EnumType.STRING)
    private MetodoPrediccion metodoPrediccion;

    @Column(name = "error_demanda")
    private double errorDemanda;

    @Column(name = "fecha_utilizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaUtilizacion;

//    @OneToOne
//    @JoinColumn(name = "articulo")
//    private Articulo articulo;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "prediccion")
    @Builder.Default
    private List<PrediccionDetalle> prediccionDetalles = new ArrayList<>();

    public void asignarDetalle(List<DTOPrediccion> dtoPredicciones){
        List<PrediccionDetalle> prediccionDetalles1=new ArrayList<>();
        for (DTOPrediccion dtoPrediccion:dtoPredicciones){
            PrediccionDetalle prediccionDetalle=new PrediccionDetalle();
            prediccionDetalle.setCantidadPrediccion(dtoPrediccion.getCantidadPrediccion());
            prediccionDetalle.setMes(dtoPrediccion.getMes());
            prediccionDetalle.setCantidadReal(dtoPrediccion.getCantidadReal());
            prediccionDetalle.setError(dtoPrediccion.getError());
            prediccionDetalles1.add(prediccionDetalle);
        }
        this.prediccionDetalles=prediccionDetalles1;
    }


}
