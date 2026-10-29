
# Chef Controller

## CHEF:
## GET /api/chef/{id}

### JSON RESPONSE
```
{
    "id": 1,
    "name": "Bob",
    "email": "Bob@gmail.com",
    "phoneNumber": "69420671",
    "addresses": []
}
```

## POST /api/chef
### JSON REQUEST
```
{
    "name": "Bob",
    "email": "Bob@gmail.com",
    "phoneNumber": "69420671"
}
```
### JSON RESPONSE
```
{
    "id": 1,
    "name": "Bob",
    "email": "Bob@gmail.com",
    "phoneNumber": "69420671",
    "addresses": []
}
```
## GET /api/chef

### JSON RESPONSE
```
[
    {
        "id": 1,
        "name": "Bob",
        "email": "Bob@gmail.com",
        "phoneNumber": "69420671",
        "addresses": []
    },
    {
        "id": 2,
        "name": "John",
        "email": "John@gmail.com",
        "phoneNumber": "67676767",
        "addresses": []
    }
]
```
## ADDRESS:
## POST /api/chef/address/{chefId}
### JSON REQUEST
```
{
    "street": "Bolle veien 6",
    "city": "Bolle By",
    "country": "Bolleland"
}
```
### JSON RESPONSE
```
[
    {
        "id": 1,
        "street": "Bolle veien 6",
        "city": "Bolle By",
        "country": "Bolleland",
        "chefId": 1
    }
]
```