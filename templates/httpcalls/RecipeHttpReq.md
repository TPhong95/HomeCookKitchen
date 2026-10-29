
# RECIPE Controller

## RECIPE:
## GET /api/recipe/{id}

### JSON RESPONSE
```
{
    "cookId": 1,
    "recipeName": "Spaggeti",
    "active": true,
    "pictureId": 0,
    "instanceOf": 0,
    "foodCategory": "ASIAN",
    "gluten": false,
    "crustaceans": false,
    "egg": false,
    "fish": false,
    "peanuts": false,
    "soybeans": false,
    "milk": false,
    "nuts": false,
    "celery": false,
    "mustard": false,
    "sesame": false,
    "sulfites": false,
    "lupin": false,
    "molluscs": false,
    "recipeId": 1
}
```

## POST /api/recipe
### JSON REQUEST
```
{
    "cookId": 1,
    "name": "Spaggeti",
    "active": true,
    "pictureId": 0,
    "instanceOf": 0,
    "foodCategory": "ASIAN",
    "gluten": false,
    "crustaceans": false,
    "egg": false,
    "fish": false,
    "peanuts": false,
    "soybeans": false,
    "milk": false,
    "nuts": false,
    "celery": false,
    "mustard": false,
    "sesame": false,
    "sulfites": false,
    "lupin": false,
    "molluscs": false
}
```
### JSON RESPONSE
```
{
    "cookId": 1,
    "recipeName": "Spaggeti",
    "active": true,
    "pictureId": 0,
    "instanceOf": 0,
    "foodCategory": "ASIAN",
    "gluten": false,
    "crustaceans": false,
    "egg": false,
    "fish": false,
    "peanuts": false,
    "soybeans": false,
    "milk": false,
    "nuts": false,
    "celery": false,
    "mustard": false,
    "sesame": false,
    "sulfites": false,
    "lupin": false,
    "molluscs": false,
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
        "pictureId": 0,
        "instanceOf": 0,
        "foodCategory": "ASIAN",
        "gluten": false,
        "crustaceans": false,
        "egg": false,
        "fish": false,
        "peanuts": false,
        "soybeans": false,
        "milk": false,
        "nuts": false,
        "celery": false,
        "mustard": false,
        "sesame": false,
        "sulfites": false,
        "lupin": false,
        "molluscs": false,
        "recipeId": 1
    },
    {
        "cookId": 1,
        "recipeName": "Allergy soup",
        "active": true,
        "pictureId": 0,
        "instanceOf": 0,
        "foodCategory": "ASIAN",
        "gluten": true,
        "crustaceans": true,
        "egg": true,
        "fish": true,
        "peanuts": true,
        "soybeans": true,
        "milk": true,
        "nuts": true,
        "celery": true,
        "mustard": true,
        "sesame": true,
        "sulfites": true,
        "lupin": true,
        "molluscs": true,
        "recipeId": 2
    }
]
```

## PATCH /api/recipe/{id}
### JSON REQUEST
```
{
        "cookId": 1,
        "name": "Less Allergy soup",
        "active": true,
        "pictureId": 0,
        "instanceOf": 0,
        "foodCategory": "ASIAN",
        "gluten": true,
        "crustaceans": false,
        "egg": true,
        "fish": true,
        "peanuts": true,
        "soybeans": true,
        "milk": true,
        "nuts": true,
        "celery": true,
        "mustard": true,
        "sesame": true,
        "sulfites": true,
        "lupin": true,
        "molluscs": true
    }
```
### JSON RESPONSE
```
{
    "cookId": 1,
    "recipeName": "Less Allergy soup",
    "active": true,
    "pictureId": 0,
    "instanceOf": 0,
    "foodCategory": "ASIAN",
    "gluten": true,
    "crustaceans": true,
    "egg": true,
    "fish": true,
    "peanuts": true,
    "soybeans": true,
    "milk": true,
    "nuts": true,
    "celery": true,
    "mustard": true,
    "sesame": true,
    "sulfites": true,
    "lupin": true,
    "molluscs": true,
    "recipeId": 2
}
```