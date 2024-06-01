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
					.build();
			familiaArticuloRepository.save(familia1);

			Articulo articulo1 = Articulo.builder()
					.NombreArticulo("Tornillo")
					.precio(2500.0)
					.familiaArticulo(familia1)
					.build();
			articuloRepository.save(articulo1);

		};
	}

}


