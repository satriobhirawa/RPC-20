package com.example.rpc20

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PlayerVersusPlayer : AppCompatActivity(), Callback {

    private var playerName = ""
    private val btnRestart by lazy { findViewById<ImageView>(R.id.ivRefresh) }
    private val btnClose by lazy { findViewById<ImageView>(R.id.ivClose) }
    private var isFinished: Boolean = false
    private var comPick: String = ""
    private var playerPick: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_versus_player)

        val controller = GameLogicController(this)
        val playerOneChoiceList =
            mutableMapOf(
                R.id.ivPlayerOneBatu to "playerOneBatu",
                R.id.ivPlayerOneGunting to "playerOneGunting",
                R.id.ivPlayerOneKertas to "playerOneKertas"
            )
        val playerTwoChoiceList =
            mutableMapOf(
                R.id.ivPlayerTwoBatu to "comBatu",
                R.id.ivPlayerTwoGunting to "comGunting",
                R.id.ivPlayerTwoKertas to "comKertas"
            )
        playerName = intent.getStringExtra("Player")!!
        findViewById<TextView>(R.id.tvPlayerOne).text = playerName
        /*findViewById<TextView>(R.id.tvPlayerTwo).text = "Pemain 2"*/
        btnClose.setOnClickListener { finish() }
        btnRestart.setOnClickListener { restart() }

        playerOneChoiceList.forEach { (idx, value) ->
            run {
                findViewById<ImageView>(idx).setOnClickListener {
                    if (!isFinished) {
                        it.isClickable = false
                        playerPick = value
                        it.setBackgroundResource(R.color.choice_selected)
                        if (playerPick != "" && comPick != "") {
                            controller.gameCondition(playerPick, comPick)
                        }
                    }
                }
            }
        }

        playerTwoChoiceList.forEach { (idx, value) ->
            run {
                findViewById<ImageView>(idx).setOnClickListener {
                    if (!isFinished) {
                        it.isClickable = false
                        comPick = value
                        it.setBackgroundResource(R.color.choice_selected)


                        if (playerPick != "" && comPick != "") {
                            controller.gameCondition(playerPick, comPick)
                        }
                    }
                }
            }
        }

    }

    private fun restart() {

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
                "Player 2 MENANG"
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
        Log.d("COM", "Pemain 2 Memilih $comPick")
        Log.d("Result", "Result $result")
        Toast.makeText(this, "$playerName memilih $playerPick", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Player 2 memilih $comPick", Toast.LENGTH_SHORT).show()
    }
}