package com.github.zjjfly

import java.net.InetSocketAddress
import java.util.Date
import java.util.logging.Level

object Functional extends App {
  def findMin[T <: Ordered[T]](tree: Tree[T]): T = tree match {
    //case class让使用模式匹配更容易
    case Node(left, right) => Seq(findMin(left), findMin(right)).min
    case Leaf(value)       => value
  }

  //尽量多使用Option.它相当于有0个或1个元素的集合,支持集合相关操作
  var userName: Option[String] = None
  userName = Some("jjzi")
  //需要对有值的Option做操作,而没有值不做任何处理,使用forEach
  userName.foreach(println)
  //如果有值和无值的情况都要做一些处理,使用模式匹配
  userName match {
    //模式匹配和解构一起使用最好
    case Some(value) => println(value)
    case None        => println("There is no value")
  }
  //如果想要提供缺省值,使用getOrElse
  userName.getOrElse("jj")
  //Option有个方法的构造器,传入的是空值则构造的是None,有值则是Some
  val option = Option(null)
  println(option)

  val list = List(1, 2)
  //当默认方法更有意义时,不要使用模式匹配
  //  list match {
//    case head :: _ => head
//    case Nil       => 0
//  }
  list.headOption getOrElse 0

  //偏函数主要使用在作为方法参数
  trait Publisher[T] {
    def subscribe(f: PartialFunction[T, Unit])
  }
  //或者代替Option作为函数返回值,因为它
  type Classifier = PartialFunction[Throwable, java.util.logging.Level]
  val classifier1: Classifier = {
    case _: NullPointerException => Level.WARNING
  }
  val classifier2: Classifier = {
    case _: IndexOutOfBoundsException => Level.WARNING
  }
  val classifier: Classifier = classifier1 orElse classifier2 orElse {
    case _ => Level.FINEST
  }

  //解构绑定,特别适合元组和样本类
  val tuple = ("a", 1)
  val (char, num) = tuple
  val tweet = Tweet("just tweeting", new Date())
  val Tweet(text, timestamp) = tweet

  def computation() = {
    Thread.sleep(100)
    1
  }

  //lazy,在需要的时候才会赋值,而且只计算一次
  lazy val field: Int = computation()

  //flatMap
  val chars = 'a' to 'z'
  val perms = chars flatMap { a =>
    chars flatMap { b =>
      if (a != b) Seq("%c%c".format(a, b))
      else Seq()
    }
  }
  println(perms)
  //等价于下面的代码
  val perms1 = for (a <- chars;
                    b <- chars
                    if a != b) yield "%c%c".format(a, b)
  assert(perms==perms1)
  //flatMap在处理Options常常很有用,它将多个options链合并为一个
  val host: Option[String] = Some("1.1.1.1")
  val port: Option[Int] = Some(80)
  val addr: Option[InetSocketAddress] = host flatMap { h =>
    port map { p =>
      new InetSocketAddress(h, p)
    }
  }
  println(addr)
}
case class Tweet(str: String, time: Date)
//使用case class很容易实现代数数据类型
sealed trait Tree[T]
case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T]
case class Leaf[T](value: T) extends Tree[T]

