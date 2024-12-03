import kotlin.math.abs

fun main() {

    fun getNumbers(el: String): List<Int> {
        return el.split("(")[1].split(",").map { it.replace(")", "") }.map { it.toInt() }
    }

    fun processRow(input: String): Long {
        val regex = Regex("""mul\(\d+,\d+\)""")
        val matches = regex.findAll(input).map { it.value }.toList()
        return matches.map { getNumbers(it) }.sumOf { it[0] * it[1] }.toLong()
    }

    fun part1(input: List<String>): Int {
        return input.map { processRow(it) }.sumOf { it }.toInt()
    }


    fun part2(input: List<String>): Long {
        val regex = Regex("""(don't\(\))|(do\(\))|(mul\((\d+),(\d+)\))""")
        var enabled = true
        return input.sumOf { line ->
            regex.findAll(line).sumOf { match ->
                when {
                    match.value.startsWith("mul") && enabled -> {
                        val params = match.value.substringAfter("mul(").substringBefore(")").split(",")
                        val x = params[0].toLong()
                        val y = params[1].toLong()
                        x * y
                    }
                    match.value.startsWith("don't") -> {
                        enabled = false
                        0L
                    }
                    match.value.startsWith("do") -> {
                        enabled = true
                        0L
                    }
                    else -> 0L
                }
            }
        }
    }

    fun parse(input: List<String>): List<String> {
        return input;
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    part1(parse(testInput)).println()
    part2(parse(testInput)).println()
    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(parse(input)).println()
    println("two")
    part2(parse(input)).println()
}
