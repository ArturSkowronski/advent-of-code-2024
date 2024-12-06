fun part1(input: List<String>): Long {
    var count = 0L
    for (row in input.indices) {
        for (col in input[row].indices) {
            if (input[row][col] == 'X') {
                count += findAllXMAS(input, row, col)
            }
        }
    }
    return count
}

fun findAllXMAS(input: List<String>, row: Int, col: Int): Int {
    return matchXMAS(input, row, col, -1, -1) +
            matchXMAS(input, row, col, -1, 0) +
            matchXMAS(input, row, col, -1, 1) +
            matchXMAS(input, row, col, 0, -1) +
            matchXMAS(input, row, col, 0, 1) +
            matchXMAS(input, row, col, 1, -1) +
            matchXMAS(input, row, col, 1, 0) +
            matchXMAS(input, row, col, 1, 1)
}

fun matchXMAS(input: List<String>, row: Int, col: Int, rowDelta: Int, colDelta: Int): Int {
    return if (
        matchLetter(input, row + rowDelta, col + colDelta, 'M') &&
        matchLetter(input, row + rowDelta * 2, col + colDelta * 2, 'A') &&
        matchLetter(input, row + rowDelta * 3, col + colDelta * 3, 'S')
    ) 1 else 0
}

fun part2(input: List<String>): Long {
    var count = 0L
    for (row in input.indices) {
        for (col in input[row].indices) {
            if (input[row][col] == 'A') {
                count += matchMAS(input, row, col)
            }
        }
    }
    return count
}

fun matchMAS(input: List<String>, row: Int, col: Int): Int {
    val match1 = (
            (matchLetter(input, row - 1, col - 1, 'M') && matchLetter(input, row + 1, col + 1, 'S')) ||
                    (matchLetter(input, row - 1, col - 1, 'S') && matchLetter(input, row + 1, col + 1, 'M'))
            )
    val match2 = (
            (matchLetter(input, row + 1, col - 1, 'M') && matchLetter(input, row - 1, col + 1, 'S')) ||
                    (matchLetter(input, row + 1, col - 1, 'S') && matchLetter(input, row - 1, col + 1, 'M'))
            )
    return if (match1 && match2) 1 else 0
}

fun matchLetter(input: List<String>, row: Int, col: Int, letter: Char): Boolean {
    if (row < 0 || row >= input.size) return false
    if (col < 0 || col >= input[0].length) return false
    return input[row][col] == letter
}

fun main() {
    val input = readInput("Day04")

    println(part1(input))
    println(part2(input))
}
