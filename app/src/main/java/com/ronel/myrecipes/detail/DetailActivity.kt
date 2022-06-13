package com.ronel.myrecipes.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ronel.myrecipes.*
import com.ronel.myrecipes.databinding.ActivityDetailBinding
import com.ronel.myrecipes.master.FoodClick
import com.ronel.myrecipes.objects.Food


class DetailActivity : AppCompatActivity() {


    private lateinit var viewModelAdapter: IngredientsAdapter
    private lateinit var binding: ActivityDetailBinding
    private lateinit var connectionLiveData: ConnectionLiveData

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private val viewModel: GenericViewModel by lazy {
        ViewModelProvider(this).get(GenericViewModel::class.java)
    }

    private var food : Food ? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //init view
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //init connnection validator
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this){
            viewModel.isNetworkAvailable.value = it
        }
        viewModel.isNetworkAvailable.value = applicationContext?.isConnected
        
        food =  Gson().fromJson(intent.getStringExtra("food") ,Food::class.java)
        Log.d("Details","FOOD : ${Gson().toJson(food)}")
        viewModel.selectFood(food)
        viewModel.selectIngredient(food!!.extendedIngredients)

        viewModelAdapter = IngredientsAdapter()

        binding.root.findViewById<RecyclerView>(R.id.ingredientsRV).apply{
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        viewModel.ingredientsList.observe(this@DetailActivity) { ingredient ->
            ingredient?.apply {
                viewModelAdapter.ingredients = ingredient
                viewModelAdapter.food = food
            }
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }

}