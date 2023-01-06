package com.example.testsmartapps.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.testsmartapps.R
import com.example.testsmartapps.databinding.FragmentGameBinding
import com.example.testsmartapps.presentation.viewmodels.GameViewModel

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            startButton.setOnClickListener { startGame() }
            rerollButton.setOnClickListener{ reroll() }
            saveButton.setOnClickListener { save() }
            nextButton.setOnClickListener { startGame() }
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun save() {
        binding.aiLayout.visibility = View.VISIBLE
        rollAi()
        endGame()
    }

    private fun reroll() {
        rollPlayer()
        binding.aiLayout.visibility = View.VISIBLE
        rollAi()
        endGame()
    }

    private fun endGame() {
        when (viewModel.compareDices()) {
            "player" -> {
                viewModel.addPlayerScore()
                binding.nextText.setText(R.string.player_win)
            }
            "ai" -> {
                viewModel.addAiScore()
                binding.nextText.setText(R.string.ai_win)
            }
            "even" -> binding.nextText.setText(R.string.even)
        }
        binding.nextButton.visibility = View.VISIBLE
        binding.nextText.visibility = View.VISIBLE
        binding.rerollButton.isEnabled = false
        binding.saveButton.isEnabled = false
    }

    private fun startGame() {
        binding.apply {
            startButton.visibility = View.GONE
            aiLayout.visibility = View.INVISIBLE
            scoreLayout.visibility = View.VISIBLE
            playerLayout.visibility = View.VISIBLE
            buttonsLayout.visibility = View.VISIBLE
            nextButton.visibility = View.GONE
            binding.nextText.visibility = View.GONE
            rerollButton.isEnabled = true
            saveButton.isEnabled = true
        }
        rollPlayer()
    }

    private fun rollPlayer() {
        viewModel.rollDices("player")
        binding.playerFirstDice.setImageResource(selectIcon(viewModel.playerDices[0]))
        binding.playerSecondDice.setImageResource(selectIcon(viewModel.playerDices[1]))
        binding.playerThirdDice.setImageResource(selectIcon(viewModel.playerDices[2]))

    }

    private fun rollAi() {
        viewModel.rollDices("ai")
        binding.aiFirstDice.setImageResource(selectIcon(viewModel.aiDices[0]))
        binding.aiSecondDice.setImageResource(selectIcon(viewModel.aiDices[1]))
        binding.aiThirdDice.setImageResource(selectIcon(viewModel.aiDices[2]))
    }

    private fun selectIcon(dice: Int): Int {
        return when(dice) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}