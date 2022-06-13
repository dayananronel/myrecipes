package com.ronel.myrecipes.master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ronel.myrecipes.*
import com.ronel.myrecipes.databinding.ActivityMainBinding
import com.ronel.myrecipes.detail.DetailActivity
import com.ronel.myrecipes.objects.Food

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelAdapter: FoodAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var connectionLiveData: ConnectionLiveData

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private val viewModel: GenericViewModel by lazy {
        ViewModelProvider(this).get(GenericViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

       //init view
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        toolbar = binding.toolbar

        viewModelAdapter = FoodAdapter(FoodClick {
            val intent = Intent(applicationContext,DetailActivity::class.java)
            intent.putExtra("food",Gson().toJson(it))
            startActivity(intent)
        })

        binding.swipeRefreshDeliveries.setOnRefreshListener{
            viewModel.refreshFoodsFromNetwork().apply {
                viewModel.foods.observe(this@MainActivity) { foods ->
                    foods?.apply {
                        viewModelAdapter.foods = foods
                    }
                }
            }
        }

        binding.root.findViewById<RecyclerView>(R.id.rv_food_list).apply{
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }


        //init connnection validator
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this){
            viewModel.isNetworkAvailable.value = it
        }
        viewModel.isNetworkAvailable.value = applicationContext?.isConnected

        viewModel.refreshFoodsFromNetwork().apply {
            viewModel.foods.observe(this@MainActivity) { foods ->
                foods?.apply {
                    viewModelAdapter.foods = foods
                }
            }
        }

    }


}



class FoodClick(val block: (Food) -> Unit) {
    fun onClick(food: Food) = block(food)
}