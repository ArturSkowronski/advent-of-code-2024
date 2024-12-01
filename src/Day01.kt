import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val inputElements = input.map() {
            val (first, second) = it.split("   ")
            first.toInt() to second.toInt()
        }
        val firstList = inputElements.map { it.first.toInt() }.sorted()
        val secondList = inputElements.map { it.second.toInt() }.sorted()
        return (0..(input.size - 1)).sumOf {
            abs(firstList[it] - secondList[it])
        }
    }

    fun part2(input: List<String>): Int {
        val inputElements = input.map() {
            val (first, second) = it.split("   ")
            first.toInt() to second.toInt()
        }
        val firstList = inputElements.map { it.first.toInt() }
        val occurrenceList = inputElements.map { it.second.toInt() }.groupingBy { it }.eachCount()

        return (0..(input.size - 1)).sumOf {
            firstList[it] * occurrenceList.getOrElse(firstList[it], { 0 })
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    part1(testInput).println()
    part2(testInput).println()
    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
