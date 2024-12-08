import kotlin.math.sqrt

fun main() {
    data class LabeledPoint(val label: Char, val x: Int, val y: Int)
    data class LabeledPointGroup(val label: Char, val list: List<Pair<LabeledPoint, LabeledPoint>>) {
        fun antinodes(): List<Pair<Int, Int>> {
            return list.map {
                listOf(
                    Pair(it.first.x + (it.first.x - it.second.x), it.first.y + (it.first.y - it.second.y)),
                    Pair(it.second.x + (it.second.x - it.first.x), it.second.y + (it.second.y - it.first.y))
                )
            }.flatten()
        }

        fun antinodesPart2(boundary: Int): List<Pair<Int, Int>> {
            val points = mutableListOf<Pair<Int, Int>>()
            for ((p1, p2) in list) {
                val a = p1.y - p2.y
                val b = p2.x - p1.x
                val c = p1.x * p2.y - p2.x * p1.y

                for (x in 0..boundary) {
                    for (y in 0..boundary) {
                        if (a * x + b * y + c == 0) {
                            points.add(Pair(x, y))
                        }
                    }
                }
            }

            return points
        }
    }

    fun allUniquePairs(key: Char, value: List<LabeledPoint>): LabeledPointGroup {

        val pairs = mutableListOf<Pair<LabeledPoint, LabeledPoint>>()
        for (i in value.indices) {
            for (j in i + 1 until value.size) {
                pairs.add(Pair(value[i], value[j]));
            }
        }

        return LabeledPointGroup(key, pairs.toList())
    }


    fun isInside(
        boundary: Int,
        pairs: Pair<Int, Int>
    ): Boolean {
        return pairs.first >= 0 && pairs.second >= 0 && pairs.first < boundary && pairs.second < boundary;
    }

    fun part1(input: List<LabeledPoint>): Int {
        val groupBy = input.groupBy(LabeledPoint::label).filter {  it.key != '.' && it.key != '#' }

        val uniqueGroups = groupBy
            .map { allUniquePairs(it.key, it.value) };

        val antinodes = uniqueGroups
            .map { it.antinodes() }.flatten()
            .filter { isInside(sqrt(input.size.toDouble()).toInt(), it) }

        return antinodes.toSet().size
    }


    fun part2(input: List<LabeledPoint>): Int {
        val groupBy = input.groupBy(LabeledPoint::label).filter { it.key != '.' && it.key != '#'}

        val uniqueGroups = groupBy
            .map { allUniquePairs(it.key, it.value) };

        val antinodes = uniqueGroups
            .map { it.antinodesPart2(sqrt(input.size.toDouble()).toInt())}.flatten()
            .filter { isInside(sqrt(input.size.toDouble()).toInt(), it) }

        return antinodes.toSet().size
    }


    fun parse(input: List<String>): List<LabeledPoint> {
        var points = mutableListOf<LabeledPoint>()
        var y = 0;
        for (line in input) {
            line.forEachIndexed() { x, label ->
                points.add(LabeledPoint(label, x, y))
            }
            y++;
        }
        return points;
    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    part1(parse(testInput)).println()
    part2(parse(testInput)).println()

//    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(parse(input)).println()
    part2(parse(input)).println()
}
