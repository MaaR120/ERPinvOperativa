package com.ERP.invOperativa;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Repositories.ArticuloProveedorRepository;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.FamiliaArticuloRepository;
import com.ERP.invOperativa.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

import static com.ERP.invOperativa.Enum.Modelo.Lote_fijo;

@SpringBootApplication
public class InvOperativaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvOperativaApplication.class, args);
		System.out.println("Estoy funcionando");
	}
}
//
//	@Autowired
//	ArticuloRepository articuloRepository;
//
//	@Autowired
//	FamiliaArticuloRepository familiaArticuloRepository;
//
//	@Autowired
//	ProveedorRepository proveedorRepository;
//
//	@Autowired
//	ArticuloProveedorRepository articuloProveedorRepository;

	//Agregue esto para hacer pruebas
//	@Bean
//	CommandLineRunner init(ArticuloRepository ArticuloRepository1) {
//		return args -> {

//			FamiliaArticulo familia1 = FamiliaArticulo.builder()
//					.nombreFamilia("herramienta")
//					.modelo(Lote_fijo)
//					.build();
//			familiaArticuloRepository.save(familia1);
//
//			Articulo articulo1 = Articulo.builder()
//					.NombreArticulo("Tornillo")
//					.CostoAlmacenamiento(1500.0)
//					.precio(2500.0)
//					.familiaArticulo(familia1)
//					.build();
//			articuloRepository.save(articulo1);
//
//			Articulo articulo2 = Articulo.builder()
//					.NombreArticulo("ruleman")
//					.CostoAlmacenamiento(1500.0)
//					.precio(2500.0)
//					.familiaArticulo(familia1)
//					.build();
//			articuloRepository.save(articulo2);
//
//			Proveedor Proveedor1 = Proveedor.builder()
//					.nombreProveedor("Juan Gonzalez")
//					.build();
//			proveedorRepository.save(Proveedor1);
//
//			Proveedor Proveedor2 = Proveedor.builder()
//					.nombreProveedor("Juan Diaz")
//					.build();
//			proveedorRepository.save(Proveedor2);
//
//
//
////Primer intento de fecha vigente
//			Date FechaVigente = new Date(2024, 9, 21);
////Creo articulo proveedor, asignandole su proveedor y su articulo
//			//falta agregar y solucionar lo de fecha
//			ArticuloProveedor articuloProveedor1 = ArticuloProveedor.builder()
//					.proveedor(Proveedor1)
//					.articulo(articulo1)
//					.precioArticuloProveedor(30.0)
//					.predeterminado(true)
//					.build();
////Guardo
//			articuloProveedorRepository.save(articuloProveedor1);
//
////Asigno otro proveedor distinto al articulo
//			ArticuloProveedor articuloProveedor2 = ArticuloProveedor.builder()
//					.proveedor(Proveedor2)
//					.articulo(articulo1)
//					.precioArticuloProveedor(30.0)
//					.predeterminado(true)
//					.build();
//			articuloProveedorRepository.save(articuloProveedor2);
//
//		};
//		}
//
//	}


