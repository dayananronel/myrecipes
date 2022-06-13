package com.ronel.myrecipes.objects

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Food(
    @Json(name="aggregateLikes")
    val aggregateLikes: Int,
    @Json(name="pricePerServing")
    val pricePerServing: Double,
    @Json(name="extendedIngredients")
    val extendedIngredients: List<Ingredients>,
    @Json(name="id")
    val id: Int,
    @Json(name="title")
    val title: String,
    @Json(name="readyInMinutes")
    val readyInMinutes: Int,
    @Json(name="servings")
    val servings: Int,
    @Json(name="sourceUrl")
    val sourceUrl: String?,
    @Json(name="image")
    val image: String?,
    @Json(name="summary")
    val summary: String,
    @Json(name="dishTypes")
    val dishTypes: List<String>,
    @Json(name="spoonacularSourceUrl")
    val spoonacularSourceUrl: String
    )



/*
        "aggregateLikes": 39,
        "pricePerServing": 39.5,
        extendedIngredients :
        "id": 664306,
        "title": "Vanilla Coconut Snowball Cupcakes",
		"readyInMinutes": 45,
		"servings": 13,
		"sourceUrl": "https://www.foodista.com/recipe/3777N2GX/vanilla-coconut-snowball-cupcakes",
		"openLicense": -1,
		"image": "https://spoonacular.com/recipeImages/664306-556x370.jpg",
		"imageType": "jpg",
		"summary": "The recipe Vanilla Coconut Snowball Cupcakes can be made <b>in about around 45 minutes</b>. For <b>52 cents per serving</b>, you get a dessert that serves 13. Watching your figure? This lacto ovo vegetarian recipe has <b>412 calories</b>, <b>4g of protein</b>, and <b>21g of fat</b> per serving. 9 people found this recipe to be scrumptious and satisfying. This recipe is typical of American cuisine. It is brought to you by Foodista. Head to the store and pick up regular milk, , salt, and a few other things to make it today. All things considered, we decided this recipe <b>deserves a spoonacular score of 12%</b>. This score is not so tremendous. Users who liked this recipe also liked <a href=\"https://spoonacular.com/recipes/coconut-snowball-cupcakes-598417\">Coconut Snowball Cupcakes</a>, <a href=\"https://spoonacular.com/recipes/hersheys-coconut-creme-kisses-chocolate-cupcakes-with-vanilla-coconut-frosting-492912\">Hershey’s Coconut Creme Kisses & Chocolate Cupcakes with Vanilla Coconut Frosting</a>, and <a href=\"https://spoonacular.com/recipes/vanilla-bean-coconut-cupcakes-with-coconut-frosting-190375\">Vanilla Bean-Coconut Cupcakes with Coconut Frosting</a>.",



Extended ingredients
{
				"id": 20081,
				"aisle": "Baking",
				"image": "flour.png",
				"consistency": "SOLID",
				"name": "flour",
				"nameClean": "wheat flour",
				"original": "1 1/2 cups All-Purpose Flour",
				"originalName": "All-Purpose Flour",
				"amount": 1.5,
				"unit": "cups",
				"meta": [
					"all-purpose"
				],
				"measures": {
					"us": {
						"amount": 1.5,
						"unitShort": "cups",
						"unitLong": "cups"
					},
					"metric": {
						"amount": 354.882,
						"unitShort": "ml",
						"unitLong": "milliliters"
					}
				}
			}
*/
