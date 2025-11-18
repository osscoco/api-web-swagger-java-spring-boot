// -- Installer un projet Java Spring Boot from scratch -- //

1) Se rendre sur https://start.spring.io/

2) Configuration suivante :

-> Project : Maven
-> Language : Java
-> Spring Boot : 3.5.7 (exemple)
-> Project Metadata : ...
-> Packaging : Jar
-> Java : 17 (exemple)
-> Dependencies :
	- Spring Web
	- Spring Security
	- Spring Data JPA
	- MySQL Driver
	- Flyway Migration
	- Lombok

3) Générer pour télécharger un .zip

4) Ouvrir le dossier (dans le .zip) avec l'IDE IntelliJ IDEA Community Edition

// -- Instancier le service MySQL via Docker -- //

1) Créer un fichier .env dans le dossier ./docker-db et coller le contenu suivant :

```
MYSQL_ROOT_PASSWORD=ToUpDaTePaSsWoRd
MYSQL_DATABASE=ApIsEcUrEdB
```

2) Créer un fichier script-apisecuredb-user-grant.sql dans le dossier ./docker-db/docker/mysql-init et coller le contenu suivant :

```
-- Utilisateur avec droits SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP, INDEX
CREATE USER IF NOT EXISTS 'ToUpDaTeUsErR'@'%' IDENTIFIED BY 'ToUpDaTePaSsWoRd';
REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'ToUpDaTeUsErR'@'%';
REVOKE SHOW DATABASES ON *.* FROM 'ToUpDaTeUsErR'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, DROP, INDEX, REFERENCES ON ApIsEcUrEdB.* TO 'ToUpDaTeUsErR'@'%';

FLUSH PRIVILEGES;
```

3) Lancer le logiciel : Docker Desktop (Télécharger et Installer si ce n'est pas fait)

4) Ouvrir un terminal dans le dossier ./docker-db et lancer la commande suivante : docker compose up

5) Se rendre sur l'interface PhpMyAdmin à l'adresse local : http://localhost:8080/

6) Se connecter avec les identifiants présents dans le fichier ./docker-db/docker/mysql-init/script-apisecuredb-user-grant.sql : 

```
Login=ToUpDaTeUsErR
Password=ToUpDaTePaSsWoRd
```

7) La base de données est présente et sans tables pour le moment

8) Dans le fichier ./src/main/resources/application.properties, coller le contenu suivant :

```
# APP NAME
spring.application.name=api-secure

# MYSQL
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/ApIsEcUrEdB?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=ToUpDaTeUsErR
spring.datasource.password=ToUpDaTePaSsWoRd
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# HIBERNATE ORM
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

# LIQUIBASE
spring.liquibase.enabled=false
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yaml
```

9) Créer un fichier liquibase.properties dans le dossier ./src/main/resources coller le contenu suivant :

```
# LIQUIBASE
outputChangeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml
changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml

# MYSQL
url=jdbc:mysql://127.0.0.1:3306/ApIsEcUrEdB?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
username=ToUpDaTeUsErR
password=ToUpDaTePaSsWoRd
driver=com.mysql.cj.jdbc.Driver
```

// -- Fonctionnement Migrations Models MySQL (Après création de la base de données vide) -- //

# Pré requis :
- Supprimer le fichier ./src/main/resources/db/changelog/db.changelog-master.yaml s'il existe déjà

3) S'assurer que dans application.properties il y a :
- spring.jpa.hibernate.ddl-auto=create
- spring.liquibase.enabled=false

// Crée les tables dans MySQL automatiquement
4) Run le fichier ./src/main/java/com/eclubmaven/api_secure/ApiSecureApplication

// Commande à executer pour générer le fichier ./src/main/resources/db/changelog/db.changelog-master.yaml
// Analyse les tables déjà présentes dans MySQL → génère un fichier YAML (migration)
5) Ouvrir un terminal dans le dossier racine du projet et lancer la commande suivante : mvn liquibase:generateChangeLog

// Applique le fichier YAML à la base (migrate)
6) Ouvrir un terminal dans le dossier racine du projet et lancer la commande suivante : mvn liquibase:update

7) S'assurer que dans application.properties il y a :
- spring.jpa.hibernate.ddl-auto=validate
- spring.liquibase.enabled=true

// À chaque démarrage → Liquibase applique les migrations automatiquement
4) Run le fichier ./src/main/java/com/eclubmaven/api_secure/ApiSecureApplication

// -- URL BACKEND SWAGGER -- //
http://localhost:8080/swagger-ui/index.html
