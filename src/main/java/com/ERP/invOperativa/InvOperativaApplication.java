package com.ERP.invOperativa;

import com.ERP.invOperativa.Entities.*;
import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import com.ERP.invOperativa.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class InvOperativaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvOperativaApplication.class, args);
		System.out.println("Programa en Ejecucion");
	}

	@Autowired
	ArticuloRepository articuloRepository;

	@Autowired
	FamiliaArticuloRepository familiaArticuloRepository;

	@Autowired
	OrdenCompraRepository ordenCompraRepository;


	@Autowired
	ProveedorRepository proveedorRepository;

	@Autowired
	ArticuloProveedorRepository articuloProveedorRepository;



//Agregue esto para hacer pruebas
	@Bean
	CommandLineRunner init(ArticuloRepository ArticuloRepository1,OrdenCompraRepository OrdenCompraRepository1 ) {
		return args -> {

		FamiliaArticulo familia1 = FamiliaArticulo.builder()
			.nombreFamilia("herramienta")
					.build();
			familiaArticuloRepository.save(familia1);

	Articulo articulo1 = Articulo.builder()
					.NombreArticulo("Tornillo")
					.precio(2500.0)
					.familiaArticulo(familia1)
					.build();
		articuloRepository.save(articulo1);

			//CREO ORDEN COMPRA

			Proveedor Proveedor1 = Proveedor.builder()
					.nombreProveedor("Juan Gonzalez")
					.tiempoEstimadoEntrega(15)
					.build();

			proveedorRepository.save(Proveedor1);

			Date FechaVigente = new Date(2024, 9, 21);

			ArticuloProveedor articuloProveedor1 = ArticuloProveedor.builder()
					.proveedor(Proveedor1)
					.articulo(articulo1)
					.precioArticuloProveedor(30.0)
					.fechaVigencia(FechaVigente)
					.predeterminado(true)
					.build();

			articuloProveedorRepository.save(articuloProveedor1);

			OrdenCompra ordencompra1 = OrdenCompra.builder()
					.estadoOrdenCompra(EstadoOrdenCompra.Preparacion)
					.totalOrden(15000.0)
					.cantidad(200)
					.articulo(articulo1)
					.proveedor(Proveedor1)
					.build();

			ordenCompraRepository.save(ordencompra1);


		};
	}
}

