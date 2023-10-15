package com.github.dmytromitin

import scala.io.Source
import cats.effect.{IO, Resource}
import MinTrianglePath.{minPath, parseTriangle}
import munit.CatsEffectSuite

class FileMinTrianglePathTest extends CatsEffectSuite {
  private def testFileTriangle(fileName: String, expected: (Int, List[Int])): IO[Unit] =
    Resource
      .fromAutoCloseable(IO.delay(Source.fromResource(fileName)))
      .use { bs =>
        for {
          lines    <- IO.delay(bs.getLines().toList)
          triangle <- IO.fromTry(parseTriangle(lines))
          obtained = minPath(triangle)
        } yield assertEquals(obtained, expected)
      }

  test("test small triangle") {
    testFileTriangle("data_small.txt", (50, List.fill(50)(1)))
  }

  test("test big triangle") {
    testFileTriangle("data_big.txt", (2000, List.fill(2000)(1)))
  }
}
