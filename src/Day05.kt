fun main() {

    data class PrintingRecords(val printingRules: List<Pair<Int, Int>>, val printingInstruction: List<List<Int>>)

    fun part1(input: PrintingRecords): Int {
        return input.printingInstruction.map { instruction ->
            val valid = input.printingRules.all { (first, second) ->
                if(!instruction.contains(first) || !instruction.contains(second)) true else
                instruction.indexOfFirst { i -> i == first } < instruction.indexOfFirst { i -> i == second }
            }
            if (valid) instruction[instruction.size / 2] else 0
        }.sumOf { it }
    }

    fun part2(input: PrintingRecords): Long {
        fun reorder(instruction: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
            return instruction.sortedWith { a, b ->
                when {
                    rules.any { it.first == a && it.second == b } -> -1
                    rules.any { it.first == b && it.second == a } -> 1
                    else -> 0
                }
            }
        }

        return input.printingInstruction.mapNotNull { instruction ->
            val isValid = input.printingRules.all { (first, second) ->
                !instruction.contains(first) || !instruction.contains(second) ||
                        instruction.indexOf(first) < instruction.indexOf(second)
            }
            if (!isValid) {
                val sorted = reorder(instruction, input.printingRules)
                sorted[sorted.size / 2].toLong()
            } else null
        }.sum()
    }

    fun parse(input: List<String>): PrintingRecords {
        val index = input.indexOfFirst(String::isBlank);
        val printingRules = input.subList(0, index).map { it.split("|") }.map { Pair(it[0].toInt(), it[1].toInt()) }
        val printingInstruction =
            input.subList(index + 1, input.size).map { it.split(",").map { el -> el.toInt() }.toList() }
        return PrintingRecords(printingRules, printingInstruction);
    }

    val testInput = readInput("Day05_test")
    println(part1(parse(testInput)))
    println(part2(parse(testInput)))

    val input = readInput("Day05")
    println(part1(parse(input)))
    println(part2(parse(input)))
}
