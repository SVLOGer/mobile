import java.util.Scanner

data class Student(
    val id: Int,
    var name: String,
    val age: Int,
    var points: Int
)

class StudentManager {
    private val students = mutableListOf<Student>()
    private var nextId = 1

    fun addStudent(studentInfo: String) {
        val parts = studentInfo.split(" ")
        if (parts.size != 3) {
            throw IllegalArgumentException("Неверный формат данных. Ожидается: <имя> <возраст> <балл>.")
        }
        val name = parts[0]
        val age = parts[1].toIntOrNull() ?: throw IllegalArgumentException("Возраст должен быть числом.")
        val points = parts[2].toIntOrNull() ?: throw IllegalArgumentException("Баллы должны быть числом.")

        val student = Student(nextId++, name, age, points)
        students.add(student)
    }

    fun removeStudent(id: Int) {
        students.removeIf { it.id == id }
    }

    fun updatePoints(id: Int, newPoints: Int) {
        val student = students.find { it.id == id } ?: throw NoSuchElementException("Студент с ID $id не найден.")
        student.points = newPoints
    }

    fun renameStudent(id: Int, newName: String) {
        val student = students.find { it.id == id } ?: throw NoSuchElementException("Студент с ID $id не найден.")
        student.name = newName
    }

    fun printSortedByPoints(): String {
        return students.sortedByDescending { it.points }
            .joinToString("\n") { "${it.id} ${it.name} (${it.age} лет) - ${it.points} балла" }
    }

    fun printSortedByNames(): String {
        return students.sortedBy { it.name }
            .joinToString("\n") { "${it.id} ${it.name} (${it.age} лет) - ${it.points} балла" }
    }
}

fun main() {
    val studentManager = StudentManager()
    val inputScanner = Scanner(System.`in`)

    while (true) {
        val input = inputScanner.nextLine().trim()
        if (input == "exit") break

        try {
            when {
                input.startsWith("add ") -> studentManager.addStudent(input.removePrefix("add ").trim())
                input.startsWith("remove ") -> {
                    val id = input.removePrefix("remove ").trim().toInt()
                    studentManager.removeStudent(id)
                }
                input.startsWith("update_points ") -> {
                    val parts = input.removePrefix("update_points ").trim().split(" ")
                    val id = parts[0].toInt()
                    val newPoints = parts[1].toInt()
                    studentManager.updatePoints(id, newPoints)
                }
                input.startsWith("rename ") -> {
                    val parts = input.removePrefix("rename ").trim().split(" ")
                    val id = parts[0].toInt()
                    val newName = parts[1]
                    studentManager.renameStudent(id, newName)
                }
                input == "print_sort_by_points" -> studentManager.printSortedByPoints()
                input == "print_sort_by_names" -> studentManager.printSortedByNames()
                else -> println("Неизвестная команда")
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
