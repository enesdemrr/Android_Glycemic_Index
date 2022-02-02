package com.project.glisemikindeks.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.project.glisemikindeks.databinding.FragmentAddFoodBinding
import com.project.glisemikindeks.db.DBHelper
import com.project.glisemikindeks.model.CategoryModel
import kotlin.collections.ArrayList


class AddFoodFragment : Fragment() {
    private var _binding : FragmentAddFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val db = DBHelper(requireContext())
        _binding = FragmentAddFoodBinding.inflate(inflater,container,false)
        nameCheck()
        calCheck()
        glyCheck()
        karbCheck()
        snipperCheck()
        binding.saveBtn.setOnClickListener {
            saveCheck()
        }
        val list = ArrayList<String>()
        val allCategories = db.getCat()
        allCategories.forEach {
            list.add(it.ID.toString()+"-${it.name}")
        }
        
        val autoComplate : ArrayAdapter<String> = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,list)
        binding.autoCompleteCat.setAdapter(autoComplate)
        return binding.root
    }


    private fun saveCheck() {
        val db = DBHelper(requireContext())
        var nameCheck = binding.textNameLayout.helperText == null
        var calCheck = binding.textCalLayout.helperText == null
        var glyCheck = binding.textGlyLayout.helperText == null
        var karbChec = binding.textKarbLayout.helperText == null
        var snipperCheck = binding.textSpinnerLayout.helperText == null
        if (nameCheck && calCheck && glyCheck && karbChec && snipperCheck){
            val name = binding.etName.text.toString()
            val gly = binding.etGly.text.toString()
            val cal = binding.etCal.text.toString()
            val karb = binding.etKarb.text.toString()
            val cat = binding.autoCompleteCat.text[0].toString()
            try {
                db.addFood(name,gly,karb,cal,cat)
                Toast.makeText(requireContext(), "Urun Basariyla Kayit Edildi", Toast.LENGTH_SHORT).show()
                activity?.supportFragmentManager?.beginTransaction()?.replace(this.id,MainFragment())?.commit()
            }catch (e : Exception){
                Log.e("hata","$e")
                Toast.makeText(requireContext(), "Urun kayit edilirken hata olustu $e", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun nameCheck() {
        binding.etName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.textNameLayout.helperText = validName()
            }
        }
    }
    private fun validName(): String? {
        val nameTxt = binding.etName.text.toString()
        if (nameTxt.isEmpty()) {
            return "Gecersiz Kalori"
        }
        return null
    }

    private fun calCheck() {
        binding.etCal.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.textCalLayout.helperText = validCal()
            }
        }
    }

    private fun validCal(): String? {
        val calTxt = binding.etGly.text.toString()
        if (calTxt.isEmpty()) {
            return "Bos Birakilamaz"
        }
        return null
    }

    private fun glyCheck() {
        binding.etGly.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.textGlyLayout.helperText = validGly()
            }
        }
    }

    private fun validGly(): String? {
        val glyTxt = binding.etGly.text.toString()
        if (glyTxt.isEmpty()) {
            return "Bos Birakilamaz"
        }
        return null
    }

    private fun karbCheck() {
        binding.etKarb.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.textKarbLayout.helperText = validKarb()
            }
        }
    }

    private fun validKarb(): String? {
        val karbTxt = binding.etKarb.text.toString()
        if (karbTxt.isEmpty()) {
            return "Bos Birakilamaz"
        }
        return null
    }

    private fun snipperCheck() {
        binding.autoCompleteCat.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.textSpinnerLayout.helperText = validSnipper()
            }
        }
    }

    private fun validSnipper(): String? {
        val snipperTxt = binding.autoCompleteCat.text.toString()
        if (snipperTxt.isEmpty()) {
            return "Bos Birakilamaz"
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}