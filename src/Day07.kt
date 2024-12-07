fun main() {

    data class Calculations(val result: Long, val list: List<Long>) {
        fun possible(f: (Long, Long) -> List<Long>): Boolean {
            var possibleResults = listOf<Long>(list[0]);
            list.drop(1).forEach { newValue ->
                possibleResults = possibleResults.map { f(it, newValue) }.flatten()
            }
            return possibleResults.any { it == result }
        }
    }

    fun part1(calculations: List<Calculations>): Long {
        return calculations
            .filter { it.possible { p1, p2 -> listOf(p1 + p2, p1 * p2) } }
            .sumOf { it.result }
    }

    fun part2(calculations: List<Calculations>): Long {
        return calculations
            .filter { it.possible { p1, p2 -> listOf(p1 + p2, p1 * p2, "$p1$p2".toLong()) } }
            .sumOf { it.result }
    }

    fun parse(input: List<String>): List<Calculations> {
        return input.map {
            val split = it.split(":")
            Calculations(split[0].toLong(), split[1].split(" ").filter{it.isNotBlank()}.map { it.toLong() })
        }
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    part1(parse(testInput)).println()
    part2(parse(testInput)).println()

//    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(parse(input)).println()
    part2(parse(input)).println()
}
