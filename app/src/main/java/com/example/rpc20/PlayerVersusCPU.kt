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
    private var playerName = ""
    private val controller = GameLogicController(this)
    private val comChoiceKertas by lazy {findViewById<ImageView>(R.id.ivPlayerTwoKertas)}
    private val comChoiceGunting by lazy {findViewById<ImageView>(R.id.ivPlayerTwoGunting)}
    private val comChoiceBatu by lazy {findViewById<ImageView>(R.id.ivPlayerTwoBatu)}
    private val btnRestart by lazy { findViewById<ImageView>(R.id.ivRefresh) }
    private val btnClose by lazy { findViewById<ImageView>(R.id.ivClose) }
    private var isFinished: Boolean = false

    private var comPick: String = ""
    private var playerPick: String = ""

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_versus_cpu)


        val playerChoiceList =
            mutableMapOf(R.id.ivPlayerOneBatu to "playerOneBatu",R.id.ivPlayerOneGunting to "playerOneGunting",
                R.id.ivPlayerOneKertas to "playerOneKertas")
        playerName = intent.getStringExtra("Player")!!
        findViewById<TextView>(R.id.tvPlayerOne) .text = playerName


        btnClose.setOnClickListener { finish() }
        btnRestart.setOnClickListener { restart() }

        //TODO isClickable
        playerChoiceList.forEach { (idx,value) ->
            run {
                findViewById<ImageView>(idx).setOnClickListener {
                    if (!isFinished) {
                        /*idx.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.choice_selected)*/

                        it.setBackgroundResource(R.color.choice_selected)
                        it.isClickable = false
                        playerPick = value
                        comPick = randomPick()
                        controller.gameCondition(playerPick, comPick)
                        isFinished = true
                    }
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun randomPick(): String {
        val choices = arrayOf("comKertas", "comGunting", "comBatu")
        val randChoiceCom = choices.random()
        when (randChoiceCom) {
            "comBatu" -> comChoiceBatu.setBackgroundResource(R.color.choice_selected)
            "comGunting" -> comChoiceGunting.setBackgroundResource(R.color.choice_selected)
            "comKertas" -> comChoiceKertas.setBackgroundResource(R.color.choice_selected)
        }
        return randChoiceCom
    }

    private fun restart() {
        /*var arrConcatenate = comChoiceList.plus(playerChoiceList)
        arrConcatenate.forEach { idx ->
            idx.backgroundTintList = getColorStateList(R.color.white)
            idx.isClickable = true

        }*/

        val tempArr = arrayOf(
            R.id.ivPlayerOneBatu,
            R.id.ivPlayerOneKertas,
            R.id.ivPlayerOneGunting,
            R.id.ivPlayerTwoGunting,
            R.id.ivPlayerTwoKertas,
            R.id.ivPlayerTwoBatu
        )
        tempArr.forEach { idx ->
            findViewById<ImageView>(idx).setBackgroundResource(R.color.white)
            findViewById<ImageView>(idx).isClickable = true
        }
        isFinished = false
        playerPick = ""
        comPick = ""
    }

    override fun showResult(result: String) {

        when (result) {
            "PlayerOneWin" -> GameResult.newInstance(
                "$playerName MENANG"
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
        Toast.makeText(this, "$playerName memilih $playerPick", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "CPU memilih $comPick", Toast.LENGTH_SHORT).show()
    }
}