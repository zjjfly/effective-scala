package com.github.zjjfly

import java.util

import scala.collection.mutable

/**
  * @author zjjfly[https://github.com/zjjfly] on 2020/3/14
  */
object Collection extends App {
  //在自定义的方法和构造函数中,总是使用最宽泛的集合类型,通常是Iterable,Seq,Set和Map中的一个
  val iterable = Iterable(1, 2)
  val seq = Seq(1, 2, 3)
  val set = Set(1, 2, 3)
  val map = Map(1 -> "one", 2 -> "two", 3 -> "three")

  //流水线操作可以使代码更简单,但会让读者很难理解代码的意图
  val votes =
    Seq(("scala", 1), ("java", 4), ("scala", 10), ("scala", 1), ("python", 10))
  val voteByLang = votes.groupBy { case (lang, _) => lang }
  val sumByLang = voteByLang.map {
    case (lang, votes) => {
      val counts = votes.map { case (_, count) => count }
      (lang, counts.sum)
    }
  }
  val orderedVotes = sumByLang.toSeq.sortBy { case (_, count) => count }.reverse
  println(orderedVotes)
  //scala.collection.JavaConverters提供了Scala集合到Java集合的互相转换的方法
  //但是我们使用的时候最好显示调用asJava和asScala方法
  import scala.collection.JavaConverters._
  val list: util.List[Int] = Seq(1, 2).asJava
  val buffer: mutable.Seq[Int] = list.asScala

}
