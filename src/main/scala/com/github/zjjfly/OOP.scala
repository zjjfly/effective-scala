package com.github.zjjfly

import java.nio.file.{Files, Paths}

object OOP extends App {
  //不要在正常情况下使用结构类型,因为它的效率不是很高,但它对反射提供了很好的简写形式
  val obj: AnyRef = Files.newBufferedReader(Paths.get("build.sbt"))
  obj.asInstanceOf[{ def close() }].close()
}

//Scala使用trait和cake pattern实现依赖注入
trait TweetStream {
  def subscribe(f: Tweet => Unit)
}

class HosebirdStream extends TweetStream {
  override def subscribe(f: Tweet => Unit): Unit = ???
}

class FileStream extends TweetStream {
  override def subscribe(f: Tweet => Unit): Unit = ???
}

class TweetCounter(stream: TweetStream) {
  //private[this]限制了它只对当前特定的实例可见
  //Scala编译器会把private[this]翻译为一个简单的字段访问(因为访问仅限于静态定义的 类)，这样有时有助于性能优化。
  private[this] var count: Long = 0
  stream.subscribe { tweet =>
    count += 1
  }
}

//尽量保持trait简单并且正交,不要把单独的功能混在一个trait里
//trait IOer {
//  def write(bytes: Array[Byte])
//  def read(n: Int): Array[Byte]
//}
trait Reader {
  def read(n: Int): Array[Byte]
}
trait Writer {
  def write(bytes: Array[Byte])
}
