package com.github.dmytromitin

import scala.annotation.tailrec
import scala.util.Try
import cats.implicits._

import scala.io.StdIn

object MinTrianglePath {
  def minPath(triangle: List[List[Int]]): (Int, List[Int]) = {
    val reversed = triangle.reverse
    val (dynamics, bits) = calcDynamicsAndBits(reversed, List.fill(reversed.head.length + 1)(0), Nil)
    (dynamics.head, calcPath(triangle, bits))
  }

  def calcPath(triangle: List[List[Int]], bits: List[List[Int]]): List[Int] = {
    @tailrec
    def calcPath1(triangle: List[List[Int]], bits: List[List[Int]], bit: Int, indexInRow: Int, res: List[Int]): List[Int] =
      (triangle, bits) match {
        case (Nil, Nil) => res.reverse
        case (row :: rows, b :: bs) =>
          val newIndexInRow = indexInRow + bit
          calcPath1(rows, bs, b(newIndexInRow), newIndexInRow, row(newIndexInRow) :: res)
      }

    calcPath1(triangle.tail, bits.tail, bits.head.head, 0, triangle.head)
  }

  @tailrec
  def calcDynamicsAndBits(reversed: List[List[Int]], dynamics: List[Int], bits: List[List[Int]]): (List[Int], List[List[Int]]) =
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

  def main(args: Array[String]): Unit = {
    var command = ""
    var rows = List[String]()
    while (command != "EOF") {
      command = StdIn.readLine()
      rows :+= command
    }
    parseTriangle(rows.init).map(minPath).foreach { case (sum, path) => println(s"""Minimal path is: ${path.mkString(" + ")} = $sum""")}
  }
}
