### Build
#### Start postgres
```
jdbc:postgresql://localhost:5432/load_data_db
```
#### Run maven tasks
```shell script
mvn clean package
```
---
### Run with postgres
Add environment variables:
```
db.user.name
db.user.password
db.connection.url
```
Then run
```shell script
java -jar -Dspring.profiles.active=PROM build/libs/app.jar
```
### Run with H2
```shell script
java -jar -Dspring.profiles.active=DEV build/libs/app.jar
```
H2 console starts at
```
http://localhost:8080/h2-console
```
---
### Use
#### Generate user list with N rows
```
http://localhost:8080/download/{N}
```
#### Load users to database from zip-file
```
http://localhost:8080/upload/
```
Example
```
POST http://localhost:8080/file/upload
Content-Type: multipart/form-data; boundary=WebAppBoundary

--WebAppBoundary
Content-Disposition: form-data; name="file"; filename="ts.zip"
```
### Swagger
```
http://localhost:8080/swagger-ui.html
```