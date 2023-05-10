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
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/4a9d7503-e4c8-4d95-bae2-43451333a013)
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/af9dfde9-5fa5-4bb1-8124-92f7a21ea5b0)
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/0d5f7aaa-b22d-4ca5-a7cf-84d3d8298859)
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/c50f33cf-5278-481c-bc3b-52fa8ff66272)

# invoke dummy endpoint from gateway using the retrived token
curl --location --request GET 'http://localhost:9191/dummy' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'
