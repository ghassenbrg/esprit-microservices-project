# build jars and docker images
run build.bat
# start docker containers ordered
run docker-compose up -d
# create test user on user-management-service
send post request on http://localhost:9191/api/users/register with body
{
	"firstName":"test",
	"lastName":"user",
	"role":"buyer",
	"email":"abcd@abcd.com",
	"password":"test",
	"username":"test"
}
# retrieve token for user test

# invoke dummy endpoint from gateway using the retrived token
curl --location --request GET 'http://localhost:9191/dummy' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'
