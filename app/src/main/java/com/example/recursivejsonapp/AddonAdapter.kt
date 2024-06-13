package com.example.recursivejsonapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AddonAdapter(
    private val addonList: List<Addon>,
    private val priceChangeListener: OnPriceChangeListener
) : RecyclerView.Adapter<AddonAdapter.AddonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_addon, parent, false)
        return AddonViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddonViewHolder, position: Int) {
        val addon = addonList[position]
        holder.cbAddon.text = addon.name
        holder.tvAddonPrice.text = String.format("$%.2f", addon.price)

        holder.cbAddon.setOnCheckedChangeListener { _, isChecked ->
            priceChangeListener.onPriceChange(addon.price, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return addonList.size
    }

    class AddonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cbAddon: CheckBox = itemView.findViewById(R.id.cbAddon)
        val tvAddonPrice: TextView = itemView.findViewById(R.id.tvAddonPrice)
    }

    interface OnPriceChangeListener {
        fun onPriceChange(price: Double, isAdded: Boolean)
    }
}
