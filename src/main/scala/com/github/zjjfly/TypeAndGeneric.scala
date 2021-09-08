package com.github.zjjfly

object TypeAndGeneric extends App {
  val service: Service = make()

  //尽管Scala允许方法的返回值类型是可以忽略的,但是最佳的实践还是加上它们,尤其是使用mixin来实例化对象的时候
  def make(): Service = new Service {
    def getId = 123
  }

  val dogs: HashSet[Dog] = new HashSet[Dog] {
    override def add[U >: Dog](item: U): Unit = {}
  }
  val mammal: HashSet[Mammal] = dogs
  dogs.add(new Mammal {})
  mammal.add(new Cat {}) //现在HashSet[Dog]可以放Cat类型的值,显然不对

  //可以自解释的类型不需要类型别名,下面这样的做法就是多余的
  type IntMaker = () => Int
}
trait Service
//可变集合的元素类型是不变的,下面的是反例
trait HashSet[+T] {
  def add[U >: T](item: U)
}
trait Mammal
trait Dog extends Mammal
trait Cat extends Mammal
