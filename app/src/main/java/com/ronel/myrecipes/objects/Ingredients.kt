package com.ronel.myrecipes.objects

import com.squareup.moshi.Json

data class Ingredients(
    @Json(name="id")
    val id: Int,
    @Json(name="aisle")
    val aisle: String,
    @Json(name="image")
    val image: String,
    @Json(name="consistency")
    val consistency: String,
    @Json(name="name")
    val name: String,
    @Json(name="nameClean")
    val nameClean: String,
    @Json(name="original")
    val original: String,
    @Json(name="originalName")
    val originalName: String,
    @Json(name="amount")
    val amount: Double,
    @Json(name="unit")
    val unit: String
)

/*
* {
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
* */