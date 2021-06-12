package chuset
package lectures.part2oop

object AbstractDataTypes extends App {

  // abstract
  abstract class Animal {
    val creatureType: String = "Wild"
    def eat(): Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"

    override def eat(): Unit = println("Crunch crunch")
  }

  // traits
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "Fresh meat"
  }

  class Crocodile extends Animal with Carnivore {
    override val creatureType: String = "Crocodile"

    override def eat(): Unit = println("nom nom nom")

    override def eat(animal: Animal): Unit =
      println(s"I'm a crocodile and I'm eating a ${animal.creatureType.toLowerCase()}")
  }

  val dog = new Dog
  val crocodile = new Crocodile
  crocodile.eat(dog)

  // traits vs abstract classes
  // 1 - traits do not have constructor parameters
  // 2 - You can only extend one class but you can inherit multiple traits
  // 3 - traits = behaviour, abstract class = "thing"
}
