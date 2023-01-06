package com.example.testsmartapps.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    private val _playerDiceScore = MutableLiveData<String>()
    val playerDiceScore: LiveData<String> = _playerDiceScore
    private val _aiDiceScore = MutableLiveData<String>()
    val aiDiceScore: LiveData<String> = _aiDiceScore

    private val _playerScore = MutableLiveData("0")
    val playerScore: LiveData<String> = _playerScore
    private val _aiScore = MutableLiveData("0")
    val aiScore: LiveData<String> = _aiScore

    val playerDices = mutableListOf(1, 2, 3)
    val aiDices = mutableListOf(1, 2, 3)

    fun rollDices(player: String) {
        if (player == "player") {
            playerDices[0] = (1..6).random()
            playerDices[1] = (1..6).random()
            playerDices[2] = (1..6).random()
            _playerDiceScore.value = (playerDices[0]+playerDices[1]+playerDices[2]).toString()
        } else {
            aiDices[0] = (1..6).random()
            aiDices[1] = (1..6).random()
            aiDices[2] = (1..6).random()
            _aiDiceScore.value = (aiDices[0]+aiDices[1]+aiDices[2]).toString()
        }
    }

    fun compareDices(): String {
        return if (_playerDiceScore.value?.toInt()!! > _aiDiceScore.value?.toInt()!!) {
            "player"
        } else if (_playerDiceScore.value?.toInt()!! < _aiDiceScore.value?.toInt()!!) {
            "ai"
        } else {
            "even"
        }
    }

    fun addPlayerScore() {
        _playerScore.value = (_playerScore.value?.toInt()!!+1).toString()
    }

    fun addAiScore() {
        _aiScore.value = (_aiScore.value?.toInt()!!+1).toString()
    }
}