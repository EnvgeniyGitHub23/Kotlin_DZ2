// Домашнее задание №2 от 05.10.2026


import kotlin.random.Random

fun main() {
    println("=== Игра «УГАДАЙ ЧИСЛО» ===")

    do {
        val settings = chooseDifficulty()
        playGame(settings)
    } while (askPlayAgain())
}

// ===== Настройки игры =====
data class GameSettings(
    val min: Int,
    val max: Int,
    val attempts: Int
)

// ===== Выбор уровня сложности =====
fun chooseDifficulty(): GameSettings {
    println("\nВыберите урвень сложности:")
    println("1 - Лёгкий (0..10, 3 попытки)")
    println("2 - Средний (0..100, 5 попыток)")
    println("3 - Сложный (0..1000, 7 попыток)")
    println("4 - Свои настройки")

    return when (readIntInput("Ваш выбор: ")) {
        1 -> GameSettings(0, 10, 3)
        2 -> GameSettings(0, 100, 5)
        3 -> GameSettings(0, 1000, 7)
        4 -> customSettings()
        else -> {
            println("Неверный выбор, будет выбран лёгкий уровень.")
            GameSettings(0, 10, 3)
        }
    }
}

// ===== Кастомные настройки =====
fun customSettings(): GameSettings {
    val min = readIntInput("Введите нижнюю границу диапазона: ")
    var max = readIntInput("Введите верхнюю границу диапазона: ")
    while (max <= min) {
        println("Верхняя граница должна быть больше нижней ($min).")
        max = readIntInput("Введите верхнюю границу диапазона: ")
    }
    var attempts = readIntInput("Введите количество попыток: ")
    while (attempts <= 0) {
        println("Количество попыток должно быть положительным.")
        attempts = readIntInput("Введитее количество попыток: ")
    }
    return GameSettings(min, max, attempts)
}

// ===== Основная логика игры =====
fun playGame(settings: GameSettings) {
    val secret = Random.nextInt(settings.min, settings.max + 1)
    var attemptsLeft = settings.attempts

    println("\nЯ загадал число от ${settings.min} до ${settings.max}.")
    println("У вас ${settings.attempts} попыток(ки).")

    while (attemptsLeft > 0) {
        val guess = readIntInput("Попытка (осталось $attemptsLeft). Ваш вариант: ")

        when {
            guess == secret -> {
                println("Вы угадали! Загаданное число: $secret")
                return
            }
            guess < secret -> println("Загаданное число БОЛЬШЕ вашего.")
            else -> println("Загаданное число МЕНЬШЕ вашего.")
        }

        attemptsLeft--
    }

    println("😔 Попытки закончились. Было загадано число: $secret")
}

// ===== Запрос на повтор игры =====
fun askPlayAgain(): Boolean {
    while (true) {
        println("\nПовторить игру ещё раз? 1 - да / 0 - нет")
        when (readIntInput("Ваш выбор: ")) {
            1 -> return true
            0 -> {
                println("Спасибо за игру!")
                return false
            }
            else -> println("Введите 1 или 0.")
        }
    }
}

// =====  чтение целого числа =====
fun readIntInput(prompt: String): Int {
    while (true) {
        print(prompt)
        val input = readLine()?.trim()
        val number = input?.toIntOrNull()
        if (number != null) return number
        println("Ошибка: введите целое число.")
    }
}

