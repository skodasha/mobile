package com.example.laba1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseError
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Array

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    var list: ArrayList<Film> = ArrayList()

    var sort: Boolean = false
    var filter: Boolean = false
    var textSize: Int = 16
    var fontId: Int = 2131296257
    var id: Int = 0
    var language: Int = 0
    lateinit var myAdapter: MyAdapter
    var listFilms = list.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun loadFilms() {
        list.clear()
        database = FirebaseDatabase.getInstance().reference
        database.child(language.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val post = snapshot.getValue(Film::class.java)
                        list.add(post!!)
                    }
                    listFilms = list.toMutableList()
                    if (filter && sort) {
                        listFilms = listFilms.filter { !it.video.isEmpty() }.toMutableList()
                        listFilms.sortBy { it.year }
                    } else if (filter) {
                        listFilms = listFilms.filter { !it.video.isEmpty() }.toMutableList()
                    } else if (sort) {
                        listFilms.sortBy { it.year }
                    } else {
                        listFilms = list
                    }
                    myAdapter.setData(listFilms)
                }

                override fun onCancelled(error: DatabaseError) {
                    //print error.message
                }
            })
    }

    override fun onStart() {
        super.onStart()
        loadFilms()
        myAdapter = MyAdapter(fontId, textSize.toFloat(), this, listFilms, object : MyAdapter.Callback {
            override fun onItemClicked(item: Film) {
                val intent = Intent(this@MainActivity, detailsActivity::class.java)
                val id: Int = this@MainActivity.resources.getIdentifier(
                    item.image,
                    "drawable",
                    "com.example.laba1"
                )
                intent.putExtra("film_name", item.name)
                intent.putExtra("film_year", item.year)
                intent.putExtra("film_ratings", item.maturityRatings)
                intent.putExtra("film_des", item.description)
                intent.putExtra("film_video", item.video)
                intent.putExtra("film_image", id)
                intent.putExtra("font", fontId)
                intent.putExtra("text", textSize)
                startActivity(intent)
            }
        })
        rv_films.adapter = myAdapter

        if(language == 0){
            button1.text = "Settings"
            button2.text = "Search"
        }
        else{
            button1.text = "Настройки"
            button2.text = "Фильтр"
        }
        button1.setTextSize(textSize.toFloat())
        button1.typeface = ResourcesCompat.getFont(this, fontId)
        button2.setTextSize(textSize.toFloat())
        button2.typeface = ResourcesCompat.getFont(this, fontId)

    }

    fun onClickSearch(view: View) {
        val intent = Intent(this@MainActivity, searchActivity::class.java)
        intent.putExtra("sort", sort)
        intent.putExtra("filter", filter)
        intent.putExtra("text", textSize)
        intent.putExtra("font", fontId)
        intent.putExtra("lan", language)
        intent.putExtra("id", id)
        startActivityForResult(intent, 1)
    }

    fun onClickSettings(view: View) {
        val intent = Intent(this@MainActivity, settingsActivity::class.java)
        intent.putExtra("sort", sort)
        intent.putExtra("filter", filter)
        intent.putExtra("text", textSize)
        intent.putExtra("font", fontId)
        intent.putExtra("id", id)
        intent.putExtra("lan", language)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            sort = data.getBooleanExtra("sort", false)
            filter = data.getBooleanExtra("filter", false)
            textSize = data.getIntExtra("text", 16)
            fontId = data.getIntExtra("font", 2131296257)
            id = data.getIntExtra("id", 0)
            language = data.getIntExtra("lan", 0)
        }
    }

}
