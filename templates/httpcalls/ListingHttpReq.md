
# ProductListing Controller

## ProductListing:
## GET /api/productListing/{id}

### JSON RESPONSE
```
{
    "quantity": 10,
    "unitPrice": 14.0,
    "productListingId": 1,
    "recipeId": {
        "cookId": 1,
        "recipeName": "Bolle",
        "active": true,
        "recipeId": 1
    }
}
```

## POST /api/productListing
### JSON REQUEST
```
{
    "recipeId": 1,
    "quantity": 10,
    "unitPrice": 14.99
}
```
### JSON RESPONSE
```
{
    "quantity": 10,
    "unitPrice": 14.0,
    "productListingId": 1,
    "recipeId": {
        "cookId": 1,
        "recipeName": "Bolle",
        "active": true,
        "recipeId": 1
    }
}
```
## GET /api/productListing/all

### JSON RESPONSE
```
[
    {
        "quantity": 10,
        "unitPrice": 14.0,
        "productListingId": 1,
        "recipeId": {
            "cookId": 1,
            "recipeName": "Bolle",
            "active": true,
            "recipeId": 1
        }
    },
    {
        "quantity": 14,
        "unitPrice": 33.0,
        "productListingId": 2,
        "recipeId": {
            "cookId": 1,
            "recipeName": "Soup",
            "active": true,
            "recipeId": 2
        }
    }
]
```

## PATCH /api/productListing/{id}
### JSON REQUEST
```
{
    "recipeId": 1,
    "quantity": 44,
    "unitPrice": 23.99
}

```
### JSON RESPONSE
```
{
    "quantity": 44,
    "unitPrice": 23.99,
    "productListingId": 1,
    "recipeId": {
        "cookId": 1,
        "recipeName": "Bolle",
        "active": true,
        "recipeId": 1
    }
}
```