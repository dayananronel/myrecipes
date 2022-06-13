package com.ronel.myrecipes

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.ronel.myrecipes.objects.Food


@BindingAdapter("swipeRefreshData")
fun swipeRefreshData(swipeRefresh: SwipeRefreshLayout, status: ApiStatus?){
    when(status){
        ApiStatus.LOADING -> {
            swipeRefresh.isRefreshing = true
        }
        ApiStatus.ERROR -> {
            swipeRefresh.isRefreshing = false
        }
        ApiStatus.DONE -> {
            swipeRefresh.isRefreshing = false
        }
        ApiStatus.NO_INTERNET -> {
            swipeRefresh.isRefreshing = false
        }
    }
}

@BindingAdapter("emptyLayout")
fun emptyLayout(linearLayout: LinearLayout, foods : List<Food>){
    if(foods.isNotEmpty()){
        linearLayout.visibility = View.GONE
    }else{
        linearLayout.visibility = View.VISIBLE
    }
}

@BindingAdapter("displayLayout")
fun displayLayout(recyclerview: RecyclerView, routes : List<Food>){
    if(routes.isNotEmpty()){
        recyclerview.visibility = View.VISIBLE
    }else{
        recyclerview.visibility = View.GONE
    }
}

@BindingAdapter("loadImg")
fun loadImg(image: ImageView, url :String?){
    Glide.with(image.context)
        .load(url)
        .transform(CenterCrop(),RoundedCorners(24))
        .error(R.drawable.ic_recipes)
        .into(image)
}
@BindingAdapter("loadDishTypes")
fun loadDishTypes(textView : TextView, dishTypes : List<String>){
    if(dishTypes.isEmpty()){
       textView.visibility = View.GONE
    }else{
        textView.visibility = View.VISIBLE

        if(dishTypes.size == 1 ){
            textView.text = dishTypes.first()
        }else{
            var data = ""
            for (dishes in dishTypes){
                data = "$data  $dishes ."
            }
            textView.text = data
        }

    }
}

@BindingAdapter("formatHTMLTags")
fun formatHTMLTags(textView : TextView, summary : String){
     textView.text = fromHtml(summary)
}

private fun fromHtml(source:String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
    {
        Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
    }
    else
    {
        Html.fromHtml(source)
    }
}

@BindingAdapter("loadIngredientsImage")
fun loadIngredientsImage(image: ImageView, url :String?){

  val urlfinal = "https://spoonacular.com/cdn/ingredients_100x100/$url"
    Glide.with(image.context)
        .asBitmap()
        .load(urlfinal)
        .error(R.drawable.ic_recipes)
        .into(object : CustomTarget<Bitmap>(){

            override fun onLoadCleared(placeholder: Drawable?) {
                // this is called when imageView is cleared on lifecycle call or for
                // some other reason.
                // if you are referencing the bitmap somewhere else too other than this imageView
                // clear it here as you can no longer have the bitmap
            }

            override fun onResourceReady(
                resource: Bitmap,
                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
            ) {
                image.setImageBitmap(resource)

            }
        })
}