package com.project.glisemikindeks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.glisemikindeks.MainActivity
import com.project.glisemikindeks.Repositores
import com.project.glisemikindeks.databinding.ItemFoodBinding
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.model.CategoryModel
import com.project.glisemikindeks.model.Food

class DBAdapter(val foodArr:ArrayList<Food>):RecyclerView.Adapter<DBAdapter.ViewHolder>() {

    class ViewHolder(val bind: ItemFoodBinding) : RecyclerView.ViewHolder(bind.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodArr.get(position)
        holder.bind.apply {
            tvName.text = food.name
            tvCal.text = food.cal
            tvKarb.text = food.karb
            tvGly.text = food.glysemic
            cardName.setOnClickListener {

            }
        }
    }
    override fun getItemCount(): Int {
        return foodArr.size
    }
}