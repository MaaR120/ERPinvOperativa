package com.ERP.invOperativa;

import com.ERP.invOperativa.Entities.*;
import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import com.ERP.invOperativa.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	DetalleOrdenCompraRepository detalleOrdenCompraRepository;

	@Autowired
	ProveedorRepository proveedorRepository;

	List<DetalleOrdenCompra> DetallesOrdenCompra;



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

			DetalleOrdenCompra Detalle1 = DetalleOrdenCompra.builder()
					.cantidad(200)
					.subtotal(12000.0)
					.articulo(articulo1)
					.build();
			detalleOrdenCompraRepository.save(Detalle1);
//			DetallesOrdenCompra.add(Detalle1);

			Proveedor Proveedor1 = Proveedor.builder()
					.nombreProveedor("Juan Gonzalez")
					.tiempoEstimadoEntrega(15)
					.build();

			proveedorRepository.save(Proveedor1);

			OrdenCompra ordencompra1 = OrdenCompra.builder()
					.estadoOrdenCompra(EstadoOrdenCompra.Preparacion)
					.totalOrden(15000.0)
					.proveedor(Proveedor1)
					.build();
//			ordencompra1.agregarDetalleOrdenCompra(Detalle1);
			ordenCompraRepository.save(ordencompra1);


		};
	}
}
//	@Bean
//	CommandLineRunner init(OrdenCompraRepository OrdenCompraRepository1) {
//		return args -> {
//			DetalleOrdenCompra Detalle1 = DetalleOrdenCompra.builder()
//					.cantidad(200)
//					.subtotal(12000)
//					.articulo(articulo1)
//					.build();
//			detalleOrdenCompraRepository.save(Detalle1);
//
//			Proveedor Proveedor1 = Proveedor.builder()
//					.nombreProveedor("Juan Gonzalez")
//							.tiempoEstimadoEntrega(15)
//									.build();
//
//			proveedorRepository.save(Proveedor1);
//
//			OrdenCompra ordencompra1 = OrdenCompra.builder()
//					.estadoOrdenCompra(EstadoOrdenCompra.Preparacion)
//					.totalOrden(15000)
//					.proveedor(Proveedor1)
//					.detalleOrdenCompras()
//					.build()
//
//
//		};
//	}
//	@Bean
//	public CommandLineRunner init() {
//		return args -> {
//			System.out.println("------------------------Estoy Funcionando------------------------");
//
//		};
//	}


