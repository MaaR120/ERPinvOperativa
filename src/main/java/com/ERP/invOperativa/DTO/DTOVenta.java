package com.ERP.invOperativa.DTO;

import com.ERP.invOperativa.DTO.DTODetalleVenta;
import com.ERP.invOperativa.Entities.DetalleVenta;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTOVenta {
    private Date fechaFacturacion;
    private double totalVenta;
    private ArrayList<DTODetalleVenta> detalleVentas;



}