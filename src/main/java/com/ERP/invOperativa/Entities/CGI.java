package com.ERP.invOperativa.Entities;

public class CGI {
    public static double calcularCGI(double costoCompra, double costoMantenimiento, double costoPedido, int cantidadPedido, int demandaAnual) {
        double costoTotalPedido = (costoPedido * demandaAnual) / cantidadPedido;
        double costoTotalCompra = costoCompra * demandaAnual;
        double costoTotalMantenimiento = (costoMantenimiento * cantidadPedido) / 2;
        return costoTotalPedido + costoTotalCompra + costoTotalMantenimiento;
    }
}
