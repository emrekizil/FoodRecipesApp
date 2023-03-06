package com.example.foodrecipesapp.ui.adapters

import android.content.Context
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipesapp.R
import com.example.foodrecipesapp.data.database.entities.FavoritesEntity
import com.example.foodrecipesapp.databinding.ItemFavoriteRecipesBinding
import com.example.foodrecipesapp.ui.MainViewModel
import com.example.foodrecipesapp.ui.fragments.FavoriteRecipesFragmentDirections
import com.example.foodrecipesapp.util.RecipesDiffUtil
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class FavoriteRecipesAdapter(
   private val requireActivity:FragmentActivity,
   private val viewModel:MainViewModel
):RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteRecipesViewHolder>() , ActionMode.Callback{

    private var multiSelection = false

    private lateinit var globalActionMode:ActionMode
    private lateinit var rootView:View

    private var selectedRecipes = arrayListOf<FavoritesEntity>()
    private var myViewHolders = arrayListOf<FavoriteRecipesViewHolder>()
    private var favoriteRecipes = emptyList<FavoritesEntity>()
    class FavoriteRecipesViewHolder(private val binding:ItemFavoriteRecipesBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(favoritesEntity: FavoritesEntity){
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):FavoriteRecipesViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoriteRecipesBinding.inflate(layoutInflater,parent,false)
                return FavoriteRecipesViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipesViewHolder {
        return FavoriteRecipesViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    override fun onBindViewHolder(holder: FavoriteRecipesViewHolder, position: Int) {
        val item = favoriteRecipes[position]
        holder.bind(item)
        rootView = holder.itemView.rootView

        myViewHolders.add(holder)

        holder.itemView.findViewById<ConstraintLayout>(R.id.favorite_recipes_layout).setOnClickListener {
            if (multiSelection){
                applySelection(holder,item)
            }else{
                val action = FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(item.result)
                holder.itemView.findNavController().navigate(action)
            }
        }
        holder.itemView.findViewById<ConstraintLayout>(R.id.favorite_recipes_layout).setOnLongClickListener {
            if(!multiSelection){
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder,item)
                true
            }else{
                multiSelection=false
                false
            }


        }
    }

    fun setData(newData:List<FavoritesEntity>){
        val diffUtil = RecipesDiffUtil(favoriteRecipes,newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        favoriteRecipes = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
    private fun applySelection(holder:FavoriteRecipesViewHolder,currentRecipe:FavoritesEntity){
        if(selectedRecipes.contains(currentRecipe)){
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)
            applyActionModeTitle()
        }else{
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundLightColor,R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: FavoriteRecipesViewHolder,backgroundColor:Int,strokeColor:Int){
        holder.itemView.findViewById<ConstraintLayout>(R.id.favorite_recipes_layout).setBackgroundColor(
            ContextCompat.getColor(requireActivity,backgroundColor)
        )
        holder.itemView.findViewById<MaterialCardView>(R.id.favoritesCardView).strokeColor =
            ContextCompat.getColor(requireActivity,strokeColor)
    }

    private fun applyActionModeTitle(){
        when(selectedRecipes.size){
            0->{
                globalActionMode.finish()
            }
            1->{
                globalActionMode.title="${selectedRecipes.size} item selected"
            }
            else->{
                globalActionMode.title="${selectedRecipes.size} items selected"
            }
        }
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu,menu)
        globalActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return  true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
       return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {

        if(menu?.itemId==R.id.deleteFavoriteRecipesMenu){
            selectedRecipes.forEach {
                viewModel.deleteFavoriteRecipe(it)
            }
            showSnackbar("${selectedRecipes.size} Recipe/s removed.")
            multiSelection=false
            selectedRecipes.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        multiSelection  = false
        myViewHolders.forEach {
            changeRecipeStyle(it,R.color.cardBackgroundColor,R.color.strokeColor)
        }
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }
    private fun showSnackbar(message:String){
        Snackbar.make(rootView,message,Snackbar.LENGTH_SHORT).setAction("OKAY"){}.show()
    }

    private fun applyStatusBarColor(color:Int){
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity,color)

    }
    fun clearContextualActionMode(){
        if(this::globalActionMode.isInitialized){
            globalActionMode.finish()
        }
    }
}