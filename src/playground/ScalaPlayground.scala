package chuset
package playground

object ScalaPlayground extends App {

  class Complex(val real: Double, val imaginary: Double) {
    def +(complex: Complex) = new Complex(real + complex.real, imaginary + complex.imaginary)

    override def toString: String = s"$real + ${imaginary}i"
  }

  val complex = new Complex(5.3, 2)
  val complex2 = new Complex(3.4, -1)
  println(complex + complex2)
}
