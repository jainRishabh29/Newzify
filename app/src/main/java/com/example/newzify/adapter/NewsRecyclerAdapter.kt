package com.example.newzify.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newzify.R
import com.example.newzify.dataClass.Article
import com.google.android.material.snackbar.Snackbar


class NewsRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    var arti_cles: ArrayList<Article> = ArrayList()


    //  , val sources : List<Source>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.newslist_item, parent, false)
        return NewsViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = arti_cles[position]
        //    val source = sources[position]
        if (!TextUtils.isEmpty(article.author)) {
            holder.authorText.text = article.author
        } else {
            holder.authorText.text = "Unknown Author"
        }

        if (!TextUtils.isEmpty(article.description)){
            holder.description.text = article.description
        }else{
            holder.description.text = "No Description.."
        }

        holder.clickableLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", article.url)
            it.findNavController().navigate(R.id.webViewFragment, bundle)
        }

        holder.sharePost.setOnClickListener{
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
              //  putExtra(Intent.EXTRA_STREAM , article.urlToImage)
                putExtra(Intent.EXTRA_TEXT, "${article.title} \n ")
                putExtra(Intent.EXTRA_TEXT, article.url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, null)
            context.startActivity(shareIntent)
        }

        holder.title.text = article.title
//        Glide.with(context).load(article.urlToImage)
//            .fallback(ColorDrawable(Color.rgb(250, 250, 250))).into(holder.newsImage)
        Glide.with(context).load(article.urlToImage)
            .fallback(R.drawable.unnamed).into(holder.newsImage)

        holder.itemView.setOnLongClickListener {
            val publishTime: String = article.publishedAt
            val date = publishTime.slice(0..9)
            val time = publishTime.slice(11..18)
            val snackbar: Snackbar =
                Snackbar.make(it, "Published on $date at $time", Snackbar.LENGTH_SHORT)
            snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            snackbar.show()
//          Log.d("batao",date.toString())
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return arti_cles.size
    }


    inner class NewsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage : ImageView = itemView.findViewById(R.id.newsImage)
        val sharePost : ImageView = itemView.findViewById(R.id.sharePost)
        val savePost : ImageView = itemView.findViewById(R.id.savePost)
        val authorText : TextView= itemView.findViewById(R.id.authorText)
        val description :TextView = itemView.findViewById(R.id.description)
        val title : TextView = itemView.findViewById(R.id.newsTitle)
        val clickableLayout : LinearLayout = itemView.findViewById(R.id.clickableLayout)

    }

    fun setNews(newss: ArrayList<Article>) {
        this.arti_cles = newss
        Log.d("progress","NRA")
        notifyDataSetChanged()
    }


    fun <T> merge(first: List<T>, second: List<T>): List<T> {
        return first + second
    }

}