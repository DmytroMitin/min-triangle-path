import scala.annotation.tailrec

object MinTrianglePath {
  def minPath(triangle: List[List[Int]]): Int = {
    val reversed = triangle.reverse
    val dynamics = calcDynamics(reversed, List.fill(reversed.head.length + 1)(0))
    dynamics.head
  }

  @tailrec
  def calcDynamics(reversed: List[List[Int]], dynamics: List[Int]): List[Int] =
    reversed match {
      case Nil => dynamics
      case row :: rows =>
        val newDyn = row.lazyZip(dynamics).lazyZip(dynamics.tail).map((a, d, d1) => a + math.min(d, d1))
        calcDynamics(rows, newDyn)
    }

  // rows nonempty, triangle nonempty
  def main(args: Array[String]): Unit = {
    println(minPath(List(
      List(7),
      List(6, 3),
      List(3, 8, 5),
      List(11, 2, 10, 9),
    )))
  }
}
