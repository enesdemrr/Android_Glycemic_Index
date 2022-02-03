package com.project.glisemikindeks.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.project.glisemikindeks.R
import com.project.glisemikindeks.databinding.FragmentAddFoodBinding
import com.project.glisemikindeks.db.DBHelper
import java.util.*
import kotlin.collections.ArrayList


class AddFoodFragment : Fragment() {
    private var _binding : FragmentAddFoodBinding? = null
    private val binding get() = _binding!!
    val list = ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        _binding = FragmentAddFoodBinding.inflate(inflater,container,false)
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        val db = DBHelper(requireContext())
        nameCheck()
        calCheck()
        glyCheck()
        karbCheck()
        snipperCheck()
        val allCategories = db.getCat()
        allCategories.forEach {
            list.add(it.name)
        }
        binding.saveBtn.setOnClickListener {
            saveCheck()
        }

        val autoComplate : ArrayAdapter<String> = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,list)
        binding.autoCompleteCat.setAdapter(autoComplate)
        return binding.root
    }
    val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val fragment  = parentFragmentManager.beginTransaction()
            fragment.replace(R.id.layoutFrg,MainFragment())
            fragment.commit()
        }
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
            val cat = db.returnCatId(binding.autoCompleteCat.text.toString()).toString()
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
        binding.etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString() == ""){
                    binding.textNameLayout.helperText = "Bos birakilamaz"
                }
                else{
                    binding.textNameLayout.helperText = null
                }
            }
        })
    }

    private fun calCheck() {
        binding.etCal.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString() == ""){
                    binding.textCalLayout.helperText = "Bos birakilamaz"
                }
                else{
                    binding.textCalLayout.helperText = null
                }
            }
        })
    }
    private fun glyCheck() {
        binding.etGly.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString() == ""){
                    binding.textGlyLayout.helperText = "Bos birakilamaz"
                }
                else{
                    binding.textGlyLayout.helperText= null
                }
            }
        })
    }
    private fun karbCheck() {
        binding.etKarb.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString() == ""){
                    binding.textKarbLayout.helperText = "Bos birakilamaz"
                }
                else{
                    binding.textKarbLayout.helperText= null
                }
            }

        })
    }

    private fun snipperCheck() {
        binding.autoCompleteCat.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString() == ""){
                    binding.textSpinnerLayout.helperText = "Bos birakilamaz"
                }
                else{
                    binding.textSpinnerLayout.helperText = null
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}