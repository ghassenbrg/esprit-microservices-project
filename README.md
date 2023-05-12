# build jars and docker images
run build.bat
# start docker containers ordered
run docker-compose up -d
# create test buyer user on user-management-service
send post request on http://localhost:9191/api/users/register with body

{
	"firstName":"test",
	"lastName":"user",
	"role":"buyer",
	"email":"abcd@abcd.com",
	"password":"test",
	"username":"test",
	"address" :"testaddress, monastir,tunisia"
}
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/af0ac729-3329-4ab5-8df1-30f9ac1b1c5c)

# retrieve token for user test
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/4a9d7503-e4c8-4d95-bae2-43451333a013)
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/fa07f718-8dd7-4951-ba44-72bf138f6768)
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/af9dfde9-5fa5-4bb1-8124-92f7a21ea5b0)
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/c50f33cf-5278-481c-bc3b-52fa8ff66272)

# invoke endpoints from gateway using the retrived token
curl --location --request GET 'http://localhost:9191/api/users' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'
