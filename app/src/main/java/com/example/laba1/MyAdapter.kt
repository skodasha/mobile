package com.example.laba1

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    val fontId: Int,
    val textSize: Float,
    val context: Context,
    var items: MutableList<Film>,
    val callback: Callback
) : RecyclerView.Adapter<MyAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(data: MutableList<Film>){
        items = data
        notifyDataSetChanged()
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.image)
        private val titleTextView: TextView = itemView.findViewById(R.id.textView1)
        private val yearTextView: TextView = itemView.findViewById(R.id.textView2)
        private val ratingsTextView: TextView = itemView.findViewById(R.id.textView3)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textView4)

        fun bind(film: Film) {
            imageView.setImageResource(
                context.resources.getIdentifier(
                    film.image,
                    "drawable",
                    "com.example.laba1"
                )
            )
            titleTextView.text = film.name
            titleTextView.setTextSize(textSize)
            titleTextView.typeface = ResourcesCompat.getFont(context, fontId)
            yearTextView.text = film.year.toString()
            yearTextView.setTextSize(textSize - 4.0f)
            yearTextView.typeface = ResourcesCompat.getFont(context, fontId)
            ratingsTextView.text = film.maturityRatings
            ratingsTextView.setTextSize(textSize - 4.0f)
            ratingsTextView.typeface = ResourcesCompat.getFont(context, fontId)
            descriptionTextView.text = film.description
            descriptionTextView.setTextSize(textSize - 2.0f)
            descriptionTextView.typeface = ResourcesCompat.getFont(context, fontId)

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: Film)
    }
}