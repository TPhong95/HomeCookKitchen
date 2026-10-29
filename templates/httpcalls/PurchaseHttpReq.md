
# Purchase Controller

## PURCHASE:
## GET /api/purchase/{id}

### JSON RESPONSE
```
{
    "customerId": 1,
    "id": 1,
    "purchaseEvent": [
        {
            "id": 1,
            "listingInfoDto": {
                "cookId": 1,
                "chefName": "Chef",
                "recipeListingId": 1,
                "recipeName": "Spaggeti",
                "unitPrice": 14.0
            },
            "quantity": 3
        },
        {
            "id": 2,
            "listingInfoDto": {
                "cookId": 1,
                "chefName": "Chef",
                "recipeListingId": 2,
                "recipeName": "Soup",
                "unitPrice": 20.0
            },
            "quantity": 2
        }
    ],
    "timestamp": "2026-10-23T11:54:13.090788",
    "totalPrice": 82.0
}
```

## POST /api/purchase
### JSON REQUEST
```
{
    "customerId": 1,
        "purchaseEvents":[
            {
                "productListingId":1,
                "quantity": 3
            },
            {
                "productListingId":2,
                "quantity": 2
            }
        ]
}
```
### JSON RESPONSE
```
{
    "customerId": 1,
    "id": 1,
    "purchaseEvent": [
        {
            "id": 1,
            "listingInfoDto": {
                "cookId": 1,
                "chefName": "Chef",
                "recipeListingId": 1,
                "recipeName": "Spaggeti",
                "unitPrice": 14.0
            },
            "quantity": 3
        },
        {
            "id": 2,
            "listingInfoDto": {
                "cookId": 1,
                "chefName": "Chef",
                "recipeListingId": 2,
                "recipeName": "Soup",
                "unitPrice": 20.0
            },
            "quantity": 2
        }
    ],
    "timestamp": "2026-10-23T11:54:13.090787713",
    "totalPrice": 82.0
}
```

