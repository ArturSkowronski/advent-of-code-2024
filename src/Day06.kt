enum class DIRECTIONS(val direction: Pair<Int, Int>) {
    U(Pair(0, -1)),
    D(Pair(0, 1)),
    R(Pair(1, 0)),
    L(Pair(-1, 0))
}

fun main() {

    data class PrintingRecords(val printingRules: List<Pair<Int, Int>>, val printingInstruction: List<List<Int>>)

    fun findGuard(list: List<List<Char>>): Pair<Int, Int> {
        for (y in 0..<list.size) {
            for (x in 0..<list[0].size) {
                if (list[y][x] == '^') return Pair(x, y)
            }
        }
        throw Exception("No guards found")
    }

    fun newDirection(direction: DIRECTIONS): DIRECTIONS {
        return when (direction) {
            DIRECTIONS.U -> DIRECTIONS.R
            DIRECTIONS.R -> DIRECTIONS.D
            DIRECTIONS.D -> DIRECTIONS.L
            DIRECTIONS.L -> DIRECTIONS.U
        }
    }


    fun part1(input: MutableList<MutableList<Char>>): Int {
        var position = findGuard(input)
        var direction = DIRECTIONS.U

        println(position)
        try {
            while (true) {
                val newPosition =
                    Pair(position.first + direction.direction.first, position.second + direction.direction.second)
                if (input[newPosition.second][newPosition.first] == '#') {
                    direction = newDirection(direction)
                } else {
                    position = newPosition
                    input[position.second][position.first] = 'X'
                }
            }
        } catch (e: Exception) {
            return input.sumOf { it.count { it == 'X' } };
        };

        return 0;
    }

    fun parse(input: List<String>): MutableList<MutableList<Char>> {
        return input.map { it.toMutableList() }.toMutableList()
    }

    fun newVariant(input: MutableList<MutableList<Char>>, x: Int, y: Int): Int {
        var loop = 0;
        val maxSize = input.size * input.size + 100
        var position = findGuard(input)
        var direction = DIRECTIONS.U
        input[y][x] = 'O'
        try {
            while (loop < maxSize) {
                val newPosition =
                    Pair(position.first + direction.direction.first, position.second + direction.direction.second)

                if (input[newPosition.second][newPosition.first] == '#' || input[newPosition.second][newPosition.first] == 'O') {
                    direction = newDirection(direction)
                } else {
                    position = newPosition
                    input[position.second][position.first] = 'X'
                }
                loop++
            }

        } catch (e: Exception) {
            return 0
        }

        println("$x,$y")
        input.forEach { println(it.joinToString(separator = "")) }

        return 1
    }


    fun part2(input: MutableList<MutableList<Char>>, input1: List<String>): Int {
        var size = 0;
        for (y in 0..<input.size) {
            for (x in 0..<input[0].size) {
                size += newVariant(parse(input1), x, y)
            }
        }

        return size
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    part1(parse(testInput)).println()
    part2(parse(testInput), testInput).println()

//    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(parse(input)).println()
    part2(parse(input), input).println()
}
