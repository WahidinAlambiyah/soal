Required

1. Java 11
2. Node 18.16.1
3. PostgreSQL if need run db.sql

RUN Application

1. Clone git
2. Create db myflat and schema soal (PostgreSQL), change application-dev.yml
3. Open cmd into folder project, run mvnw

Jawaban ada di entity soalxsis, bisa login pake admin, masuk ke halaman entities->soalxsis atau ke admnistrator->api pakai swagger

untuk get detail title bisa pakai curl ini

curl -X 'GET' \
  'http://localhost:8080/api/soalxses?title.contains=Bobo&page=0&size=20' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY5MTU0OTM0MX0.3o-A9rPYDakmmZ6RxtOVhrWc7WJj71I4KCO3ZPcCbms5ovhseYyqBPQWxI6MGVv3cW_ZwYOLII3ck8cyanYcww'
