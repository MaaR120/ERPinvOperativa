package com.ERP.invOperativa;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.DetalleVentaRepository;
import com.ERP.invOperativa.Repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Date;
import java.util.List;

@SpringBootApplication
public class InvOperativaApplication implements CommandLineRunner {

	@Autowired
	private ArticuloRepository articuloRepository;

	@Autowired
	private DetalleVentaRepository detalleVentaRepository;

	@Autowired
	private VentaRepository ventaRepository;

	public static void main(String[] args) {
		SpringApplication.run(InvOperativaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Iniciando inicialización de datos...");

		// Crear artículos
		Articulo articulo1 = new Articulo();
		articulo1.setNombreArticulo("Tornillo");
		articulo1.setPrecio(2500.0);
		articuloRepository.save(articulo1);

		System.out.println("Artículo creado: ");
		System.out.println("ID: " + articulo1.getId());
		System.out.println("Nombre: " + articulo1.getNombreArticulo());
		System.out.println("Precio: " + articulo1.getPrecio());

		// Crear venta
		Venta venta = new Venta();
		venta.setFechaFacturacion(new Date(System.currentTimeMillis()));
		venta.setTotalVenta(0);

		// Crear detalles de venta
		DetalleVenta detalleVenta1 = new DetalleVenta();
		detalleVenta1.setArticulo(articulo1);
		detalleVenta1.setCantidad(5);
		detalleVenta1.setSubtotal(articulo1.getPrecio() * detalleVenta1.getCantidad());

		venta.agregarDetalleVenta(detalleVenta1);

		// Calcular total de venta
		venta.setTotalVenta(detalleVenta1.getSubtotal());

		// Guardar venta (esto también guarda los detalles de venta debido a CascadeType.ALL)
		ventaRepository.save(venta);

		// Crear venta
		Venta venta1 = new Venta();
		venta1.setFechaFacturacion(new Date(System.currentTimeMillis()));
		venta1.setTotalVenta(0);

		// Crear detalles de venta
		DetalleVenta detalleVenta2 = new DetalleVenta();
		detalleVenta2.setArticulo(articulo1);
		detalleVenta2.setCantidad(5);
		detalleVenta2.setSubtotal(articulo1.getPrecio() * detalleVenta2.getCantidad());

		venta1.agregarDetalleVenta(detalleVenta2);

		// Calcular total de venta
		venta1.setTotalVenta(detalleVenta2.getSubtotal());

		// Guardar venta (esto también guarda los detalles de venta debido a CascadeType.ALL)
		ventaRepository.save(venta1);

		System.out.println("Venta creada: ");
		System.out.println("ID: " + venta.getId());
		System.out.println("Fecha de facturación: " + venta.getFechaFacturacion());
		System.out.println("Total de venta: " + venta.getTotalVenta());

		for (DetalleVenta detalle : venta.getDetalleVentas()) {
			System.out.println("Detalle de venta creado: ");
			System.out.println("ID: " + detalle.getId());
			System.out.println("Cantidad: " + detalle.getCantidad());
			System.out.println("Subtotal: " + detalle.getSubtotal());
			System.out.println("Articulo: " + detalle.getArticulo().getNombreArticulo());
		}

		System.out.println("Datos inicializados con éxito.");
	}
}
