package com.example.foodrecipesapp.ui.fragments.detail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import coil.load
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.data.dto.Result
import com.example.foodrecipesapp.databinding.FragmentOverviewBinding
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private lateinit var binding:FragmentOverviewBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOverviewBinding.inflate(layoutInflater)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val recipeBundle : Result? = args?.getParcelable("recipeBundle")
        setupUi(recipeBundle)
    }

    private fun setupUi(recipeBundle: Result?) {
        binding.mainImageView.load(recipeBundle?.image)
        binding.timeTv.text = recipeBundle?.readyInMinutes.toString()
        binding.likeTv.text = recipeBundle?.aggregateLikes.toString()
        binding.titleTv.text = recipeBundle?.title
        recipeBundle?.summary.let {
            val parsedSummary = Jsoup.parse(it).text()
            binding.summaryTv.text = parsedSummary
        }


        setColorOfAttributes(binding.dairyfreeIv,binding.dairyfreeTv,recipeBundle?.dairyFree)
        setColorOfAttributes(binding.veganIv,binding.veganTv,recipeBundle?.vegan)
        setColorOfAttributes(binding.healthyIv,binding.healthyTv,recipeBundle?.veryHealthy)
        setColorOfAttributes(binding.vegetarianIv,binding.vegetarianTv,recipeBundle?.vegetarian)
        setColorOfAttributes(binding.glutenfreeIv,binding.glutenfreeTv,recipeBundle?.glutenFree)
        setColorOfAttributes(binding.cheapIv,binding.cheapTv,recipeBundle?.cheap)
    }

    private fun setColorOfAttributes(imageView: ImageView, textView: TextView, isSuitable: Boolean?) {
        if (isSuitable == true){
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }
    }


}