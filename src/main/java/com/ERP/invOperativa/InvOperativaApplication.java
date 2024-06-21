package com.ERP.invOperativa;

import com.ERP.invOperativa.Entities.*;
import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import com.ERP.invOperativa.Repositories.*;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Repositories.ArticuloProveedorRepository;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.FamiliaArticuloRepository;
import com.ERP.invOperativa.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.VentaRepository;
import com.ERP.invOperativa.Services.VentaService;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.Venta;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.ERP.invOperativa.Enum.Modelo.Lote_fijo;


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

//
//	@Autowired
//	FamiliaArticuloRepository familiaArticuloRepository;
//
//	@Autowired
//	OrdenCompraRepository ordenCompraRepository;
//
//
//	@Autowired
//	ProveedorRepository proveedorRepository;
//
//	@Autowired
//	ArticuloProveedorRepository articuloProveedorRepository;
//
//	//Agregue esto para hacer pruebas
//	@Bean
//	CommandLineRunner init(ArticuloRepository ArticuloRepository1,OrdenCompraRepository OrdenCompraRepository1 ) {
//		return args -> {
//
//			FamiliaArticulo familia1 = FamiliaArticulo.builder()
//					.nombreFamilia("herramienta")
//					.modelo(Lote_fijo)
//					.build();
//			familiaArticuloRepository.save(familia1);
//
//	Articulo articulo1 = Articulo.builder()
//					.NombreArticulo("Tornillo")
//					.CostoAlmacenamiento(1500.0)
//					.Stock(800)
//					.precio(2500.0)
//					.familiaArticulo(familia1)
//					.build();
//		articuloRepository.save(articulo1);
//
//			Articulo articulo2 = Articulo.builder()
//					.NombreArticulo("ruleman")
//					.CostoAlmacenamiento(1500.0)
//					.Stock(400)
//					.precio(2500.0)
//					.familiaArticulo(familia1)
//					.build();
//			articuloRepository.save(articulo2);
//
//
//
////Creo proveedor
//			Proveedor Proveedor1 = Proveedor.builder()
//					.nombreProveedor("Juan Gonzalez")
//					.build();
//
//			Proveedor Proveedor2 = Proveedor.builder()
//					.nombreProveedor("Will Smith")
//					.build();
//
////Guardo Proveedor
//			proveedorRepository.save(Proveedor1);
//			proveedorRepository.save(Proveedor2);
//
////Creo articulo proveedor, asignandole su proveedor y su articulo
//			//falta agregar y solucionar lo de fecha
//			ArticuloProveedor articuloProveedor1 = ArticuloProveedor.builder()
//					.proveedor(Proveedor1)
//					.articulo(articulo1)
//					.precioArticuloProveedor(30.0)
//					.predeterminado(true)
//					.build();
//
//			ArticuloProveedor articuloProveedor2 = ArticuloProveedor.builder()
//					.proveedor(Proveedor1)
//					.articulo(articulo2)
//					.precioArticuloProveedor(50.0)
//					.predeterminado(true)
//					.build();
//			ArticuloProveedor articuloProveedor3 = ArticuloProveedor.builder()
//					.proveedor(Proveedor2)
//					.articulo(articulo1)
//					.precioArticuloProveedor(550.0)
//					.predeterminado(false)
//					.build();
////Guardo
//			articuloProveedorRepository.save(articuloProveedor1);
//			articuloProveedorRepository.save(articuloProveedor2);
//			articuloProveedorRepository.save(articuloProveedor3);
////Obtengo la lista de articuloProveedores de proveedor y le agrego el aP que cree recientemente
//			Proveedor1.addArticuloProveedor(articuloProveedor1);
//			articulo1.addArticuloProveedor(articuloProveedor1);
//			Proveedor1.addArticuloProveedor(articuloProveedor2);
//			articulo2.addArticuloProveedor(articuloProveedor2);
//			Proveedor2.addArticuloProveedor(articuloProveedor3);
//			articulo1.addArticuloProveedor(articuloProveedor3);
//
////Creo OC
//			OrdenCompra ordencompra1 = OrdenCompra.builder()
//					.estadoOrdenCompra(EstadoOrdenCompra.Preparacion)
//					.totalOrden(15000.0)
//					.cantidad(200)
//					.articulo(articulo1)
//					.proveedor(Proveedor1)
//					.build();
//
//			ordenCompraRepository.save(ordencompra1);
//// Le asigno la OC al articulo
//			articulo1.addOrdenCompra(ordencompra1);
////Guardo los cambios, aunque en teoria, se deberia hacer automaticamente por el cascade
//			proveedorRepository.save(Proveedor1);
//			articuloRepository.save(articulo1);
//			// Crear artículos
//			Articulo articulo1 = new Articulo();
//			articulo1.setNombreArticulo("Tornillo");
//			articulo1.setPrecio(2500.0);
//			articuloRepository.save(articulo1);
//
//			Articulo articulo2 = new Articulo();
//			articulo2.setNombreArticulo("Tuerca");
//			articulo2.setPrecio(1000);
//			articuloRepository.save(articulo2);
//
//			// Crear detalles de venta
//			DetalleVenta detalleVenta1 = new DetalleVenta();
//			detalleVenta1.setArticulo(articulo1);
//			detalleVenta1.setCantidad(5);
//			detalleVenta1.setSubtotal(detalleVenta1.getCantidad() * detalleVenta1.getArticulo().getPrecio());
//
//			List<DetalleVenta> detallesVenta = new ArrayList<>();
//			detallesVenta.add(detalleVenta1);
//
//			// Crear venta
//			Venta venta = new Venta();
//			venta.setDetalleVentas(detallesVenta);
//			venta.setTotalVenta(detallesVenta.getLast().getSubtotal());
//			venta.setFechaFacturacion(new Date(System.currentTimeMillis()));
//
//			ventaRepository.save(venta);


		};



