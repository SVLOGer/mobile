package com.example.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app.models.Building
import com.example.myapplication.databinding.ItemBuildingBinding

class BuildingsAdapter(
    private val onBuildingClick: (Building) -> Unit
) : RecyclerView.Adapter<BuildingsAdapter.BuildingViewHolder>() {

    private var buildingsList: List<Building> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder {
        val binding = ItemBuildingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BuildingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {
        val building = buildingsList[position]
        holder.bind(building)
    }

    override fun getItemCount(): Int = buildingsList.size

    fun submitList(newList: List<Building>) {
        buildingsList = newList
        notifyDataSetChanged()  // Перерисовываем весь список (можно заменить на более оптимальный подход)
    }

    inner class BuildingViewHolder(private val binding: ItemBuildingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(building: Building) {
            binding.name.text = building.name
            binding.count.text = building.count.toString()
            binding.cost.text = "${building.cost} 🍪"
            binding.icon.setImageResource(building.icon)
            binding.root.alpha = if (building.isAvailable) 1.0f else 0.5f
            binding.root.isClickable = building.isAvailable
            binding.root.setOnClickListener { onBuildingClick(building) }
        }
    }
}
