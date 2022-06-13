package com.ronel.myrecipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ronel.myrecipes.databinding.FoodsListItemBinding
import com.ronel.myrecipes.master.FoodClick
import com.ronel.myrecipes.objects.Food

class FoodAdapter(val callback: FoodClick) : RecyclerView.Adapter<FoodsViewHolder>() {

    var foods : List<Food> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val withDataBinding: FoodsListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            FoodsViewHolder.LAYOUT,
            parent,
            false)
        return FoodsViewHolder(withDataBinding)
    }

    override fun getItemCount() = foods.size

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.food = foods[position]
            it.foodClickCallback = callback
        }
    }


}
class FoodsViewHolder(val viewDataBinding: FoodsListItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.foods_list_item
    }
}