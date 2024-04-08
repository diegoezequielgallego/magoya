# TP Diego Gallego

1) Para ejecutar esta app simplemente se puede abrir en el intelliJ como un proyecto maven.

2) Ejecutar el archivo docker-compose.yml desde el intelliJ dandole click derecho/Run para crear todas las imagenes de docker necesarias (MONGO, KAFKA, SQL)

3) Ejecutar el servicio con el comando spring-boot:run


dejo algnos ejemplos para copiar y pegar en el postman para testear el servicio

//// crear cuenta
POST : http://localhost:8080/accounts
body:
{
    "name" : "cuenta sueldo1",
    "accountNumber" : "1234"
}

//// consultar balance
GET : http://localhost:8080/accounts/XXX/balance
body:

//// transacción depositar fondos
POST : http://localhost:8080/transactions
body:
{
    "accountId" : 9,
    "transactionType" : "DEPOSIT",
    "amount" : "32.25"
}

//// transacción retirar fondos
POST : http://localhost:8080/transactions
body:
{
    "accountId" : 9,
    "transactionType" : "WITHDRAWAL",
    "amount" : "32.25"
}

//// transacción alerta mayor a 10.000
POST : http://localhost:8080/transactions
body:
{
    "accountId" : 9,
    "transactionType" : "DEPOSIT",
    "amount" : "10032.25"
}




