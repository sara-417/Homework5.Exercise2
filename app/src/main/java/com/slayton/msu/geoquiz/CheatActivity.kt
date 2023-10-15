package com.slayton.msu.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.slayton.msu.geoquiz.databinding.ActivityCheatBinding
import com.slayton.msu.geoquiz.databinding.ActivityMainBinding

const val EXTRA_ANSWER_SHOWN =
    "com.slayton.msu.geoquiz.anwswer_shown"

private const val EXTRA_ANSWER_IS_TRUE =
    "com.slayton.msu.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCheatBinding
    private var answerIsTrue = false
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        val answerShown = quizViewModel.answerShown

        if(answerShown) {
            setAnswerText()
        }

        binding.showAnswerButton.setOnClickListener {
            setAnswerText()
            quizViewModel.answerShown = true
            setAnswerShownResult(quizViewModel.answerShown)
        }
    }

    private fun setAnswerText() {
        val answerText = when {
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        binding.answerTextView.setText(answerText)
    }

    private fun setAnswerShownResult(isAnswerShown:Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
    companion object {
        fun newIntent(packageContext: Context, answerIsTrue:Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}