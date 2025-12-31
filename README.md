# ERP – Gestión de Stock y Ventas de una Empresa

Este proyecto fue desarrollado como trabajo integrador de la materia **Investigacion Operativa** (4to año) de la carrera Ingeniería en Sistemas de Información.

El prototipo permite gestionar **artículos, proveedores, órdenes de compra y ventas**, e incorpora cálculos/funcionalidades de **modelos de inventario** (ej. lote fijo / lote a intervalo fijo) y **predicción de demanda** (promedio móvil / promedio ponderado).

### Objetivos

- Centralizar la **gestión de stock** y la información de artículos.
- Facilitar el flujo de **compras y ventas** (órdenes, registro y seguimiento).
- Aplicar conceptos de **Investigación Operativa**: modelos de inventario y métricas asociadas.
- Incorporar **predicción de demanda** para apoyar decisiones de reposición.

## Tecnologías utilizadas
- Java  
- Spring Boot  
- Thymeleaf  
- Bootstrap  
- JPA / Hibernate  
- Base de datos H2

## Módulos del sistema
- **Maestro de artículos:** administración de productos y sus características.
- **Inventarios:** control de stock, punto de pedido y artículos a reponer.
- **Compras:** gestión de órdenes de compra y proveedores.
- **Ventas:** registro de ventas y cálculo de totales.
- **Demanda:** apoyo al análisis de demanda y planificación.

## Estructura del proyecto
```text
src/
└── main/
    ├── java/
    │   └── dominio/
    │       ├── controllers/      # Endpoints y flujo entre vistas/servicios
    │       ├── services/         # Lógica de negocio + cálculos (inventario, predicción)
    │       ├── repositories/     # Persistencia de datos 
    │       ├── dto/              # Objetos de transferencia (DTOs)
    │       ├── entities/         # Modelo del dominio (Artículo, Venta, OrdenCompra, etc.)
    │       ├── enums/            # Enumeraciones (Modelo, EstadoOrden, MetodoPrediccion)
    │       └── exceptions/       # Excepciones y manejo de errores
    │
    └── resources/
        ├── templates/            # Vistas Thymeleaf (pantallas y formularios)
        └── application.properties # Configuración de la app
```
## Capturas del sistema
Algunas vistas del prototipo, incluyendo navegación principal, gestión de artículos, órdenes de compra y artículos a reponer.
<p align="center">
  <img width="80%" height="694" alt="image" src="https://github.com/user-attachments/assets/73686d1d-5b86-4934-9106-454c80497f9c" />
</p>
<p align="center">
  <img width="80%" height="576" alt="image" src="https://github.com/user-attachments/assets/dfef9e75-ea01-4d47-83e9-0ca51796fb16" />
</p>
<p align="center">
  <img width="80%" height="560" alt="image" src="https://github.com/user-attachments/assets/231d1b19-5a05-4480-9f4b-fb91cd105638" 
</p>
<p align="center">
  <img width="80%" height="460" alt="image" src="https://github.com/user-attachments/assets/485ffa18-80cd-4ef7-b5a8-d94b30b1d981" />
</p>

## Integrantes del grupo
Martin Lihue · Paula Rodriguez · Giuliana Ozan · Juan Ignacio Diaz



