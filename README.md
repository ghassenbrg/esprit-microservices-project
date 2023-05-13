# build jars and docker images
run build.bat
# start docker containers
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
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/2c4b595d-b543-4017-b409-fb1dfabae03b)

# retrieve token for user test
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/4a9d7503-e4c8-4d95-bae2-43451333a013)
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/fa07f718-8dd7-4951-ba44-72bf138f6768)
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/af9dfde9-5fa5-4bb1-8124-92f7a21ea5b0)
![image](https://github.com/mazenaissa/esprit-microservices-project/assets/25006500/c50f33cf-5278-481c-bc3b-52fa8ff66272)

# invoke endpoints from gateway using the retrieved token
curl --location --request GET 'http://localhost:9191/api/users' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'

curl --location --request GET 'http://localhost:9191/api/users/sellers' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'

POST Dummy products
curl --location --request POST 'http://localhost:9191/product-catalog/api/products/loadDummyProducts' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'

Check products for user with id 4
curl --location --request GET 'http://localhost:9191/product-catalog/api/products/seller/4' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'

Delete user (seller) ==> it will delete it's products after communicating with product-catalog-service
curl --location --request DELETE 'http://localhost:9191/api/users/4' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'

Check again products for user with id 4 ==> empty array
curl --location --request GET 'http://localhost:9191/product-catalog/api/products/seller/4' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'

curl --location --request GET 'http://localhost:9191/api/inventory' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'

curl --location --request GET 'http://localhost:9191/product-catalog/api/products/P123456' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'

POST 'http://localhost:9191/product-catalog/api/products/P123456' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer xxxx...'
body
{
    "orderId" : 1,
    "email": "dhiua99@gmail.com",
	"userId" : 1,
    "amount" : 100,
	"cardHolderName" : "Mohamed Dhia Hachem",
	"cardNo" : "0956643654325",
	"cvv" : "879",
	"expDate" : "12/12"
}
