package com.example.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.models.Building
import com.example.app.models.BuildingType
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBuildingBinding
import com.example.myapplication.ui.GameState

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

    fun submitList(buildings: List<Building>) {
        buildingsList = buildings
        notifyDataSetChanged()
    }

    inner class BuildingViewHolder(private val binding: ItemBuildingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(building: Building) {
            binding.name.text = building.name
            binding.count.text = building.count.toString()
            binding.cost.text = "${building.cost} 🍪"
            binding.icon.setImageResource(getIconForBuildingType(building.type))
            binding.root.alpha = if (building.isAvailable) 1.0f else 0.5f
            binding.root.isClickable = building.isAvailable
            binding.root.setOnClickListener { onBuildingClick(building) }
        }
    }

    // Метод для определения иконки по типу строения
    private fun getIconForBuildingType(type: BuildingType): Int {
        return when (type) {
            BuildingType.CLICKER -> R.drawable.ic_clicker
            BuildingType.GRANDMA -> R.drawable.ic_grandma
            BuildingType.FARM -> R.drawable.ic_farm
            BuildingType.MINE -> R.drawable.ic_mine
            BuildingType.FABRIC -> R.drawable.ic_fabric
            BuildingType.BANK -> R.drawable.ic_bank
            BuildingType.TEMPLE -> R.drawable.ic_temple
            BuildingType.WIZARD_TOWER -> R.drawable.ic_wizard_tower
            BuildingType.ROCKET -> R.drawable.ic_rocket
        }
    }
}
