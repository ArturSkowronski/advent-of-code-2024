import java.util.PriorityQueue

data class Node(val pos: Pair<Int, Int>, val dir: Pair<Int, Int>, val cost: Int) : Comparable<Node> {
    override fun compareTo(other: Node): Int = this.cost - other.cost

    fun forward(maze: List<List<Char>>): Node? {
        val nextPos = pos.first + dir.first to pos.second + dir.second
        return if (maze[nextPos.first][nextPos.second] != '#') {
            Node(nextPos, dir, cost + 1)
        } else {
            null
        }
    }

    fun left(): Node {
        val newDir = -dir.second to dir.first
        return Node(pos, newDir, cost + 1000)
    }

    fun right(): Node {
        val newDir = dir.second to -dir.first
        return Node(pos, newDir, cost + 1000)
    }
}

fun main() {

    fun parse(input: List<String>): Pair<List<List<Char>>, Pair<Pair<Int, Int>, Pair<Int, Int>>> {
        val maze = input.map { it.toList() }
        val start = maze.flatMapIndexed { i, row -> row.mapIndexed { j, c -> if (c == 'S') i to j else null } }.firstNotNullOf { it }
        val end = maze.flatMapIndexed { i, row -> row.mapIndexed { j, c -> if (c == 'E') i to j else null } }.firstNotNullOf { it }
        return Pair(maze, Pair(start, end))
    }

    fun part1(input: Pair<List<List<Char>>, Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
        val (maze, positions) = input
        val (start, end) = positions

        val pq = PriorityQueue<Node>()
        pq.add(Node(start, 0 to 1, 0))
        val visited = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

        while (pq.isNotEmpty()) {
            val node = pq.poll()

            if (node.pos == end) return node.cost
            if (visited.add(node.pos to node.dir)) {
                node.forward(maze)?.let { pq.add(it) }
                pq.add(node.left())
                pq.add(node.right())
            }
        }
        return -1
    }

    fun part2(input: Pair<List<List<Char>>, Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
        val (maze, positions) = input
        val (start, end) = positions

        val pq = PriorityQueue<Node>()
        pq.add(Node(start, 0 to 1, 0)) // Start facing east
        val visited = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        val preds = mutableMapOf<Node, MutableSet<Node>>()
        var endNode: Node? = null

        while (pq.isNotEmpty()) {
            val node = pq.poll()

            if (visited.add(node.pos to node.dir)) {
                node.forward(maze)?.let { forward ->
                    pq.add(forward)
                    preds.computeIfAbsent(forward) { mutableSetOf() }.add(node)
                }

                val left = node.left()
                pq.add(left)
                preds.computeIfAbsent(left) { mutableSetOf() }.add(node)

                val right = node.right()
                pq.add(right)
                preds.computeIfAbsent(right) { mutableSetOf() }.add(node)

                if (node.pos == end && endNode == null) {
                    endNode = node
                }
            }
        }

        return if (endNode == null) {
            -1
        } else {
            val queue = ArrayDeque<Node>()
            val nodes = mutableSetOf<Node>()
            queue.add(endNode)
            while (queue.isNotEmpty()) {
                val node = queue.removeFirst()
                if (nodes.add(node)) {
                    preds[node]?.forEach { queue.add(it) }
                }
            }
            nodes.map { it.pos }.toSet().size
        }
    }

    val testInput = readInput("Day16_test")
    println(part1(parse(testInput)))
    println(part2(parse(testInput)))
    val input = readInput("Day16")
    println(part1(parse(input)))
    println(part2(parse(input)))
}
