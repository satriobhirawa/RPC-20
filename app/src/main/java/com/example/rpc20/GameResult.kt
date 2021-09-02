package com.example.rpc20

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment


class GameResult : DialogFragment() {

    private var playerName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_result, container, false)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tvWinner).text = playerName
        view.findViewById<TextView>(R.id.btnMenu).setOnClickListener { activity?.finish() }
        view.findViewById<TextView>(R.id.btnRestart).setOnClickListener { dialog?.dismiss() }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            GameResult().apply {
                playerName = param1
            }
    }
}
