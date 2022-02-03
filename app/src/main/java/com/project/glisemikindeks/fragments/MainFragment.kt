package com.project.glisemikindeks.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.glisemikindeks.R
import com.project.glisemikindeks.repositories.Repositores
import com.project.glisemikindeks.adapter.DBAdapter
import com.project.glisemikindeks.adapter.DBCatAdapter
import com.project.glisemikindeks.databinding.ActivityMainBinding.bind
import com.project.glisemikindeks.databinding.ActivityMainBinding.inflate
import com.project.glisemikindeks.databinding.FragmentMainBinding
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.model.CategoryModel
import com.project.glisemikindeks.model.Food
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding?=null
    private val binding get() = _binding!!
    private lateinit var foodData : ArrayList<Food>
    private lateinit var catData : ArrayList<CategoryModel>
    var counter = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        catData = ArrayList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = DBHelper(requireContext())
        val repo = Repositores()
       // val data = db.getCat()
        catData.add(CategoryModel(-1,"Yeni Kategori"))
        catData.add(CategoryModel(0,"Tum Besinler"))
        db.getCat().forEach {
            catData.add(it)
        }
        binding.rcyCat.adapter = DBCatAdapter(catData, repo)
        repo.catid.observe(viewLifecycleOwner) {
            if (it == 0 || it == -1) {
                foodData = db.getAll()
            } else {
                foodData = db.getCatFood(it.toString())
            }
            val foodAdapter = DBAdapter(foodData)
            foodAdapter.notifyDataSetChanged()
            binding.rcyFood.adapter = foodAdapter

        }
      binding.etSearch.addTextChangedListener(object : TextWatcher{
          override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

          }

          override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
              var filteredResult = ArrayList<Food>()
              if(binding.etSearch.text.isNotEmpty()){
                  for(i in 0..foodData.size-1){
                      if(foodData.get(i).name!!.lowercase().contains(p0.toString().lowercase()))
                          filteredResult.add(foodData[i])
                  }
                  binding.rcyFood.adapter = DBAdapter(filteredResult)
              }
          }
          override fun afterTextChanged(p0: Editable?) {
          }

      })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId

        if(id == R.id.sort_bar){
            if (counter == 0){
                sortArtan()
                counter=1
            }
            else if(counter==1){
                sortAzalan()
                counter=0
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun sortAzalan() {
        foodData.sortBy { it.glysemic.toInt() }
        binding.rcyFood.adapter?.notifyDataSetChanged()
    }
    private fun sortArtan(){
        foodData.sortBy { it.glysemic.toInt() }
        foodData.reverse()
        binding.rcyFood.adapter?.notifyDataSetChanged()
    }

    val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val fragment  = getParentFragmentManager().beginTransaction()
            fragment.replace(R.id.layoutFrg,MainFragment())
            fragment.commit()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}