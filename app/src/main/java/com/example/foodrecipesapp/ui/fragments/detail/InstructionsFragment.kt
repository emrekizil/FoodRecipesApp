package com.example.foodrecipesapp.ui.fragments.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.foodrecipesapp.data.dto.Result
import com.example.foodrecipesapp.databinding.FragmentInstructionsBinding


class InstructionsFragment : Fragment() {

    private lateinit var binding:FragmentInstructionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstructionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
    }

    private fun setupWebView() {
        val args = arguments
        val bundle:Result? = args?.getParcelable("recipeBundle")

        binding.webView.webViewClient = object : WebViewClient() {}
        val webUrl: String? = bundle?.sourceUrl
        if (webUrl != null) {
            binding.webView.loadUrl(webUrl)
        }

    }

}