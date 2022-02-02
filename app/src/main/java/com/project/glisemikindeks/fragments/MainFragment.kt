package com.project.glisemikindeks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.glisemikindeks.R
import com.project.glisemikindeks.repositories.Repositores
import com.project.glisemikindeks.adapter.DBAdapter
import com.project.glisemikindeks.adapter.DBCatAdapter
import com.project.glisemikindeks.databinding.FragmentMainBinding
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.model.CategoryModel
import com.project.glisemikindeks.model.Food

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding?=null
    private val binding get() = _binding!!
    private lateinit var foodData : ArrayList<Food>
    private lateinit var catData : ArrayList<CategoryModel>
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
        catData.add(CategoryModel(-1,"Yeni Kategori Ekle"))
        catData.add(CategoryModel(0,"Tum Urunler"))
        db.getCat().forEach {
            catData.add(it)
        }
        binding.rcyCat.adapter = DBCatAdapter(catData, repo)
        repo.catid.observe(viewLifecycleOwner) {
            if (it == 0 || it == -1) {
                foodData = db.getAll()
            } else {
                foodData = db.getCatFood(it)
            }
            val foodAdapter = DBAdapter(foodData)
            foodAdapter.notifyDataSetChanged()
            binding.rcyFood.adapter = foodAdapter

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}