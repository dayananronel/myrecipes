package com.ronel.myrecipes

import android.app.Application
import androidx.lifecycle.*
import com.ronel.myrecipes.objects.Food
import com.ronel.myrecipes.objects.GenericResponse
import com.ronel.myrecipes.objects.Ingredients
import kotlinx.coroutines.launch

class GenericViewModel (application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

    private val _recipesResponse = MutableLiveData<GenericResponse>()
    val recipesResponse : MutableLiveData<GenericResponse> get() = _recipesResponse

    private val _foods = MutableLiveData<List<Food>>(emptyList())
    val foods : LiveData<List<Food>> get() = _foods

    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    val isNetworkAvailable : MutableLiveData<Boolean> get() = _isNetworkAvailable

    private val _response = MutableLiveData<String>("")
    val response : LiveData<String> get() = _response


    private val _selectedFood = MutableLiveData<Food>()
    val selectedFood : LiveData<Food> get() = _selectedFood

    private val _ingredientsList = MutableLiveData<List<Ingredients>>(emptyList())
    val ingredientsList : LiveData<List<Ingredients>> get() = _ingredientsList

    fun refreshFoodsFromNetwork(){
        if(isNetworkAvailable.value == true){
            _status.value = ApiStatus.LOADING
            viewModelScope.launch {
                try {
                    _recipesResponse.value = ApiService.retrofitService.getFoods()

                    if(_recipesResponse.value!!.recipes.isNotEmpty()){
                        _status.value = ApiStatus.DONE

                        _foods.value = _recipesResponse.value?.recipes

                    }else{
                        _status.value = ApiStatus.ERROR
                        _response.value ="App has encountered an error. Pull down to refresh data."
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    _status.value = ApiStatus.ERROR
                    _response.value = "Something went wrong. Swipe down to try again."
                }
            }
        }else{
            _status.value = ApiStatus.NO_INTERNET
            _response.value = "Please check your internet connection and try again."
        }
    }

    fun selectFood(food : Food?){
       if (food == null){
           _status.value = ApiStatus.ERROR
           _response.value = "Information can't be found."
       }else{
           _status.value = ApiStatus.DONE
           _response.value = ""
           _selectedFood.value = food
       }
    }

    fun selectIngredient(ingredients : List<Ingredients>){
        if (ingredients.isEmpty()){
            _status.value = ApiStatus.ERROR
            _response.value = "Information can't be found."
        }else{
            _status.value = ApiStatus.DONE
            _response.value = ""
            _ingredientsList.value = ingredients
        }
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GenericViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GenericViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}