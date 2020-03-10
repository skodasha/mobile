package com.example.laba1

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_details.*


class detailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val filmName: String = intent.getStringExtra("film_name")
        val filmYear: Int = intent.getIntExtra("film_year", 0)
        val filmRatings: String = intent.getStringExtra("film_ratings")
        val filmDes: String = intent.getStringExtra("film_des")
        val filmImg: Int = intent.getIntExtra("film_image",0)
        val filmVideo: String = intent.getStringExtra("film_video")
        val textSize: Int = intent.getIntExtra("text",16)
        val font: Int = intent.getIntExtra("font",2131296257)

        val imageView: ImageView = findViewById(R.id.image)
        val titleTextView: TextView = findViewById(R.id.textView1)
        val yearTextView: TextView = findViewById(R.id.textView2)
        val ratingsTextView: TextView = findViewById(R.id.textView3)
        val descriptionTextView: TextView = findViewById(R.id.textView4)

        imageView.setImageResource(filmImg)
        titleTextView.text = filmName
        titleTextView.setTextSize(textSize.toFloat())
        titleTextView.typeface = ResourcesCompat.getFont(this, font)
        yearTextView.text = filmYear.toString()
        yearTextView.setTextSize(textSize.toFloat())
        yearTextView.typeface = ResourcesCompat.getFont(this,font)
        ratingsTextView.text = filmRatings
        ratingsTextView.setTextSize(textSize.toFloat())
        ratingsTextView.typeface = ResourcesCompat.getFont(this,font)
        descriptionTextView.text = filmDes
        descriptionTextView.setTextSize(textSize.toFloat())
        descriptionTextView.typeface = ResourcesCompat.getFont(this,font)

        if (!filmVideo.isEmpty()) {
            val videoView = YouTubePlayerView(this)
            videoView.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.33f
                ) )
            videoView.getPlayerUiController().showFullscreenButton(true)
            videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                    val videoId = filmVideo
                    youTubePlayer.cueVideo(filmVideo, 0f)
                }
            })

            videoView.getPlayerUiController().setFullScreenButtonClickListener(View.OnClickListener {
                if (videoView.isFullScreen()) {
                    videoView.exitFullScreen()
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    // Show ActionBar
                    if (supportActionBar != null) {
                        supportActionBar!!.show()
                    }
                } else {
                    videoView.enterFullScreen()
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                    // Hide ActionBar
                    if (supportActionBar != null) {
                        supportActionBar!!.hide()
                    }
                }
            })
            linLayout.addView(videoView)
        }
        else{
            val imageNone = ImageView(this)
            imageNone.setImageResource(R.drawable.novideo)
            linLayout.addView(imageNone)
        }


    }
}
