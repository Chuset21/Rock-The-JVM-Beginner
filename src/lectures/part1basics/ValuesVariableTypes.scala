package chuset
package lectures.part1basics

object ValuesVariableTypes extends App {

  val x: Int = 42
  println(x)

  // VAL's ARE IMMUTABLE
  // Compiler can infer types

  val aString: String = "hello"

  val aBoolean: Boolean = false
  val aChar: Char = 'a'
  val anInt: Int = x
  val aShort: Short = 4613
  val aLong: Long = 4782348723748232L
  val aFloat: Float = 2.0f
  val aDouble: Double = 3.14

  // variables
  var aVariable: Int = 4 // best to not use

  aVariable = 5 // side effects
}
