package com.project.glisemikindeks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.glisemikindeks.R
import com.project.glisemikindeks.Repositores
import com.project.glisemikindeks.adapter.DBAdapter
import com.project.glisemikindeks.adapter.DBCatAdapter
import com.project.glisemikindeks.databinding.FragmentMainBinding
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.model.Food

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding?=null
    private val binding get() = _binding!!
    private lateinit var foodData : ArrayList<Food>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }
    fun saveCheck(){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = DBHelper(requireContext())
        val repo = Repositores()
        val data = db.getCat()
        binding.rcyCat.adapter = DBCatAdapter(data, repo)
        val fd = db.getAll()
        fd.clear()
        repo.catid.observe(viewLifecycleOwner) {
            if (it == 0) {
                foodData = db.getAll()
            } else {
                foodData = db.getCatFood(it)
            }
            val foodAdapter = DBAdapter(foodData)
            binding.rcyFood.adapter = foodAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}