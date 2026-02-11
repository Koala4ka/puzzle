import scala.io.Source
import scala.util.Using

object PuzzleApp {

  def main(args: Array[String]): Unit = {

    if (args.length != 1) {
      println("Usage: sbt run numbers.txt")
      sys.exit(1)
    }

    val filename = args(0)

    val file = new java.io.File(filename)
    if (!file.exists()) {
      println(s"File not found: $filename")
      println("Working directory: " + new java.io.File(".").getAbsolutePath)
      sys.exit(1)
    }

    val lines = Using.resource(Source.fromFile(filename, "UTF-8")) { source =>
      source.getLines()
        .map(_.trim)
        .filter(_.matches("""\d+"""))
        .toArray
    }

    if (lines.isEmpty) {
      println("File contains no valid numeric lines.")
      sys.exit(1)
    }

    val fragments = lines.map { s =>
      Fragment(
        from = toInt2(s.take(2)),
        to = toInt2(s.takeRight(2)),
        weight = s.length - 2
      )
    }

    val graph = Array.fill(100)(List.empty[Int])
    fragments.indices.foreach { i =>
      graph(fragments(i).from) ::= i
    }

    val used = Array.fill(fragments.length)(false)
    var bestLength = 0

    def dfs(node: Int, current: Int): Unit = {
      if (current > bestLength)
        bestLength = current

      for (i <- graph(node)) {
        if (!used(i)) {
          used(i) = true
          dfs(fragments(i).to, current + fragments(i).weight)
          used(i) = false
        }
      }
    }

    fragments.indices.foreach { i =>
      used(i) = true
      dfs(fragments(i).to, fragments(i).weight)
      used(i) = false
    }

    println(s"Max length: ${bestLength + 2}")
  }
}