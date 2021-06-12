package chuset
package exercises

abstract class MyList[+A] {

  /*
    head = first element of the list
    tail = remainder of the list (pointer)
    isEmpty = is this list empty
    add(Int) => new list with this element added
    toString => a string representation of the list
   */

  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def add[B >: A](element: B): MyList[B]

  // Polymorphic call
  def printElements: String

  override def toString: String = s"[$printElements]"

  // higher-order functions
  def map[B](transformer: A => B): MyList[B]

  def flatMap[B](transformer: A => MyList[B]): MyList[B]

  def filter(predicate: A => Boolean): MyList[A]

  // concatenation
  def ++[B >: A](list: MyList[B]): MyList[B]

  // HOFS
  def foreach(f: A => Unit): Unit

  def sort(compare: (A, A) => Int): MyList[A]

  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]

  def fold[B](start: B)(operator: (B, A) => B): B
}

case object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](element: B): MyList[B] = Cons(element, Empty)

  override def printElements: String = ""

  override def map[B](transformer: Nothing => B): MyList[B] = Empty

  override def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty

  override def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  // HOFS
  override def foreach(f: Nothing => Unit): Unit = ()

  override def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = Empty

  override def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
    if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Empty

  override def fold[B](start: B)(operator: (B, Nothing) => B): B = start
}

final case class Cons[+A](h: A, t: MyList[A] = Empty) extends MyList[A] {
  override def head: A = h

  override def tail: MyList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](element: B): MyList[B] = Cons(element, this)

  override def printElements: String =
    if (t.isEmpty) h.toString
    else s"$h, ${t.printElements}"

  /*
    [1, 2, 3].filter(n % 2 == 0)
    = [2, 3].filter(n % 2 == 0)
    = new Cons(2, [3].filter(n % 2 == 0))
    = new Cons(2, new Cons(Empty.filter(n % 2 == 0))
    = new Cons(2, Empty)
   */
  override def filter(predicate: A => Boolean): MyList[A] =
    if (predicate(h)) Cons(h, t.filter(predicate))
    else t.filter(predicate)

  /*
    [1, 2, 3].map(n * 2)
      = new Cons(2, [2, 3].map(n * 2))
      = new Cons(2, new Cons(4, [3].map(n * 2))
      = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
      = new Cons(2, new Cons(4, new Cons(6))
   */
  override def map[B](transformer: A => B): MyList[B] =
    Cons(transformer(h), t.map(transformer))

  /*
    [1, 2] ++ [3, 4, 5]
    = new Cons(1, [2] ++ [3, 4, 5])
    = new Cons(1, new Cons(2, Empty ++ [3, 4, 5]))
    = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons 5))))
   */
  override def ++[B >: A](list: MyList[B]): MyList[B] = Cons(h, t ++ list)

  /*
      [1, 2].flatMap(n => [n, n + 1])
      = [1, 2] ++ [2].flatMap(n => [n, n + 1])
      = [1, 2] ++ [2, 3] ++ Empty.flatMap(n => [n, n + 1])
      = [1, 2] ++ [2, 3] ++ Empty
      = [1, 2, 2, 3]
     */
  override def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer(h) ++ t.flatMap(transformer)

  // HOFS
  override def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  override def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if (sortedList.isEmpty) Cons(x)
      else if (compare(x, sortedList.head) <= 0) Cons(x, sortedList)
      else Cons(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  override def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] =
    if (list.isEmpty) throw new RuntimeException("List do not have the same length")
    else Cons(zip(h, list.head), t.zipWith(list.tail, zip))

  /*
    [1, 2, 3].fold(0)(+)
    = [2, 3].fold(1)(+)
    = [3].fold(3)(+)
    = [].fold(6)(+)
    = 6
   */
  override def fold[B](start: B)(operator: (B, A) => B): B =
    t.fold(operator(start, h))(operator)
}

object ListTest extends App {
  val listOfIntegers: MyList[Int] = Cons(1, Cons(2, Cons(3)))
  val cloneListOfIntegers: MyList[Int] = Cons(1, Cons(2, Cons(3)))
  val anotherListOfIntegers: MyList[Int] = Cons(4, Cons(5))
  val listOfStrings: MyList[String] = Cons("Hello", Cons("Scala"))

  //  [1, 2, 3]
  println(listOfIntegers)
  //  [Hello, Scala]
  println(listOfStrings)

  //  [2, 4, 6]
  println(listOfIntegers.map(_ * 2))

  //  [2]
  println(listOfIntegers.filter(_ % 2 == 0))

  //  [1, 2, 3, 4, 5]
  println((listOfIntegers ++ anotherListOfIntegers))
  //  [1, 2, 2, 3, 3, 4]
  println(listOfIntegers.flatMap(elem => Cons(elem, Cons(elem + 1, Empty))))

  //  true
  println(cloneListOfIntegers == listOfIntegers)

  /*
      1
      2
      3
   */
  listOfIntegers.foreach(println)
  //  [3, 2, 1]
  println(listOfIntegers.sort((x, y) => y - x))

  //  [4-Hello, 5-Scala]
  println(anotherListOfIntegers.zipWith[String, String](listOfStrings, _ + "-" + _))

  //  6
  println(listOfIntegers.fold(0)(_ + _))

  val combinations = for {
    n <- listOfIntegers
    string <- listOfStrings
  } yield s"$n-$string"
  println(combinations) // [1-Hello, 1-Scala, 2-Hello, 2-Scala, 3-Hello, 3-Scala]
}
