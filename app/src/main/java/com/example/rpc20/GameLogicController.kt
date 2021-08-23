package com.example.rpc20

class GameLogicController(private val callback: Callback) {
    fun gameCondition(playerInput: String, comInput: String){

        when {
            ((playerInput == "playerOneGunting" && comInput == "comKertas") ||
                    (playerInput == "playerOneBatu" && comInput == "comGunting") ||
                    (playerInput == "playerOneKertas" && comInput == "comBatu")
                    ) -> {
                callback.showResult("PlayerOneWin")
            }

            ((comInput == "comGunting" && playerInput == "playerOneKertas") ||
                    (comInput == "comBatu" && playerInput == "playerOneGunting") ||
                    (comInput == "comKertas" && playerInput == "playerOneBatu")
                    ) -> {
                callback.showResult("ComWin")
            }
            else -> {
                callback.showResult("Draw")
            }
        }


    }
}