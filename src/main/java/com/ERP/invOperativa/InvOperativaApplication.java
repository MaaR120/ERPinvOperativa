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
import static com.ERP.invOperativa.Enum.Modelo.Lote_fijo;

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
					.modelo(Lote_fijo)
					.build();
			familiaArticuloRepository.save(familia1);

	Articulo articulo1 = Articulo.builder()
					.NombreArticulo("Tornillo")
					.CostoAlmacenamiento(1500.0)
					.precio(2500.0)
					.familiaArticulo(familia1)
					.build();
		articuloRepository.save(articulo1);

			Articulo articulo2 = Articulo.builder()
					.NombreArticulo("Celular")
					.CostoAlmacenamiento(1600.0)
					.precio(2000.0)
					.familiaArticulo(familia1)
					.build();
			articuloRepository.save(articulo2);



//Creo proveedor
			Proveedor Proveedor1 = Proveedor.builder()
					.nombreProveedor("Juan Gonzalez")
					.build();

//Guardo Proveedor
			proveedorRepository.save(Proveedor1);
			
//Creo articulo proveedor, asignandole su proveedor y su articulo
			//falta agregar y solucionar lo de fecha
			ArticuloProveedor articuloProveedor1 = ArticuloProveedor.builder()
					.proveedor(Proveedor1)
					.articulo(articulo1)
					.precioArticuloProveedor(30.0)
					.predeterminado(true)
					.build();

			ArticuloProveedor articuloProveedor2 = ArticuloProveedor.builder()
					.proveedor(Proveedor1)
					.articulo(articulo2)
					.precioArticuloProveedor(50.0)
					.predeterminado(true)
					.build();
//Guardo
			articuloProveedorRepository.save(articuloProveedor1);
			articuloProveedorRepository.save(articuloProveedor2);
//Obtengo la lista de articuloProveedores de proveedor y le agrego el aP que cree recientemente
			Proveedor1.addArticuloProveedor(articuloProveedor1);
			articulo1.addArticuloProveedor(articuloProveedor1);
			Proveedor1.addArticuloProveedor(articuloProveedor2);
			articulo2.addArticuloProveedor(articuloProveedor2);

//Creo OC
			OrdenCompra ordencompra1 = OrdenCompra.builder()
					.estadoOrdenCompra(EstadoOrdenCompra.Preparacion)
					.totalOrden(15000.0)
					.cantidad(200)
					.articulo(articulo1)
					.proveedor(Proveedor1)
					.build();

			ordenCompraRepository.save(ordencompra1);
// Le asigno la OC al articulo
			articulo1.addOrdenCompra(ordencompra1);
//Guardo los cambios, aunque en teoria, se deberia hacer automaticamente por el cascade
			proveedorRepository.save(Proveedor1);
			articuloRepository.save(articulo1);
		};
	}
}

