package com.example.rpc20

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class Menu : AppCompatActivity() {

    private lateinit var player: Player

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        player = intent.getParcelableExtra<Player>("Player") as Player
        val textOne = findViewById<TextView>(R.id.tvVersusPlayer)
        val textTwo = findViewById<TextView>(R.id.tvVersusCPU)
        val textVersusPlayer = "${player.name} VS Pemain"
        val textVersusCPU = "${player.name} VS CPU"
        textOne.text = textVersusPlayer
        textTwo.text = textVersusCPU

        val snackbar = Snackbar.make(
            findViewById(R.id.clMenu), "Selamat datang ${player.name}",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setTextColor(Color.WHITE)
        snackbar.setActionTextColor(Color.parseColor(resources.getString(R.color.orange)))
        snackbar.setAction("Tutup") { snackbar.dismiss() }


        val tvSnackbar = snackbar.view.findViewById<TextView>(R.id.snackbar_text)
        tvSnackbar.setTypeface(resources.getFont(R.font.qdbettercomicsans_jeeeg), Typeface.BOLD)
        tvSnackbar.textSize = 20f
        snackbar.show()

        val playerVersusPlayer = findViewById<ImageView>(R.id.ivVersusPlayer)
        val playerVersusCPU = findViewById<ImageView>(R.id.ivVersusCPU)

        playerVersusPlayer.setOnClickListener {
            val intent = Intent(this, PlayerVersusPlayer::class.java)
            intent.putExtra("Player", player.name)
            startActivity( Intent(this, PlayerVersusPlayer::class.java))
        }

        playerVersusCPU.setOnClickListener {
            val intent = Intent(this, PlayerVersusCPU::class.java)
            intent.putExtra("Player", player.name)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        finishAffinity()
        exitProcess(0)
    }
}

