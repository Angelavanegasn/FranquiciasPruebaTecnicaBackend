## Franquicias API – Backend Reactivo con Spring WebFlux

## Descripción del Proyecto

Este proyecto implementa una API REST reactiva utilizando **Spring WebFlux** para la gestión de franquicias. Cada franquicia puede tener múltiples sucursales, y cada sucursal gestiona su propio catálogo de productos con control de inventario.

## Funcionalidades Principales

- Crear una nueva franquicia.
- Registrar una nueva sucursal en una franquicia existente.
- Agregar productos al catálogo de una sucursal.
- Eliminar productos del catálogo.
- Modificar el stock de un producto.
- Consultar el producto con mayor stock por sucursal dentro de una franquicia.

### Funcionalidades Adicionales (Plus)

- Actualización del nombre de franquicias, sucursales y productos.

## Pruebas Unitarias

Las pruebas unitarias están implementadas con **JUnit 5** y **Reactor Test** para validar la lógica de negocio en un entorno reactivo.

## Se ejecutan todas las pruebas unitarias

![image](https://github.com/user-attachments/assets/0580a43b-e170-4e72-b9a1-21a9110614f5)



## Tecnologías Utilizadas

- **Java 12**
- **Spring Boot 2.1.7**
- **Spring WebFlux** (Programación reactiva)
- **MongoDB reactivo** (Persistencia NoSQL)
- **Docker**
- **JUnit 5**
- **Terraform** (Infraestructura como Código)
- **AWS EC2**

## Docker

La aplicación está preparada para ejecutarse en un contenedor Docker.

### Construcción de la Imagen

![image](https://github.com/user-attachments/assets/bffc8ade-d7f1-4b5c-9e10-d2a7ea633f73)

docker build -t franquicias-api .
![image](https://github.com/user-attachments/assets/c9597dc0-41dd-4a35-b777-4cf92cbb3dcc)


### Ejecución del Contenedor

docker run -p 8080:8080 franquicias-api

## Resultado
Una vez ejecutado docker-compose up --build, puedes acceder al backend en:
http://localhost:8080

![image](https://github.com/user-attachments/assets/b1462f77-f149-4598-a474-20ff2ac9ceda)

## Despliegue en AWS EC2 con Terraform (IaC)

El backend puede desplegarse en una instancia EC2 utilizando **Terraform** para automatizar la provisión de infraestructura.

### Recursos Proporcionados por Terraform

- Instancia EC2 (Amazon Linux 2 o Ubuntu)
- Grupo de seguridad para exponer el puerto 8080
- Variables configurables (región, clave SSH, etc.)

### Estructura del Proyecto Terraform


infra/
├── main.tf
├── variables.tf
├── outputs.tf


### Ejemplo `main.tf`


provider "aws" {
  region = var.region
}

resource "aws_instance" "franquicia_api" {
  ami           = var.ami_id
  instance_type = "t2.micro"
  key_name      = var.key_name

  vpc_security_group_ids = [aws_security_group.allow_http.id]

  user_data = <<-EOF
              #!/bin/bash
              yum update -y
              amazon-linux-extras install docker -y
              service docker start
              usermod -a -G docker ec2-user
              docker run -d -p 8080:8080 your-dockerhub-user/franquicias-api
              EOF

  tags = {
    Name = "franquicia-backend"
  }
}

resource "aws_security_group" "allow_http" {
  name        = "allow_http"
  description = "Allow HTTP inbound traffic"

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}


### Pasos para Desplegar

1. Instalar [Terraform](https://www.terraform.io/).
2. Configurar credenciales de AWS:
 
   aws configure
  
3. Inicializar el proyecto:

   cd infra
   terraform init

4. Aplicar la infraestructura:
   
   terraform apply
   

Una vez desplegada, la API estará disponible en el puerto 8080 de la IP pública de la EC2.

## Endpoints Principales

### Franquicias

| Método | Endpoint                             | Descripción                  |
|--------|--------------------------------------|------------------------------|
| POST   | `/api/franquicia`                   | Crear nueva franquicia       |
| GET    | `/api/franquicia`                   | Listar todas las franquicias |
| GET    | `/api/franquicia/{id}`              | Obtener franquicia por ID    |

###  Sucursales

| Método | Endpoint                             | Descripción                            |
|--------|--------------------------------------|----------------------------------------|
| POST   | `/api/sucursal`                      | Crear nueva sucursal                   |
| GET    | `/api/sucursal`                      | Listar todas las sucursales            |
| GET    | `/api/sucursal/{id}`                 | Obtener sucursal por ID                |

### Productos

| Método | Endpoint                              | Descripción                            |
|--------|---------------------------------------|----------------------------------------|
| POST   | `/api/producto`                       | Agregar nuevo producto a una sucursal |
| GET    | `/api/producto`                       | Listar todos los productos              |
| GET    | `/api/producto/{id}`                  | Obtener producto por ID                 |
| PUT    | `/api/producto/{id}`                  | Actualizar información de un producto  |
| DELETE | `/api/producto/{id}`                  | Eliminar producto                       |
