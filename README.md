
# Diego Saez Challenge



## Entorno de desarrollo utilizado

* Eclipse 2021-12 (4.22.0)
* Java openjdk 11.0.13 2021-10-19
* Docker version 20.10.12, build e91ed57
## Build and Deploy

### Precondiciones:
* Java 11 instalado.
* Docker instalado.

### Pasos

* Clonar el repositorio.
* Por consola dirigirse a la carpeta del proyecto.
* Ejecutar el arhivo build_docker_image.sh.
El archivo compilará el código, generará una imagen docker, la subirá a dockerhub y luego levantará una imagen para base de datos postgres y la aplicación.


## Utilización

* Una vez compilado, generada las imágenes y levantadas.
* Dirigirse a la siguiente url en el navegador: http://localhost:8080/swagger-ui/#/. En la misma se puede visualizar un swagger con todos los endpoints a disposición para ser ejecutados.
Importante: Para utilizar los endpoints securizados, primero se debe hacer un login para obtener el token y luego se debe colocar en la sección de swagger destinado para ello, con el siguiente formato Bearer xxxxxxxxxx
