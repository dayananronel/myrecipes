package com.ronel.myrecipes.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ronel.myrecipes.*
import com.ronel.myrecipes.databinding.FragmentDetailBinding
import com.ronel.myrecipes.databinding.FragmentMenuBinding
import com.ronel.myrecipes.objects.Food


class DetailFragment : Fragment() {


    private lateinit var viewModelAdapter: IngredientsAdapter
    private lateinit var connectionLiveData: ConnectionLiveData

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private val viewModel : GenericViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewmodel after onActivityCreated()"
        }
        ViewModelProvider(this,GenericViewModel.Factory(activity.application))
            .get(GenericViewModel::class.java)
    }

    private var food : Food ? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel



        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //init connnection validator
        connectionLiveData = ConnectionLiveData(requireContext())
        connectionLiveData.observe(viewLifecycleOwner){
            viewModel.isNetworkAvailable.value = it
        }
        viewModel.isNetworkAvailable.value = context?.isConnected


        food =  Gson().fromJson(requireArguments().getString("food").toString() ,Food::class.java)
        Log.d("Details","FOOD : ${Gson().toJson(food)}")
        viewModel.selectFood(food)
        viewModel.selectIngredient(food!!.extendedIngredients)

        viewModelAdapter = IngredientsAdapter()

        binding.root.findViewById<RecyclerView>(R.id.ingredientsRV).apply{
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

      return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.ingredientsList.observe(viewLifecycleOwner) { ingredient ->
            ingredient?.apply {
                viewModelAdapter.ingredients = ingredient
                viewModelAdapter.food = food
            }
        }



    }


}