package com.github.dmytromitin

import MinTrianglePath.minPath

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
}