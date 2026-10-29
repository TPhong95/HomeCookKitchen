
# ProductListing Controller

## ProductListing:
## GET /api/productListing/{id}

### JSON RESPONSE
```
{
    "recipeId": 1,
    "chefId": 1,
    "active": true,
    "quantity": 10,
    "unitPrice": 14.99,
    "purchaseDeadline": "2026-11-01T18:00:00",
    "readyBy": "2026-11-03T12:00:00",
    "createdAtTime": "2026-10-28T15:17:29.217928",
    "productListingId": 1
}
```

## POST /api/productListing
### JSON REQUEST
```
{
    "recipeId": 1,
    "chefId" : 1,
    "active": true,
    "quantity": 10,
    "unitPrice": 14.99,
    "purchaseDeadline": "2026-11-01T18:00:00",
    "readyBy": "2026-11-03T12:00:00"
}
```
### JSON RESPONSE
```
{
    "recipeId": 1,
    "chefId": 1,
    "active": true,
    "quantity": 10,
    "unitPrice": 14.99,
    "purchaseDeadline": "2026-11-01T18:00:00",
    "readyBy": "2026-11-03T12:00:00",
    "createdAtTime": "2026-10-28T15:17:29.217927682",
    "productListingId": 1
}
```
## GET /api/productListing/all

### JSON RESPONSE
```
[
    {
        "recipeId": 1,
        "chefId": 1,
        "active": true,
        "quantity": 10,
        "unitPrice": 14.99,
        "purchaseDeadline": "2026-11-01T18:00:00",
        "readyBy": "2026-11-03T12:00:00",
        "createdAtTime": "2026-10-28T15:17:29.217928",
        "productListingId": 1
    },
    {
        "recipeId": 2,
        "chefId": 1,
        "active": true,
        "quantity": 14,
        "unitPrice": 22.99,
        "purchaseDeadline": "2026-11-01T18:00:00",
        "readyBy": "2026-11-03T12:00:00",
        "createdAtTime": "2026-10-28T15:18:49.839314",
        "productListingId": 2
    }
]
```

## PATCH /api/productListing/{id}
### JSON REQUEST
```
{
  "recipeId": 1,
  "chefId": 1,
  "active": true,
  "quantity": 10,
  "unitPrice": 149.99,
  "purchaseDeadline": "2026-11-01T18:00:00",
  "readyBy": "2026-11-03T12:00:00"
}

```
### JSON RESPONSE
```
{
    "recipeId": 1,
    "chefId": 1,
    "active": true,
    "quantity": 10,
    "unitPrice": 149.99,
    "purchaseDeadline": "2026-11-01T18:00:00",
    "readyBy": "2026-11-03T12:00:00",
    "createdAtTime": "2026-10-28T15:17:29.217928",
    "productListingId": 1
}
```