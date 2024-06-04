package com.ERP.invOperativa;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.FamiliaArticulo;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.FamiliaArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.ERP.invOperativa.Enum.Modelo.Lote_fijo;

@SpringBootApplication
public class InvOperativaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvOperativaApplication.class, args);
		System.out.println("Estoy funcionando");
	}
	@Autowired
	ArticuloRepository articuloRepository;

	@Autowired
	FamiliaArticuloRepository familiaArticuloRepository;

//Agregue esto para hacer pruebas
	@Bean
	CommandLineRunner init(ArticuloRepository ArticuloRepository1) {
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

		};
	}

}


