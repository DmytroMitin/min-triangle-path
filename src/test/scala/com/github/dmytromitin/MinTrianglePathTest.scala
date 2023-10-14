package com.github.dmytromitin

import MinTrianglePath.{minPath, parseTriangle}

import scala.io.Source
import scala.util.{Success, Try}

class MinTrianglePathTest extends munit.FunSuite {
  test("min triangle path") {
    val obtained = minPath(List(
      List(7),
      List(6, 3),
      List(3, 8, 5),
      List(11, 2, 10, 9),
    ))
    val expected = (18,List(7, 6, 3, 2))
    assertEquals(obtained, expected)
  }

  private def fileTriangleMinPath(fileName: String): Try[(Int, List[Int])] = {
    val rows = Source.fromResource(fileName).getLines.toList
    parseTriangle(rows).map(minPath)
  }

  test("min triangle path for data_small.txt") {
    val obtained = fileTriangleMinPath("data_small.txt")
    val expected = Success((50, List.fill(50)(1)))
    assertEquals(obtained, expected)
  }

  test("min triangle path for data_big.txt") {
    val obtained = fileTriangleMinPath("data_big.txt")
    val expected = Success((2000, List.fill(2000)(1)))
    assertEquals(obtained, expected)
  }
}