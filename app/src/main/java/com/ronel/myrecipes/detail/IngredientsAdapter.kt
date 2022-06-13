package com.ronel.myrecipes.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ronel.myrecipes.R
import com.ronel.myrecipes.databinding.FoodsListItemBinding
import com.ronel.myrecipes.databinding.IngredientsListItemBinding
import com.ronel.myrecipes.objects.Food
import com.ronel.myrecipes.objects.Ingredients

class IngredientsAdapter() : RecyclerView.Adapter<IngredientsViewHolder>() {

    var ingredients : List<Ingredients> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var food : Food? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val withDataBinding: IngredientsListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            IngredientsViewHolder.LAYOUT,
            parent,
            false)
        return IngredientsViewHolder(withDataBinding)
    }

    override fun getItemCount() = ingredients.size

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.ingredients = ingredients[position]
            it.food = food
        }
    }


}
class IngredientsViewHolder(val viewDataBinding: IngredientsListItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.ingredients_list_item
    }
}