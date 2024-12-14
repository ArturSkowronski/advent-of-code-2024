fun main() {

    data class Point(val x: Int, val y: Int)


    data class Robot(val initialPos: Point, val velocities: Pair<Int, Int>, val map: Pair<Int, Int>) {
        val poss = mutableListOf<Point>(initialPos)
        var curpos = initialPos
        fun pos() = poss.last()

        fun tick() {
            val lastPos = poss.last()
            var newX = lastPos.x + velocities.first;
            var newY = lastPos.y + velocities.second;

            if(newX > map.first ) { newX = newX - map.first -1 }
            if(newX < 0 ) { newX = map.first + newX + 1}

            if(newY > map.second ) { newY = newY - map.second -1 }
            if(newY < 0 ) { newY = map.second + newY +1}
            curpos = Point(newX, newY)
            poss.add(Point(newX, newY))
        }
    }


    data class Quadrant(val number: Int, val map: Pair<Int, Int>) {
        lateinit var TLPoint: Point
        lateinit var BRPoint: Point

        init {

            if(number == 0) {
                val minX = 0
                val maxX =  (map.first / 2).toInt() - 1
                val minY = 0
                val maxY =  (map.second / 2).toInt() - 1
                TLPoint = Point(minX, minY)
                BRPoint = Point(maxX, maxY)
            }

            if(number == 1) {
                val minX = (map.first / 2).toInt() + 1
                val maxX = map.first
                val minY = 0
                val maxY =  (map.second / 2).toInt() - 1
                TLPoint = Point(minX, minY)
                BRPoint = Point(maxX, maxY)
            }

            if(number == 2) {
                val minX = 0
                val maxX = (map.first / 2).toInt() - 1
                val minY = (map.second / 2).toInt() + 1
                val maxY =  map.second
                TLPoint = Point(minX, minY)
                BRPoint = Point(maxX, maxY)
            }

            if(number == 3) {
                val minX = (map.first / 2).toInt() + 1
                val maxX = map.first
                val minY = (map.second / 2).toInt() + 1
                val maxY =  map.second
                TLPoint = Point(minX, minY)
                BRPoint = Point(maxX, maxY)
            }
        }

        fun inQuadrant(robots: List<Robot>): List<Robot> {
            val robots1 = robots.filter{
                val rPos = it.pos()
                rPos.x >= TLPoint.x && rPos.x <= (BRPoint.x) && rPos.y >= TLPoint.y && rPos.y <= (BRPoint.y)
            }
            return robots1;
        }
    }

    fun parse(input: List<String>): List<Robot> {
        val map = input.first().split(",").map { it.toInt() }
        return input.drop(1).map {
            val split = it.split(" ")
            val p = split[0].split(",").map { it.replace("p=", "") }.map { it.toInt() }
            val v = split[1].split(",").map { it.replace("v=", "") }.map { it.toInt() }
            Robot(Point(p[0], p[1]), Pair(v[0], v[1]), Pair(map[0] - 1, map[1] - 1))
        }
    }


    fun part1(robots: List<Robot>): Int {
        val map = robots.first().map;
        val quadrants = listOf<Quadrant>(
            Quadrant(0, map),
            Quadrant(1, map),
            Quadrant(2, map),
            Quadrant(3, map)
        )

        (0..99).forEach {
            robots.forEach {
                it.tick()
            }
        }

        val quad = quadrants.map {it.inQuadrant(robots)}.map{it.size}

        return quad.reduce { p1, p2 -> p1 * p2 };
    }


    fun renderMatrix(tick: Long, map: Pair<Int, Int>, robots: List<Robot>) {
        val matrix = Array(map.second + 1) { CharArray(map.first + 1) { '.' } }

        robots.forEach {
            val pos = it.pos()
            matrix[pos.y][pos.x] = 'R'
        }

        val hasFiveConsecutiveRs = matrix.any { row ->
            row.joinToString("").contains("RRRRR")
        }

        if (hasFiveConsecutiveRs) {
            println("Tick: $tick")

            matrix.forEach { row ->
                println(row.concatToString())
            }
            println()
        }
    }

    fun part2(robots: List<Robot>): Long {
        val map = robots.first().map;
        val quadrants = listOf<Quadrant>(
            Quadrant(0, map),
            Quadrant(1, map),
            Quadrant(2, map),
            Quadrant(3, map)
        )

        (0..10000L).forEach {
            robots.forEach {
                it.tick()
            }
            renderMatrix(it, map, robots)

        }
        return 0
    }

    // Read input from files
    val testInput = readInput("Day14_test")
    println(part1(parse(testInput)))
//    println(part2(parse(testInput)))

    val input = readInput("Day14")
    println(part1(parse(input)))
    println(part2(parse(input)))
}