package com.example.myapplication

import Context
import Translate
import Translator
import Word
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val translator = Translator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputWord = findViewById<EditText>(R.id.inputWord)
        val inputContext = findViewById<EditText>(R.id.inputContext)
        val inputTranslate = findViewById<EditText>(R.id.inputTranslate)
        val outputText = findViewById<TextView>(R.id.outputText)

        findViewById<Button>(R.id.addButton).setOnClickListener {
            val word = Word(inputWord.text.toString().trim().lowercase())
            val context = Context(inputContext.text.toString().trim().lowercase())
            val translate = Translate(inputTranslate.text.toString().trim().lowercase())

            translator.add(word, context, translate)
            outputText.text = "Добавлен перевод для слова: ${word.value}"
        }

        findViewById<Button>(R.id.removeButton).setOnClickListener {
            val word = Word(inputWord.text.toString().trim().lowercase())
            val context = Context(inputContext.text.toString().trim().lowercase())
            val translate = Translate(inputTranslate.text.toString().trim().lowercase())

            translator.remove(word, context, translate)
            outputText.text = "Удалён перевод для слова: ${word.value}"
        }

        findViewById<Button>(R.id.translateButton).setOnClickListener {
            val word = Word(inputWord.text.toString().trim().lowercase())
            val contextMap = translator.getTranslate(word)

            if (contextMap != null && contextMap.isNotEmpty()) {
                val result = StringBuilder("Переводы для слова «${word.value}»:\n")
                for ((context, translations) in contextMap) {
                    result.append("В контексте «${context.name}»: ${translations.joinToString(", ") { it.value }}\n")
                }
                outputText.text = result.toString()
            } else {
                outputText.text = "Переводы для слова «${word.value}» не найдены"
            }
        }

        findViewById<Button>(R.id.printButton).setOnClickListener {
            val allWords = translator.words()

            if (allWords.isEmpty()) {
                outputText.text = "Словарь пуст"
            } else {
                val result = StringBuilder("Словарь:\n")
                for ((word, contextMap) in allWords) {
                    result.append("Слово: ${word.value}\n")
                    for ((context, translations) in contextMap) {
                        result.append("  В контексте «${context.name}»: ${translations.joinToString(", ") { it.value }}\n")
                    }
                }
                outputText.text = result.toString()
            }
        }
    }
}
