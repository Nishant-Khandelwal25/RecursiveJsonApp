package com.example.recursivejsonapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), AddonAdapter.OnPriceChangeListener,
    VariantAdapter.OnVariantSelectedListener {
    private lateinit var item: Item

    private lateinit var tvBaseItemName: TextView
    private lateinit var tvBaseItemDescription: TextView
    private lateinit var tvTotalPrice: TextView
    private lateinit var rvVariants: RecyclerView
    private lateinit var rvGroups: RecyclerView

    private var totalPrice: Double = 0.0
    private var variantPriceAdjustment: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item = getData()

        tvBaseItemName = findViewById(R.id.tvBaseItemName)
        tvBaseItemDescription = findViewById(R.id.tvBaseItemDescription)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        rvVariants = findViewById(R.id.rvVariants)
        rvGroups = findViewById(R.id.rvGroups)

        tvBaseItemName.text = item.baseItem.name
        tvBaseItemDescription.text = item.baseItem.description

        totalPrice = item.baseItem.price
        updateTotalPrice()

        val variantAdapter = VariantAdapter(item.baseItem.variants, this)
        rvVariants.adapter = variantAdapter
        rvVariants.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val variantsAndBaseItemGroupList = item.baseItem.groups.toMutableList()
        item.baseItem.variants.forEach {
            variantsAndBaseItemGroupList += it.groups
        }
        val groupAdapter = GroupAdapter(variantsAndBaseItemGroupList, this)
        rvGroups.adapter = groupAdapter
        rvGroups.layoutManager = LinearLayoutManager(this)
    }

    override fun onPriceChange(price: Double, isAdded: Boolean) {
        totalPrice += if (isAdded) price else -price
        updateTotalPrice()
    }

    override fun onVariantSelected(priceAdjustment: Double) {
        variantPriceAdjustment = priceAdjustment
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val finalPrice = totalPrice + variantPriceAdjustment
        tvTotalPrice.text = String.format("Total Price: $%.2f", finalPrice)
    }

    private fun getData(): Item {
        return Item(
            BaseItem(
                id = 1,
                name = "Pizza",
                description = "Delicious pizza with your choice of toppings",
                price = 10.00,
                groups = listOf(
                    Group(
                        id = 101, name = "Crust", addons = listOf(
                            Addon(id = 201, name = "Thin Crust", price = 0.00),
                            Addon(id = 202, name = "Thick Crust", price = 1.00)
                        )
                    ), Group(
                        id = 102, name = "Toppings", addons = listOf(
                            Addon(
                                id = 211, name = "Pepperoni", price = 1.50, groups = listOf(
                                    Group(
                                        id = 301, name = "Extra Pepperoni", addons = listOf(
                                            Addon(id = 401, name = "Double Pepperoni", price = 2.00)
                                        )
                                    )
                                )
                            ), Addon(id = 212, name = "Mushrooms", price = 1.00)
                        )
                    )
                ),
                variants = listOf(
                    Variant(
                        id = 10,
                        name = "Vegetarian Pizza",
                        priceAdjustment = -1.00,
                        groups = listOf(
                            Group(
                                id = 111, name = "Veggie Toppings", addons = listOf(
                                    Addon(id = 221, name = "Onions", price = 0.50), Addon(
                                        id = 222,
                                        name = "Green Peppers",
                                        price = 0.75,
                                        groups = listOf(
                                            Group(
                                                id = 311, name = "Spicy Peppers", addons = listOf(
                                                    Addon(
                                                        id = 411, name = "Jalapenos", price = 0.25
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }
}