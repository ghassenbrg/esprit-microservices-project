# build jars and docker images
run build.bat
# start docker containers ordered
run docker-compose up -d
# create test user on keycloak
login via http://localhost:8080/auth/admin/master/console/ with credentials admin admin and create user test in the realm espri
# retrieve token for user test
curl -L -X POST \
  'http://localhost:8080/auth/realms/ESPRIT/protocol/openid-connect/token' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'client_id=public-client' \
  --data-urlencode 'grant_type=password' \
  --data-urlencode 'scope=email openid profile' \
  --data-urlencode 'username=test' \
  --data-urlencode 'password=test'
# invoke dummy endpoint from gateway using the retrived token
curl --location --request GET 'http://localhost:9191/dummy' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'
