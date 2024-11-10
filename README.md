# Coffee Machine API

This project uses the API available at [https://date.nager.at/Api](https://date.nager.at/Api).

## Available API Endpoints

You can view all available paths in the Swagger UI at the following address:
[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

## Adding a New Recipe

Before adding a new recipe, make sure the ingredients exist in the system. If the ingredients don't exist yet, you will need to add them first.

### Example of Adding a New Recipe

To add a new recipe, use the following JSON structure:

```json
{
  "name": "DRINK_NAME",
  "ingredients": [
    {
      "ingredient": {
        "name": "INGREDIENT_NAME"
      },
      "quantity": 19
    },
    {
      "ingredient": {
        "name": "INGREDIENT_NAME_2"
      },
      "quantity": 121
    }
  ]
}
