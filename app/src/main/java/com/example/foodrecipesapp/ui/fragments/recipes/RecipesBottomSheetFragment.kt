package com.example.foodrecipesapp.ui.fragments.recipes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.databinding.FragmentRecipesBottomSheetBinding
import com.example.foodrecipesapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipesapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RecipesBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<RecipesViewModel>()

    private lateinit var binding:FragmentRecipesBottomSheetBinding

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId =0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner){
            mealTypeChip = it.selectedMealType
            dietTypeChip = it.selectedDietType
            updateChip(it.selectedMealTypeId,binding.mealTypeChipGroup)
            updateChip(it.selectedDietTypeId,binding.dietTypeChipGroup)
        }
        observeChipCheckedListener()
        binding.applyBtn.setOnClickListener {
            viewModel.saveMealAndDiet(mealTypeChip,mealTypeChipId,dietTypeChip,dietTypeChipId)
            val action = RecipesBottomSheetFragmentDirections.actionRecipesBottomSheetFragmentToRecipesFragment(true)
            findNavController().navigate(action)
        }
    }

    private fun updateChip(selectedId: Int, chipGroup: ChipGroup) {
        if(selectedId != 0){
            try {
                chipGroup.findViewById<Chip>(selectedId).isChecked = true
            }catch (e:Exception){
                Log.d("RecipesBottomSheet",e.message.toString())
            }
        }
    }

    private fun observeChipCheckedListener() {
        binding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId= checkedIds[0]

        }
        binding.dietTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])
            val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId= checkedIds[0]

        }


    }


}