package com.ERP.invOperativa;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Repositories.*;
import com.ERP.invOperativa.Services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

import static com.ERP.invOperativa.Enum.Modelo.Lote_fijo;

@SpringBootApplication
public class InvOperativaApplication {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    FamiliaArticuloRepository familiaArticuloRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    ArticuloProveedorRepository articuloProveedorRepository;

    public static void main(String[] args) {
        SpringApplication.run(InvOperativaApplication.class, args);
        System.out.println("Programa en Ejecucion");
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            System.out.println("------------------------Estoy Funcionando------------------------");

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
                    .NombreArticulo("ruleman")
                    .CostoAlmacenamiento(1500.0)
                    .precio(2500.0)
                    .familiaArticulo(familia1)
                    .build();
            articuloRepository.save(articulo2);

            Proveedor Proveedor1 = Proveedor.builder()
                    .nombreProveedor("Juan Gonzalez")
                    .build();
            proveedorRepository.save(Proveedor1);

            Proveedor Proveedor2 = Proveedor.builder()
                    .nombreProveedor("Juan Diaz")
                    .build();
            proveedorRepository.save(Proveedor2);



//Primer intento de fecha vigente
            Date FechaVigente = new Date(2024, 9, 21);
//Creo articulo proveedor, asignandole su proveedor y su articulo
            //falta agregar y solucionar lo de fecha
            ArticuloProveedor articuloProveedor1 = ArticuloProveedor.builder()
                    .proveedor(Proveedor1)
                    .articulo(articulo1)
                    .precioArticuloProveedor(30.0)
                    .predeterminado(true)
                    .build();
//Guardo
            articuloProveedorRepository.save(articuloProveedor1);

//Asigno otro proveedor distinto al articulo
            ArticuloProveedor articuloProveedor2 = ArticuloProveedor.builder()
                    .proveedor(Proveedor2)
                    .articulo(articulo1)
                    .precioArticuloProveedor(30.0)
                    .predeterminado(true)
                    .build();
            articuloProveedorRepository.save(articuloProveedor2);
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
    }
}