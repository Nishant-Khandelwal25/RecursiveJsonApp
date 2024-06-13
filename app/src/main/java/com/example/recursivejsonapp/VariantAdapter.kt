package com.example.recursivejsonapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView

class VariantAdapter(
    private val variantList: List<Variant>,
    private val variantSelectedListener: OnVariantSelectedListener
) : RecyclerView.Adapter<VariantAdapter.VariantViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_variant, parent, false)
        return VariantViewHolder(view)
    }

    override fun onBindViewHolder(holder: VariantViewHolder, position: Int) {
        val variant = variantList[position]
        holder.rbVariant.text = variant.name
        holder.rbVariant.isChecked = position == selectedPosition

        if (holder.rbVariant.isChecked) variantSelectedListener.onVariantSelected(variant.priceAdjustment)

        holder.rbVariant.setOnClickListener {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
            variantSelectedListener.onVariantSelected(variant.priceAdjustment)
        }
    }

    override fun getItemCount(): Int {
        return variantList.size
    }

    class VariantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rbVariant: RadioButton = itemView.findViewById(R.id.rbVariant)
    }

    interface OnVariantSelectedListener {
        fun onVariantSelected(priceAdjustment: Double)
    }
}
