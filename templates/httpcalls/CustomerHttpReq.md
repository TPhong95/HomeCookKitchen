
# Customer Controller

## CUSTOMER:
## GET /customer/{id}
### JSON RESPONSE
```
{
    "name": "Bob",
    "email": "Bob@gmail.com",
    "customerId": 1
}
```

## POST /customer
### JSON REQUEST
```
{
    "name": "Bob",
    "email": "Bob@gmail.com"
}
```
### JSON RESPONSE
```
{
    "name": "Bob",
    "email": "Bob@gmail.com",
    "customerId": 1
}
```
## GET /customer/all

### JSON RESPONSE
```
[
    {
        "name": "Bob",
        "email": "Bob@gmail.com",
        "customerId": 1
    },
    {
        "name": "John",
        "email": "John@gmail.com",
        "customerId": 2
    }
]
```
## PATCH /customer
### JSON REQUEST
```
{
    "name": "Bob2",
    "email": "Bob2@gmail.com"
}
```
### JSON RESPONSE
```
{
    "name": "Bob2",
    "email": "Bob2@gmail.com",
    "customerId": 1
}
```