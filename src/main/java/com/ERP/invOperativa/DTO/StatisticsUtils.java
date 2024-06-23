package com.ERP.invOperativa.DTO;

import java.util.List;

public class StatisticsUtils {
    public static double calcularMedia(List<Double> valores) {
        double sum = 0.0;
        for (double valor : valores) {
            sum += valor;
        }
        return sum / valores.size();
    }

    // Método para calcular la desviación estándar
    public static double calcularDesviacionEstandar(List<Double> valores) {
        double media = calcularMedia(valores);
        double sum = 0.0;
        for (double valor : valores) {
            sum += Math.pow(valor - media, 2);
        }
        return Math.sqrt(sum / valores.size());
    }
}
