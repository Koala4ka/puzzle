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

    val lines: Array[String] = Using.resource(Source.fromFile(filename, "UTF-8")) { source =>
      source.getLines()
        .map(_.trim)
        .filter(_.matches("""\d+"""))
        .toArray
    }

    if (lines.isEmpty) {
      println("File contains no valid numeric lines.")
      sys.exit(1)
    }

    val fragments: Array[Fragment] = lines.map { s =>
      Fragment(
        from = toInt2(s.take(2)),
        to = toInt2(s.takeRight(2)),
        weight = s.length - 2
      )
    }

    val graph: Array[List[Int]] = Array.fill(100)(List.empty)
    fragments.indices.foreach { i =>
      graph(fragments(i).from) ::= i
    }

    def dfs(node: Int, used: Set[Int], currentSequence: List[Int]): (Int, List[Int]) = {
      val successors = graph(node).filterNot(used.contains)
      if (successors.isEmpty) {
        val length = currentSequence.map(i => fragments(i).weight).sum + 2
        (length, currentSequence)
      } else {
        successors
          .map { i =>
            dfs(fragments(i).to, used + i, currentSequence :+ i)
          }
          .maxBy(_._1)
      }
    }

    val allResults = fragments.indices.map { i =>
      dfs(fragments(i).to, Set(i), List(i))
    }

    val (maxLength, bestSequence) = allResults.maxBy(_._1)
    val resultSequence = bestSequence.map(i => lines(i)).mkString("")

    println(s"Max length: $maxLength")
    println(s"Max sequence: $resultSequence")
  }
}