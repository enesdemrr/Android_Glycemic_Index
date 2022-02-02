package com.project.glisemikindeks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.glisemikindeks.Repositores
import com.project.glisemikindeks.databinding.CategoryItemBinding
import com.project.glisemikindeks.databinding.ItemFoodBinding
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.model.CategoryModel
import com.project.glisemikindeks.model.Food

class DBCatAdapter (val catArr:ArrayList<CategoryModel>,val repo:Repositores):RecyclerView.Adapter<DBCatAdapter.ViewHolder>() {

    class ViewHolder(val bind: CategoryItemBinding): RecyclerView.ViewHolder(bind.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val category = catArr.get(position)
        holder.bind.apply {
            tvCat.text = category.name
            categoryCard.setOnClickListener {
                repo.catid.value =  catArr.get(position).ID
                //Diffutil
            }
        }
    }


    override fun getItemCount(): Int {
        return catArr.size
    }




}