-- Richard Padilla
Create database EXAMEN;
use EXAMEN;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE estudiantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    nota1 DOUBLE NOT NULL,
    nota2 DOUBLE NOT NULL,
    nota3 DOUBLE NOT NULL,
    nota4 DOUBLE NOT NULL,
    nota5 DOUBLE NOT NULL
);

insert into usuarios(username, password) values ('richard','padilla22');


select * from Usuarios;
select * from estudiantes;
