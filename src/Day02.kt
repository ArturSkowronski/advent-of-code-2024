import kotlin.math.abs

fun main() {

    fun allIncreasing(input: List<Int>): Boolean {
        return input.zipWithNext { a, b -> (a > b && a < b + 4) }.all { it }
    }

    fun checkRow(input: List<Int>): Boolean = allIncreasing(input) || allIncreasing(input.reversed())

    fun rowGenerator(row: List<Int>): List<List<Int>> {
        return (0..row.size).map { i ->
            row.take(i) + row.drop(i + 1)
        }
    }

    fun part1(input: List<List<Int>>): Int {
        return input.map { checkRow(it) }.count { it }

    }

    fun part2(input: List<List<Int>>): Int {
        return input.map { rowGenerator(it).map { checkRow(it) }.any {it} }.count { it }
    }

    fun parse(input: List<String>): List<List<Int>> {
        return input.map { line ->
            line.split(" ").map { it.toInt() }
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    part1(parse(testInput)).println()
    part2(parse(testInput)).println()
    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(parse(input)).println()
    part2(parse(input)).println()
}
