package com.example.rpc20

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PlayerVersusCPU : AppCompatActivity(), Callback {
    private var playerName: String? = null
    private val controller = GameLogicController(this)
    private val playerNameInGame by lazy { findViewById<TextView>(R.id.tvPlayerOne) }
    private val comChoiceKertas by lazy { findViewById<ImageView>(R.id.ivPlayerTwoKertas) }
    private val comChoiceGunting by lazy { findViewById<ImageView>(R.id.ivPlayerTwoGunting) }
    private val comChoiceBatu by lazy { findViewById<ImageView>(R.id.ivPlayerTwoBatu) }
    private val playerChoiceKertas by lazy { findViewById<ImageView>(R.id.ivPlayerOneKertas) }
    private val playerChoiceGunting by lazy { findViewById<ImageView>(R.id.ivPlayerOneGunting) }
    private val playerChoiceBatu by lazy { findViewById<ImageView>(R.id.ivPlayerOneBatu) }
    private val btnRestart by lazy { findViewById<ImageView>(R.id.ivRefresh) }
    private val btnClose by lazy { findViewById<ImageView>(R.id.ivClose) }
    private var isFinished: Boolean = false
    private val comChoiceList =
        arrayListOf<ImageView>(comChoiceBatu, comChoiceGunting, comChoiceKertas)
    private val playerChoiceList =
        arrayListOf<ImageView>(playerChoiceBatu, playerChoiceGunting, playerChoiceKertas)
    private var comPick: String? = null
    private var playerPick: String? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_versus_cpu)

        playerName = intent.getStringExtra("Player")!!
        playerNameInGame.text = playerName
        //TODO isClickable
        playerChoiceList.forEach { idx ->
            run {
                idx.setOnClickListener {
                    if (!isFinished) {
                        idx.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.choice_selected)
                        idx.isClickable = false
                        playerPick = idx.toString()
                        comPick = randomPick(comChoiceList)
                        controller.gameCondition(playerPick!!, comPick!!)
                        isFinished = true
                    }
                }
            }
        }

        btnClose.setOnClickListener { finish() }
        btnRestart.setOnClickListener { restart() }


    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun randomPick(arr: ArrayList<ImageView>): String {
        val randChoice = arr.random()
        when (randChoice) {
            comChoiceBatu -> comChoiceBatu.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.choice_selected)
            comChoiceGunting -> comChoiceGunting.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.choice_selected)
            comChoiceKertas -> comChoiceKertas.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.choice_selected)
        }
        return randChoice.toString()
    }

    private fun restart() {
        var arrConcatenate = comChoiceList.plus(playerChoiceList)
        arrConcatenate.forEach { idx ->
            idx.backgroundTintList = getColorStateList(R.color.white)
            idx.isClickable = true

        }
        isFinished = false
        playerPick = null
        comPick = null
    }

    override fun showResult(result: String) {

        when (result) {
            "PlayerOneWin" -> GameResult.newInstance(
                "$playerNameInGame MENANG"
            ).show(
                supportFragmentManager,
                null
            )
            "ComWin" -> GameResult.newInstance(
                "CPU MENANG"
            ).show(
                supportFragmentManager,
                null
            )
            "Draw" -> GameResult.newInstance(
                "DRAW"
            ).show(
                supportFragmentManager,
                null
            )
        }

        Log.d("Player1", "Pemain Memilih $playerPick")
        Log.d("COM", "Computer Memilih $comPick")
        Log.d("Result", "Result $result")
        Toast.makeText(this, "$playerNameInGame memilih $playerPick", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "CPU memilih $comPick", Toast.LENGTH_SHORT).show()
    }
}