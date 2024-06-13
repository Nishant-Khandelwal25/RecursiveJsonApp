package com.example.recursivejsonapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GroupAdapter(
    private val groupList: List<Group>,
    private val priceChangeListener: AddonAdapter.OnPriceChangeListener
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groupList[position]
        holder.tvGroupName.text = group.name

        when (group.id) {
            101 -> setupCrustGroup(holder, group.addons)
            else -> setupDefaultGroup(holder, getAllAddons(group))
        }
    }

    private fun setupCrustGroup(holder: GroupViewHolder, addons: List<Addon>) {
        holder.rgCrustOptions.setOnCheckedChangeListener(null)
        holder.rgCrustOptions.removeAllViews()
        addons.forEach { addon ->
            val radioButton = RadioButton(holder.itemView.context)
            radioButton.text = addon.name
            radioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    priceChangeListener.onPriceChange(addon.price, true)
                } else {
                    priceChangeListener.onPriceChange(addon.price, false)
                }
            }
            holder.rgCrustOptions.addView(radioButton)
        }
        holder.rvAddons.visibility = View.GONE
    }

    private fun setupDefaultGroup(holder: GroupViewHolder, addons: List<Addon>) {
        val addonAdapter = AddonAdapter(addons, priceChangeListener)
        holder.rvAddons.adapter = addonAdapter
        holder.rvAddons.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.rvAddons.visibility = View.VISIBLE
    }

    private fun getAllAddons(group: Group): List<Addon> {
        val allAddons = mutableListOf<Addon>()
        addAllAddons(allAddons, group)
        return allAddons
    }

    private fun addAllAddons(allAddons: MutableList<Addon>, group: Group) {
        allAddons.addAll(group.addons)
        group.addons.forEach { addon ->
            addon.groups?.forEach { nestedGroup ->
                addAllAddons(allAddons, nestedGroup)
            }
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGroupName: TextView = itemView.findViewById(R.id.tvGroupName)
        val rvAddons: RecyclerView = itemView.findViewById(R.id.rvAddons)
        val rgCrustOptions: RadioGroup = itemView.findViewById(R.id.rgCrustOptions)
    }
}
