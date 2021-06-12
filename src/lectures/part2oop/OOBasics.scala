package chuset
package lectures.part2oop

object OOBasics extends App {

  val person = new Person("Miguel", 18)
  println(person.age)
  person.greet("Daniel")


}

class Person(name: String, val age: Int = 0) {
  // body
  val x = 2

  println(4)

  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  def greet(): Unit = println(s"Hi, I am $name")

  // multiple constructors
  def this() = this("John Doe")
}

/*
  Novel and a Writer

  Writer: first name, surname, year
    - method fullName

  Novel: name, year of release, author
    - method authorAge (at year of release)
    - isWrittenBy(author)
    - copy(new year of release) = new instance of Novel
 */

class Writer(firstName: String, surname: String, val year: Int) {
  def fullName(): String = s"$firstName $surname"
}

class Novel(name: String, yearOfRelease: Int, author: Writer) {
  def authorAge(): Int = yearOfRelease - author.year

  def isWrittenBy(author: Writer): Boolean = this.author == author

  def copy(newYear: Int): Novel = new Novel(name, newYear, author)
}

/*
  Counter class
    - receives an int value
    - method current count
    - method to increment/decrement => new Counter
    - overload inc/dec to receive an amount
 */

class Counter(val count: Int) {
  def increment() = new Counter(count + 1) // immutability
  def decrement() = new Counter(count - 1)

  def increment(n: Int): Counter = {
    if (n <= 0) this
    else increment().increment(n - 1)
  }

  def decrement(n: Int): Counter = {
    if (n <= 0) this
    else decrement().decrement(n - 1)
  }

  def print(): Unit = println(count)
}

// class parameters are NOT FIELDS