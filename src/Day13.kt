import kotlin.math.sqrt

fun main() {

    data class Game(val buttonA: IntArray, val buttonB: IntArray, val prize: LongArray)

    fun parse(input: List<String>): List<Game> {
        val games = mutableListOf<Game>()
        var i = 0
        while (i < input.size) {
            val buttonA = input[i++].split(":")[1].trim().split(", ").map { it.split("+")[1].toInt() }.toIntArray()
            val buttonB = input[i++].split(":")[1].trim().split(", ").map { it.split("+")[1].toInt() }.toIntArray()
            val prize = input[i++].split(":")[1].trim().split(", ").map { it.split("=")[1].toLong() }.toLongArray()
            games.add(Game(buttonA, buttonB, prize))
            if (i < input.size && input[i].isBlank()) i++
        }
        return games
    }

    fun solveEquations(x1: Int, y1: Int, a1: Long, x2: Int, y2: Int, a2: Long): LongArray {
        val det = (x1.toLong() * y2 - y1.toLong() * x2)
        if (det == 0L) return longArrayOf(-1, -1)

        val detX = (a1 * y2 - y1.toLong() * a2)
        val detY = (x1.toLong() * a2 - a1 * x2)
        if (detX % det != 0L || detY % det != 0L) return longArrayOf(-1, -1)

        val aPresses = detX / det
        val bPresses = detY / det

        if (aPresses < 0 || bPresses < 0) return longArrayOf(-1, -1)

        return longArrayOf(aPresses, bPresses)
    }

    fun findMinimumTokens(game: Game): Long? {
        val (buttonA, buttonB, prize) = game
        val solutions = solveEquations(
            buttonA[0], buttonB[0], prize[0],
            buttonA[1], buttonB[1], prize[1]
        )

        val aPresses = solutions[0]
        val bPresses = solutions[1]

        if (aPresses == -1L || bPresses == -1L) return null

        return aPresses * 3 + bPresses
    }

    fun part1(games: List<Game>): Long {
        var totalTokens = 0L

        for (game in games) {
            val tokens = findMinimumTokens(game)
            if (tokens != null) {
                totalTokens += tokens
            }
        }

        return totalTokens
    }

    fun part2(games: List<Game>): Long {
        val updatedGames = games.map { game ->
            game.copy(prize = longArrayOf(game.prize[0] + 10000000000000L, game.prize[1] + 10000000000000L))
        }

        var totalTokens = 0L

        for (game in updatedGames) {
            val tokens = findMinimumTokens(game)
            if (tokens != null) {
                totalTokens += tokens
            }
        }

        return totalTokens
    }


    // Read input from files
    val testInput = readInput("Day13_test")
    println(part1(parse(testInput)))
    println(part2(parse(testInput)))

    val input = readInput("Day13")
    println(part1(parse(input)))
    println(part2(parse(input)))
}