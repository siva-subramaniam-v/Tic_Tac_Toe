package com.example.tictactoe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.others.DRAW
import com.example.tictactoe.others.PLAYER_1
import com.example.tictactoe.others.PLAYER_2
import com.example.tictactoe.others.WIN
import com.example.tictactoe.viewmodels.GameViewModel

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.gameViewModel = gameViewModel

        binding.apply {
            cell1.setOnClickListener {
                onCellClicked(it)
            }

            cell2.setOnClickListener {
                onCellClicked(it)
            }

            cell3.setOnClickListener {
                onCellClicked(it)
            }

            cell4.setOnClickListener {
                onCellClicked(it)
            }

            cell5.setOnClickListener {
                onCellClicked(it)
            }

            cell6.setOnClickListener {
                onCellClicked(it)
            }

            cell7.setOnClickListener {
                onCellClicked(it)
            }

            cell8.setOnClickListener {
                onCellClicked(it)
            }

            cell9.setOnClickListener {
                onCellClicked(it)
            }
        }

        gameViewModel.gameStatus.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    WIN -> {
                        Toast.makeText(requireContext(),"${gameViewModel.currentPlayer} won!", Toast.LENGTH_LONG).show()
                        clearUI()
                    }
                    DRAW -> {
                        Toast.makeText(requireContext(), "Match draw!", Toast.LENGTH_LONG).show()
                        clearUI()
                    }
                }
            }
        }

        gameViewModel.player1Score.observe(viewLifecycleOwner) {
            it?.let {
                binding.player1Score.text = it.toString()
            }
        }

        gameViewModel.player2Score.observe(viewLifecycleOwner) {
            it?.let {
                binding.player2Score.text = it.toString()
            }
        }

        return binding.root
    }

    private fun onCellClicked(_cell: View) {
        val cell = _cell as TextView

        if (cell.text.isNullOrBlank()) {
            val (row, col) = cell.tag.toString().split(",").map { it.toInt() }
            updateCell(cell)
            gameViewModel.onPlayerMove(row, col)
        }
    }

    private fun updateCell(cell: TextView) {
        when (gameViewModel.currentPlayer) {
            PLAYER_1 -> {
                cell.text = PLAYER_1.toString()
                cell.setTextColor(requireContext().getColor(R.color.x))
            }

            PLAYER_2 -> {
                cell.text = PLAYER_2.toString()
                cell.setTextColor(requireContext().getColor(R.color.o))
            }
        }
    }

    private fun clearUI() {
        binding.apply {
            cell1.text = ""
            cell2.text = ""
            cell3.text = ""
            cell4.text = ""
            cell5.text = ""
            cell6.text = ""
            cell7.text = ""
            cell8.text = ""
            cell9.text = ""
        }
    }
}