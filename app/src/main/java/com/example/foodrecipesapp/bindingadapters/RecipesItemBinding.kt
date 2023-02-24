package com.example.foodrecipesapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foodrecipesapp.R

class RecipesItemBinding {
    companion object {

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

    }
}