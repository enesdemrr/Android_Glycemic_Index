package com.project.glisemikindeks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.glisemikindeks.R
import com.project.glisemikindeks.databinding.FragmentAddCategoryBinding
import com.project.glisemikindeks.databinding.FragmentAddFoodBinding
import com.project.glisemikindeks.db.DBHelper

class AddCategoryFragment : Fragment() {
    private var _binding : FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?):View{
        _binding = FragmentAddCategoryBinding.inflate(inflater,container,false)
        catCheck()
        binding.btnCatSave.setOnClickListener{
            saveCat()
        }
        return binding.root
    }
    fun saveCat(){
        val db = DBHelper(requireContext())
        val catCheck = binding.textCategoryLayout.helperText == null
        if (catCheck){
            val name = binding.etCategory.text.toString()
            try {
                db.addCat(name)
                Toast.makeText(requireContext(), "Kategori Basariyla eklendi", Toast.LENGTH_SHORT).show()
                activity?.supportFragmentManager?.beginTransaction()?.replace(this.id,MainFragment())?.commit()

            }catch (e : Exception){
                Toast.makeText(requireContext(), "Kategori eklenirken Hata Meydana Geldi $e", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun catCheck() {
        binding.etCategory.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.textCategoryLayout.helperText = validName()
            }
        }
    }
    private fun validName(): String? {
        val nameTxt = binding.etCategory.text.toString()
        if (nameTxt.isEmpty()) {
            return "Bos Birakilamaz"
        }
        return null
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}