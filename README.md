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
### Run
```shell script
java -jar build/libs/app.jar
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