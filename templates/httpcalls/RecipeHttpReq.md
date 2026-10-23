
# RECIPE Controller

## RECIPE:
## GET /api/recipe/{id}

### JSON RESPONSE
```
{
    "cookId": 1,
    "recipeName": "Spaggeti",
    "active": true,
    "productListing": [],
    "recipeId": 1
}
```

## POST /api/recipe
### JSON REQUEST
```
{
    "cookId": 1,
    "name": "Spaggeti",
    "active": true
}
```
### JSON RESPONSE
```
{
    "cookId": 1,
    "recipeName": "Spaggeti",
    "active": true,
    "productListing": null,
    "recipeId": 1
}
```
## GET /api/recipe/all

### JSON RESPONSE
```
[
    {
        "cookId": 1,
        "recipeName": "Spaggeti",
        "active": true,
        "productListing": [],
        "recipeId": 1
    },
    {
        "cookId": 1,
        "recipeName": "Soup",
        "active": true,
        "productListing": [],
        "recipeId": 2
    }
]
```

## PATCH /api/recipe/{id}
### JSON REQUEST
```
{
    "cookId": 1,
    "name": "Bolle",
    "active": true
}
```
### JSON RESPONSE
```
{
    "cookId": 1,
    "recipeName": "Bolle",
    "active": true,
    "productListing": [],
    "recipeId": 1
}
```