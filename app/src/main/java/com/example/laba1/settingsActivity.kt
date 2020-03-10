package com.example.laba1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_settings.*

class settingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var textSize: Int = intent.getIntExtra("text", 16)
        val fonts = resources.getStringArray(R.array.fonts)
        val spinner : Spinner = findViewById(R.id.spinner)
        var selection: Int = intent.getIntExtra("id", 0)
        var language = intent.getIntExtra("lan",0)
        var fontId: Int = 2131296257
        var sortMode: Boolean = intent.getBooleanExtra("sort",false)
        var filterMode: Boolean = intent.getBooleanExtra("filter", false)


        if (spinner != null){
            val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, fonts)
            spinner.adapter = adapter

            spinner.setSelection(selection)
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    fontId = this@settingsActivity.resources.getIdentifier(spinner.selectedItem.toString(),"font", "com.example.laba1")
                    textView.typeface = ResourcesCompat.getFont(this@settingsActivity, fontId)
                    val intent = Intent(this@settingsActivity, MainActivity::class.java)
                    intent.putExtra("font",fontId)
                    selection = position
                    intent.putExtra("id", position)
                    intent.putExtra("text",textSize)
                    intent.putExtra("lan", language)
                    intent.putExtra("sort", sortMode)
                    intent.putExtra("filter", filterMode)
                    setResult(Activity.RESULT_OK, intent)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }

        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    val intent = Intent(this@settingsActivity, MainActivity::class.java)
                    textSize = 16 +  i
                    intent.putExtra("text",textSize)
                    intent.putExtra("font",fontId)
                    intent.putExtra("id", selection)
                    intent.putExtra("lan", language)
                    intent.putExtra("sort", sortMode)
                    intent.putExtra("filter", filterMode)
                    setResult(Activity.RESULT_OK, intent)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat() )
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
                }
            }
        )
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
        seekBar.setProgress(textSize - 16)

        toggleButton.isChecked = language != 1

        toggleButton.setOnCheckedChangeListener{buttonView, isChecked ->
            if(isChecked){
                language = 0
                textView.text = "Text size:"
            }
            else{
                language = 1
                textView.text = "Размер шрифта:"
            }
            val intent = Intent(this@settingsActivity, MainActivity::class.java)
            intent.putExtra("text",textSize)
            intent.putExtra("font",fontId)
            intent.putExtra("id", selection)
            intent.putExtra("lan", language)
            intent.putExtra("sort", sortMode)
            intent.putExtra("filter", filterMode)
            setResult(Activity.RESULT_OK, intent)
        }
    }

    override fun onStart() {
        super.onStart()
    }
}


class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        val fontId = this.resources.getIdentifier(spinner.selectedItem.toString(),"font", "com.example.laba1")
        textView.typeface = ResourcesCompat.getFont(this, fontId)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}
