package com.ERP.invOperativa;

import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.VentaRepository;
import com.ERP.invOperativa.Services.VentaService;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.Venta;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class InvOperativaApplication {
	@Autowired
	private VentaService ventaService;

	@Autowired
	private ArticuloRepository articuloRepository;
	@Autowired

	private VentaRepository ventaRepository;
	public static void main(String[] args) {
		SpringApplication.run(InvOperativaApplication.class, args);
		System.out.println("Programa en Ejecucion");
	}

	@Bean
	public CommandLineRunner init() {
		return args -> {
			System.out.println("------------------------Estoy Funcionando------------------------");

			// Crear artículos
			Articulo articulo1 = new Articulo();
			articulo1.setNombreArticulo("Tornillo");
			articulo1.setPrecio(2500.0);
			articuloRepository.save(articulo1);


			// Crear detalles de venta
			DetalleVenta detalleVenta1 = new DetalleVenta();
			detalleVenta1.setArticulo(articulo1);
			detalleVenta1.setCantidad(5);

			List<DetalleVenta> detallesVenta = new ArrayList<>();
			detallesVenta.add(detalleVenta1);

			// Crear venta
			Venta venta = new Venta();
			venta.setDetalleVentas(detallesVenta);
			venta.setTotalVenta(15000);
			venta.setFechaFacturacion(new Date(System.currentTimeMillis()));

			ventaRepository.save(venta);


		};
	}
}

