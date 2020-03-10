package com.example.laba1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_search.*

class searchActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var sortMode: Boolean = intent.getBooleanExtra("sort",false)
        var filterMode: Boolean = intent.getBooleanExtra("filter", false)
        var textSize: Int = intent.getIntExtra("text", 16)
        var font: Int = intent.getIntExtra("font",2131296257)
        var id = intent.getIntExtra("id", 0)
        var language = intent.getIntExtra("lan", 0)

        if(language == 0){
            switchFilter.text = "Show only with video"
            switchSort.text = "Sort by year"
        }
        else{
            switchFilter.text = "Показать только с видео"
            switchSort.text = "Сортировать по году"
        }
        switchFilter.setTextSize(textSize.toFloat())
        switchFilter.typeface = ResourcesCompat.getFont(this, font)
        switchSort.setTextSize(textSize.toFloat())
        switchSort.typeface = ResourcesCompat.getFont(this, font)

        if (sortMode){
            switchSort.isChecked = true
        }

        if (filterMode){
            switchFilter.isChecked = true
        }

        switchSort.setOnCheckedChangeListener { buttonView, isChecked ->
            val intent = Intent(this@searchActivity,MainActivity::class.java)
            sortMode = isChecked
            intent.putExtra("sort", sortMode)
            intent.putExtra("filter", filterMode)
            intent.putExtra("font", font)
            intent.putExtra("text", textSize)
            intent.putExtra("id", id)
            intent.putExtra("lan", language)
            setResult(Activity.RESULT_OK,intent)
        }

        switchFilter.setOnCheckedChangeListener { buttonView, isChecked ->
            val intent = Intent(this@searchActivity, MainActivity::class.java)
            filterMode = isChecked
            intent.putExtra("sort", sortMode)
            intent.putExtra("filter", filterMode)
            intent.putExtra("font", font)
            intent.putExtra("text", textSize)
            intent.putExtra("id", id)
            intent.putExtra("lan", language)
            setResult(Activity.RESULT_OK, intent)
        }

    }

}
