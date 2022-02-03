package com.project.glisemikindeks.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.project.glisemikindeks.R
import com.project.glisemikindeks.databinding.ItemFoodBinding
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.model.Food
import com.project.glisemikindeks.repositories.Repositores

class DBAdapter(val foodArr:ArrayList<Food>):RecyclerView.Adapter<DBAdapter.ViewHolder>() {

    class ViewHolder(val bind: ItemFoodBinding) : RecyclerView.ViewHolder(bind.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db = DBHelper(holder.bind.root.context)
        val food = foodArr.get(position)
        holder.bind.apply {
            tvName.text = food.name
            tvCal.text = food.cal
            tvKarb.text = food.karb
            tvGly.text = food.glysemic
            cardName.setOnLongClickListener {
                val alert = AlertDialog.Builder(root.context)
                alert.setTitle("Silme")
                alert.setNegativeButton("Iptal", DialogInterface.OnClickListener{ dialogInterface, i -> })
                alert.setPositiveButton("Sil", DialogInterface.OnClickListener { dialogInterface, i ->
                    val count = db.deleteFood(food.ID)
                    if (count > 0) {
                        Toast.makeText(holder.bind.root.context, "Silme işlemi başarılı!", Toast.LENGTH_SHORT).show()
                        foodArr.removeAt(position)
                        notifyItemRemoved(position)
                    } else {
                        Toast.makeText(holder.bind.root.context, "Silme işlemi hatası!", Toast.LENGTH_SHORT).show()
                    }
                } )
                alert.show()
                true
            }
            setFood.setOnClickListener {
                val v = LayoutInflater.from(root.context).inflate(R.layout.update_item,null)
                val list = ArrayList<String>()
                val allCategories = db.getCat()
                val spinner = v.findViewById<AutoCompleteTextView>(R.id.UpCat)
                allCategories.forEach {
                    list.add(it.ID.toString()+"-${it.name}")
                }
                val spinnerAd : ArrayAdapter<String> = ArrayAdapter(root.context,android.R.layout.simple_spinner_dropdown_item,list)
                spinner.setAdapter(spinnerAd)

                v.findViewById<EditText>(R.id.etUpName).setText(food.name)
                v.findViewById<EditText>(R.id.etUpGly).setText(food.glysemic)
                v.findViewById<EditText>(R.id.etUpCal).setText(food.cal)
                v.findViewById<EditText>(R.id.etUpKarb).setText(food.karb)
                val alert = AlertDialog.Builder(root.context)
                alert.setView(v)
                    .setNegativeButton("Iptal",DialogInterface.OnClickListener{dialogInterface, i ->})
                    .setPositiveButton("Guncelle", DialogInterface.OnClickListener { dialogInterface, i ->
                        val name = v.findViewById<EditText>(R.id.etUpName).text.toString()
                        val gly = v.findViewById<EditText>(R.id.etUpGly).text.toString()
                        val cal = v.findViewById<EditText>(R.id.etUpCal).text.toString()
                        val karb = v.findViewById<EditText>(R.id.etUpKarb).text.toString()
                        val cat = spinner.text[0].toString()
                        if (name.isNotEmpty() && gly.isNotEmpty() && cal.isNotEmpty() && karb.isNotEmpty()){
                            db.updateFood(food.ID,name,gly,karb,cal,cat)
                            Toast.makeText(root.context , "Basariyla Guncellendi", Toast.LENGTH_SHORT).show()
                            notifyDataSetChanged()
                            list.clear()
                        }
                        else{
                            Toast.makeText(root.context , "Bos Birakamazsiniz", Toast.LENGTH_SHORT).show()
                        }

                    })
                alert.show()
                notifyDataSetChanged()
            }
        }
        if(food.glysemic.toInt()<50){
            holder.bind.tvGly
        }
        else if(food.glysemic.toInt()<70){

        }
        else{

        }
    }


    override fun getItemCount(): Int {
        return foodArr.size
    }
}