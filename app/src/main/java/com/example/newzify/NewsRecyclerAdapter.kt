package com.example.newzify

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newzify.dataClass.Article

import com.google.android.material.snackbar.Snackbar



class NewsRecyclerAdapter(private val context: Context, private val articles: List<Article>, private val listener: OnNewsClick) :
    RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    //  , val sources : List<Source>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.newslist_item, parent, false)
        return NewsViewHolder(view)

    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        //    val source = sources[position]
        holder.autherText.text = article.author
        holder.description.text = article.description
        holder.title.text = article.title
        Glide.with(context).load(article.urlToImage).fallback(ColorDrawable(Color.rgb(250,250,250))).into(holder.newsImage)

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
        return articles.size
    }


   inner class NewsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val newsImage = itemView.findViewById<ImageView>(R.id.newsImage)
        val autherText = itemView.findViewById<TextView>(R.id.authorText)
        val description = itemView.findViewById<TextView>(R.id.description)
        val title = itemView.findViewById<TextView>(R.id.newsTitle)

        init {
            itemView.setOnClickListener(this)
          //  Log.d("batao","hii3")
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            val article_inst = articles[position]
         //   Log.d("batao","hii1")
            listener.onItemClick(article_inst, position)
        }
    }

    interface OnNewsClick{

        fun onItemClick(article:Article , position: Int)

    }

}