package com.project.glisemikindeks.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.glisemikindeks.repositories.Repositores
import com.project.glisemikindeks.databinding.CategoryItemBinding
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.model.CategoryModel

class DBCatAdapter (val catArr:ArrayList<CategoryModel>,val repo: Repositores):RecyclerView.Adapter<DBCatAdapter.ViewHolder>() {

    class ViewHolder(val bind: CategoryItemBinding): RecyclerView.ViewHolder(bind.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val category = catArr.get(position)
        val db = DBHelper(holder.bind.root.context)
        holder.bind.apply {
            tvCat.text = category.name
            categoryCard.setOnClickListener {
                if (catArr.get(position).ID == -1){
                    val inputEditTextField = EditText(holder.bind.root.context)
                    val alert = AlertDialog.Builder(root.context)
                    alert.setTitle("Kategori Ekle")
                    alert.setMessage("Kategori Adi")
                    alert.setView(inputEditTextField)
                    alert.setNegativeButton("Iptal",DialogInterface.OnClickListener{dialogInterface, i -> })
                    alert.setPositiveButton("Kaydet" , DialogInterface.OnClickListener{dialogInterface, i ->
                        if (inputEditTextField.text.toString().isEmpty()){
                            Toast.makeText(holder.bind.root.context, "Bos Birakamazsiniz", Toast.LENGTH_SHORT).show()
                        }else{
                            db.addCat(inputEditTextField.text.toString())
                            catArr.add(CategoryModel(catArr.get(position).ID,inputEditTextField.text.toString()))
                            notifyItemChanged(catArr.size-1)

                        }
                    })
                    alert.show()
                    true
                }
                repo.catid.value =  catArr.get(position).ID
                //Diffutil
            }

          categoryCard.setOnLongClickListener {
              val alert = AlertDialog.Builder(root.context)
              alert.setTitle("Silme")
              alert.setNegativeButton("Iptal",DialogInterface.OnClickListener{dialogInterface, i -> })
              alert.setPositiveButton("Sil", DialogInterface.OnClickListener { dialogInterface, i ->
                  val count = db.deleteCat(category.ID!!)
                  if (count > 0) {
                      Toast.makeText(holder.bind.root.context, "Silme işlemi başarılı!", Toast.LENGTH_SHORT).show()
                      catArr.removeAt(position)
                      notifyItemRemoved(position)
                  } else {
                      Toast.makeText(holder.bind.root.context, "Silme işlemi hatası!", Toast.LENGTH_SHORT).show()
                  }
              } )
              alert.show()
              true
          }
       }
    }


    override fun getItemCount(): Int {
        return catArr.size
    }




}