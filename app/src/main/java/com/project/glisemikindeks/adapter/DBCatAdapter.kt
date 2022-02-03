package com.project.glisemikindeks.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.glisemikindeks.R
import com.project.glisemikindeks.repositories.Repositores
import com.project.glisemikindeks.databinding.CategoryItemBinding
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.model.CategoryModel

class DBCatAdapter(val catArr: ArrayList<CategoryModel>, val repo: Repositores) :
    RecyclerView.Adapter<DBCatAdapter.ViewHolder>() {

    class ViewHolder(val bind: CategoryItemBinding) : RecyclerView.ViewHolder(bind.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = catArr.get(position)
        val db = DBHelper(holder.bind.root.context)
        holder.bind.apply {
            tvCat.text = category.name
            categoryCard.setOnClickListener {
                repo.catid.value = catArr.get(position).ID
                if (category.ID == -1){
                    addCat(holder.bind,position)
                }
                //Diffutil
            }

            categoryCard.setOnLongClickListener {
                if (category.ID != 0) {
                    val alert = AlertDialog.Builder(root.context)
                    alert.setTitle("Kategoriyi icindeki tum urunlerle birlikte silinecektir!")
                    alert.setMessage("Silmek Istiyor musunuz?")
                    alert.setNegativeButton(
                        "Iptal",
                        DialogInterface.OnClickListener { dialogInterface, i -> })
                    alert.setPositiveButton(
                        "Sil",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            val count = db.deleteCat(category.ID!!)
                            val count2= db.deleteFoodCat(category.ID.toString())
                            if (count > 0 || count2> 0) {
                                Toast.makeText(
                                    holder.bind.root.context,
                                    "Silme işlemi başarılı!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                catArr.removeAt(position)
                                notifyItemRemoved(position)
                                repo.catid.value = catArr.get(0).ID
                            } else {
                                Toast.makeText(
                                    holder.bind.root.context,
                                    "Silme işlemi hatası!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    alert.show()
                }
                true

            }
        }
    }

    private fun addCat(bind: CategoryItemBinding,position: Int) {
        val db = DBHelper(bind.root.context)
        val v = LayoutInflater.from(bind.root.context).inflate(R.layout.add_cat,null)
        v.findViewById<EditText>(R.id.etCatNewName)
        val alert = AlertDialog.Builder(bind.root.context)
        alert.setView(v)
            .setNegativeButton("Iptal",DialogInterface.OnClickListener{dialogInterface, i ->})
            .setPositiveButton("Kaydet", DialogInterface.OnClickListener { dialogInterface, i ->
                val name = v.findViewById<EditText>(R.id.etCatNewName).text.toString()
                if (name.isNotEmpty()){
                    db.addCat(name)
                    Toast.makeText(bind.root.context , "Basariyla Eklendi", Toast.LENGTH_SHORT).show()
                    catArr.add(CategoryModel(catArr.get(position).ID,name))
                    notifyItemChanged(catArr.size-1)
                }
                else{
                    Toast.makeText(bind.root.context , "Bos Birakamazsiniz", Toast.LENGTH_SHORT).show()
                }

            })
        alert.show()
        repo.catid.value =  catArr.get(position).ID
    }


    override fun getItemCount(): Int {
        return catArr.size
    }


}