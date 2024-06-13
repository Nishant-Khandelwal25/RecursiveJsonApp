package com.example.recursivejsonapp

data class Item(
    val baseItem: BaseItem
)

data class BaseItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val groups: List<Group>,
    val variants: List<Variant>
)

data class Group(
    val id: Int,
    val name: String,
    val addons: List<Addon>,
    val groups: List<Group>? = null // Recursive property
)

data class Addon(
    val id: Int,
    val name: String,
    val price: Double,
    val groups: List<Group>? = null // Recursive property
)

data class Variant(
    val id: Int,
    val name: String,
    val priceAdjustment: Double,
    val groups: List<Group>
)
