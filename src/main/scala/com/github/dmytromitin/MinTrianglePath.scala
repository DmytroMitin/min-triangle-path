package com.github.dmytromitin

import scala.annotation.tailrec
import scala.util.Try
import cats.effect.{IO, IOApp}
import cats.effect.std.Console
import cats.implicits._

object MinTrianglePath extends IOApp.Simple {
  def minPath(triangle: List[List[Int]]): (Int, List[Int]) = {
    val reversed = triangle.reverse
    val (dynamics, bits) = calcDynamicsAndBits(reversed, List.fill(reversed.head.length + 1)(0), Nil)
    (dynamics.head, calcPath(triangle, bits))
  }

  private def calcPath(triangle: List[List[Int]], bits: List[List[Int]]): List[Int] = {
    @tailrec
    def calcPath1(
                   triangle: List[List[Int]],
                   bits: List[List[Int]],
                   bit: Int,
                   indexInRow: Int,
                   res: List[Int]
                 ): List[Int] =
      (triangle, bits) match {
        case (Nil, Nil) => res.reverse
        case (row :: rows, b :: bs) =>
          val newIndexInRow = indexInRow + bit
          calcPath1(rows, bs, b(newIndexInRow), newIndexInRow, row(newIndexInRow) :: res)
      }

    calcPath1(triangle.tail, bits.tail, bits.head.head, 0, triangle.head)
  }

  @tailrec
  private def calcDynamicsAndBits(
                                   reversed: List[List[Int]],
                                   dynamics: List[Int],
                                   bits: List[List[Int]]
                                 ): (List[Int], List[List[Int]]) =
    reversed match {
      case Nil => (dynamics, bits)
      case row :: rows =>
        val (newDyn, newBits) = row.lazyZip(dynamics).lazyZip(dynamics.tail).map((a, d, d1) =>
          (a + math.min(d, d1), if (d <= d1) 0 else 1)
        ).unzip
        calcDynamicsAndBits(rows, newDyn, newBits :: bits)
    }

  def parseTriangle(rows: List[String]): Try[List[List[Int]]] =
    rows.traverse(_.split(" ").toList.traverse(n => Try(n.toInt)))

  private lazy val readLines: IO[List[String]] = for {
    s  <- Console[IO].readLine
    ss <- if (s != "EOF") readLines else IO.pure(Nil)
  } yield s :: ss

  override val run: IO[Unit] =
    for {
      lines       <- readLines
      triangle    <- IO.fromTry(parseTriangle(lines.init))
      (sum, path) = minPath(triangle)
      _           <- Console[IO].println(s"""Minimal path is: ${path.mkString(" + ")} = $sum""")
    } yield ()
}
