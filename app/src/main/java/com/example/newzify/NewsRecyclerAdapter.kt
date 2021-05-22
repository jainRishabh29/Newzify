package com.example.newzify

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newzify.dataClass.Article
import com.example.newzify.fragments.WebViewFragment
import com.google.android.material.snackbar.Snackbar


class NewsRecyclerAdapter(private val context: Context, private val articles: List<Article>) :
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
        //     holder.autherText.text = source.name
        holder.description.text = article.description
        holder.title.text = article.title
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)

        holder.itemView.setOnClickListener {
//            val ft = (context as AppCompatActivity).supportFragmentManager
//            val a: FragmentTransaction = ft.beginTransaction()

//           val a = supportFragmentManager.beginTransaction()
//            supportFragmentManager.popBackStack()

//            val frag = WebViewFragment()
//            frag.arguments = Bundle().putString("url",article.url)
//            a.replace(R.id.containerView, frag).addToBackStack("backStack")
//            a.commit()

//            val bundle = bundleOf("url" to article.url)
//            Navigation.findNavController(it).navigate(R.id.webViewFragment,bundle)

            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(article.url))
        }

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


    class NewsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage = itemView.findViewById<ImageView>(R.id.newsImage)
        val autherText = itemView.findViewById<TextView>(R.id.authorText)
        val description = itemView.findViewById<TextView>(R.id.description)
        val title = itemView.findViewById<TextView>(R.id.newsTitle)
    }
}