package chuset
package lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App {

  def factorial(n: Int): Int = {
    if (n <= 0) 1
    else {
      println(s"Computing factorial of $n - I first need factorial of " + (n - 1))
      val result = n * factorial(n - 1)
      println("Computed factorial of " + n)

      result
    }
  }

  println(factorial(10))

  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factorialHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else factorialHelper(x - 1, x * accumulator) // TAIL RECURSION = use recursive call as the LAST expression

    factorialHelper(n, 1)
  }

  /*
    anotherFactorial(10) = factorialHelper(10, 1)
    = factorialHelper(9, 10 * 1)
    = factorialHelper(8, 9 * 10 * 1)
    = factorialHelper(7, 8 * 9 * 10 * 1)
    = ...
    = factorialHelper(2, 3 * 4 * ... * 10 * 1)
    = factorialHelper(1, 1 * 2 * ... * 10)
    = 1 * 2 * 3 * 4 * ... * 10
   */

  println(anotherFactorial(20000))

  // WHEN YOU NEED LOOPS, USE TAIL RECURSION.

  /*
    1. Concatenate a String n times
    2. isPrime() tail recursive
    3. Fibonacci function, tail recursive
   */

  def aRepeatedFunction(aString: String, n: Int): String = {
    @tailrec
    def tailRec(string: String, x: Int, accumulator: String): String =
      if (x <= 0) accumulator
      else tailRec(string, x - 1, accumulator + string)

    tailRec(aString, n, "")
  }

  println(aRepeatedFunction("Hello", 3))

  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeUntil(t: Int, isStillPrime: Boolean): Boolean =
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeUntil(t - 1, n % t != 0)

    isPrimeUntil(n / 2, isStillPrime = true)
  }

  println(isPrime(2003))
  println(isPrime(37 * 17))

  def fibonacci(n: Int): Int = {
    @tailrec
    def tailRec(i: Int, last: Int, nextToLast: Int): Int =
      if (i >= n) last
      else tailRec(i + 1, last + nextToLast, last)

    if (n <= 2) 1
    else tailRec(2, 1, 1)
  }

  println(fibonacci(8))
}
