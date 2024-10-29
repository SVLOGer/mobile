import android.util.Log

data class Word(val value: String) {
    init {
        require(value.isNotBlank()) { "Слово не может быть пустым" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Word) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

data class Context(val name: String) {
    init {
        require(name.isNotBlank()) { "Контекст не может быть пустым" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Context) return false
        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

data class Translate(val value: String) {
    init {
        require(value.isNotBlank()) { "Перевод не может быть пустым" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Translate) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

typealias ContextMap = MutableMap<Context, MutableList<Translate>>

class Translator {
    private val dictionary: MutableMap<Word, ContextMap> = mutableMapOf()

    fun add(word: Word, context: Context, translate: Translate) {
        val contextMap = dictionary.getOrPut(word) { mutableMapOf() }
        val translations = contextMap.getOrPut(context) { mutableListOf() }

        if (translate !in translations) {
            translations.add(translate)
        }
    }

    fun remove(word: Word, context: Context, translate: Translate) {
        dictionary[word]?.get(context)?.let { translations ->
            translations.remove(translate)
            if (translations.isEmpty()) {
                dictionary[word]?.remove(context)
            }
            if (dictionary[word]?.isEmpty() == true) {
                dictionary.remove(word)
            }
        }
    }

    fun getTranslate(word: Word): ContextMap? {
        return dictionary[word]
    }

    fun words(): Map<Word, ContextMap> {
        return dictionary
    }
}

fun main() {
    val translator = Translator()
    while (true) {
        println("Введите команду:")
        val input = readLine() ?: continue
        val tokens = input.split(" ")

        when (tokens[0]) {
            "add" -> {
                val word = Word(tokens[1])
                val context = Context(tokens[2])
                val translate = Translate(tokens[3])
                translator.add(word, context, translate)
                println("Добавлен перевод: ${tokens[3]} для слова ${tokens[1]} в контексте ${tokens[2]}")
            }

            "remove" -> {
                val word = Word(tokens[1])
                val context = Context(tokens[2])
                val translate = Translate(tokens[3])
                translator.remove(word, context, translate)
                println("Удален перевод: ${tokens[3]} для слова ${tokens[1]} в контексте ${tokens[2]}")
            }

            "translate" -> {
                val word = Word(tokens[1])
                val contextMap = translator.getTranslate(word)
                if (contextMap != null) {
                    println("Перевод слова «${word.value}»")
                    for ((context, translations) in contextMap) {
                        println("В контексте «${context.name}»: ${translations.joinToString(", ") { it.value }}")
                    }
                } else {
                    println("Переводы для слова «${word.value}» не найдены")
                }
            }

            "print" -> {
                val allWords = translator.words()
                for ((word, contextMap) in allWords) {
                    println("Перевод слова «${word.value}»")
                    for ((context, translations) in contextMap) {
                        println("В контексте «${context.name}»: ${translations.joinToString(", ") { it.value }}")
                    }
                }
            }

            "exit" -> {
                println("Завершение программы...")
                return
            }

            else -> {
                println("Неизвестная команда")
            }
        }
    }
}
