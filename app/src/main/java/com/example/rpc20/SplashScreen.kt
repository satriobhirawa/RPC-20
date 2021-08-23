package com.example.rpc20

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splashscreen)

        val gameTitle = findViewById<ImageView>(R.id.ivTitle)
        val gameIcon = findViewById<ImageView>(R.id.ivGameIcon)

        gameTitle.alpha = 0f
        gameIcon.alpha = 0f

        gameTitle.animate().setDuration(1500).alpha(1f)
        gameIcon.animate().setDuration(1500).alpha(1f)

        val explicitIntent = Intent(this, OnBoardPagesActivity::class.java)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            this.finish()
            startActivity(explicitIntent)
        },3000)

    }
}