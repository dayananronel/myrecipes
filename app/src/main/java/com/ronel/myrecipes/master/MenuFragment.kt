package com.ronel.myrecipes.master

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ronel.myrecipes.*
import com.ronel.myrecipes.databinding.FragmentMenuBinding
import com.ronel.myrecipes.detail.DetailFragment
import com.ronel.myrecipes.objects.Food

class MenuFragment : Fragment() {

    private lateinit var viewModelAdapter: FoodAdapter
    private lateinit var connectionLiveData: ConnectionLiveData

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private val viewModel : GenericViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewmodel after onActivityCreated()"
        }
        ViewModelProvider(this,GenericViewModel.Factory(activity.application))
            .get(GenericViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentMenuBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_menu,
            container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModelAdapter = FoodAdapter(FoodClick {
            val bundle = bundleOf("food" to Gson().toJson(it))
            Navigation.findNavController(binding.root).navigate(R.id.action_action_menu_to_action_detail, bundle)
        })

        binding.swipeRefreshDeliveries.setOnRefreshListener{
            viewModel.refreshFoodsFromNetwork().apply {
                viewModel.foods.observe(viewLifecycleOwner) { foods ->
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
        connectionLiveData = ConnectionLiveData(requireContext())
        connectionLiveData.observe(viewLifecycleOwner){
            viewModel.isNetworkAvailable.value = it
        }
        viewModel.isNetworkAvailable.value = context?.isConnected


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshFoodsFromNetwork().apply {
            viewModel.foods.observe(viewLifecycleOwner) { foods ->
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