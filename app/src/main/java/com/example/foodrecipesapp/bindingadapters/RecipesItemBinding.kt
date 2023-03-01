package com.example.foodrecipesapp.bindingadapters
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.ui.fragments.recipes.RecipesFragmentDirections
import org.jsoup.Jsoup

class RecipesItemBinding {
    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout:ConstraintLayout,result:com.example.foodrecipesapp.data.dto.Result){
            recipeRowLayout.setOnClickListener {
                try {
                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                }catch (e:Exception){
                    Log.d("ClickListener",e.message.toString())
                }
            }
        }

        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView,imageUrl:String){
            imageView.load(
                imageUrl
            ){
                crossfade(600)
                error(R.drawable.ic_placeholder_image)

            }
        }


        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, numberOfLikes: Int) {
            textView.text = numberOfLikes.toString()
        }

        @BindingAdapter("setMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView,minutes:Int){
            textView.text = minutes.toString()
        }

        @BindingAdapter("setVeganColor")
        @JvmStatic
        fun setVeganColor(view:View,isVegan:Boolean){
            if(isVegan){
                when(view){
                    is TextView ->{
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView ->{
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }

        @BindingAdapter("parseHTML")
        @JvmStatic
        fun parseHtml(textView: TextView,description:String?){
            if(description!=null){
                val parsedDescription = Jsoup.parse(description).text()
                textView.text = parsedDescription
            }
        }

    }
}