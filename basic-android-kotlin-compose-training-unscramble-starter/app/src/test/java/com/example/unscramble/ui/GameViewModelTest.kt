package com.example.unscramble.ui

import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()

    // Проверка правильного ответа
    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    // Неверный ввод
    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        viewModel.updateUserGuess("WRONG_WORD")
        viewModel.checkUserGuess()

        val currentGameUiState = viewModel.uiState.value
        assertTrue(currentGameUiState.isGuessedWordWrong)
        assertEquals(0, currentGameUiState.score)
    }

    // Инициализация (загрузка первого слова)
    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val currentGameUiState = viewModel.uiState.value
        assertTrue(currentGameUiState.currentScrambledWord.isNotEmpty())
        assertEquals(1, currentGameUiState.currentWordCount)
        assertFalse(currentGameUiState.isGameOver)
    }

    // Пропуск слова (счёт не меняется, счётчик растёт)
    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
        val initialGameUiState = viewModel.uiState.value

        viewModel.skipWord()

        val newGameUiState = viewModel.uiState.value
        assertEquals(initialGameUiState.score, newGameUiState.score)
        assertEquals(initialGameUiState.currentWordCount + 1, newGameUiState.currentWordCount)
        assertFalse(newGameUiState.isGuessedWordWrong)
    }

    // Все слова угаданы
    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        repeat(MAX_NO_OF_WORDS) {
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        }

        assertTrue(currentGameUiState.isGameOver)
        assertEquals(expectedScore, currentGameUiState.score)
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }
}