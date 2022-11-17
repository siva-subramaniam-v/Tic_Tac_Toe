package com.example.tictactoe.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.others.DRAW
import com.example.tictactoe.others.PLAYER_1
import com.example.tictactoe.others.PLAYER_2
import com.example.tictactoe.others.WIN

class GameViewModel : ViewModel() {
    private val _gameStatus = MutableLiveData<Int>()
    val gameStatus: LiveData<Int>
        get() = _gameStatus

    private var _currentPlayer = PLAYER_1
    val currentPlayer: Char
        get() = _currentPlayer

    private var moveCount = 0

    private val _player1Score = MutableLiveData<Int>()
    val player1Score: LiveData<Int>
        get() = _player1Score

    private val _player2Score = MutableLiveData<Int>()
    val player2Score: LiveData<Int>
        get() = _player2Score

    private var gameBoard = arrayOf(
        arrayOf('\u0000', '\u0000', '\u0000'),
        arrayOf('\u0000', '\u0000', '\u0000'),
        arrayOf('\u0000', '\u0000', '\u0000')
    )

    init {
        _player1Score.value = 0
        _player2Score.value = 0
    }

    fun onPlayerMove(row: Int, col: Int) {
        moveCount++
        gameBoard[row][col] = _currentPlayer

        if (checkWin(row, col, 3)) {
            _gameStatus.value = WIN

            if (_currentPlayer == PLAYER_1) _player1Score.value = _player1Score.value?.plus(1)
            else _player2Score.value = _player2Score.value?.plus(1)

            reset()
        } else if (moveCount == 9) {
            _gameStatus.value = DRAW
            reset()
        } else {
            changePlayer()
        }
    }

    private fun changePlayer() {
        _currentPlayer = if (_currentPlayer == PLAYER_1) PLAYER_2 else PLAYER_1
    }

    private fun checkWin(row: Int, col: Int, size: Int): Boolean {
        // check row
        for (column in 0 until size) {
            if (gameBoard[row][column] != _currentPlayer) break
            if (column == size - 1) return true
        }

        // check col
        for (rows in 0 until size) {
            if (gameBoard[rows][col] != _currentPlayer) break
            if (rows == size - 1) return true
        }

        // check diagonal 1
        if (row == col) {
            for (index in 0 until size) {
                if (gameBoard[index][index] != _currentPlayer) break
                if (index == size - 1) return true
            }
        }

        // check diagonal 2
        if (col == (size - 1) - row) {
            for (rows in 0 until size) {
                if (gameBoard[rows][(size - 1) - rows] != _currentPlayer) break
                if (rows == size - 1) return true
            }
        }

        return false
    }

    private fun reset() {
        gameBoard = arrayOf(
            arrayOf('\u0000', '\u0000', '\u0000'),
            arrayOf('\u0000', '\u0000', '\u0000'),
            arrayOf('\u0000', '\u0000', '\u0000')
        )
        moveCount = 0
        _gameStatus.value = -1
        _currentPlayer = PLAYER_1
    }
}