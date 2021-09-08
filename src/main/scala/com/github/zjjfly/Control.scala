package com.github.zjjfly

/**
  * @author zjjfly[https://github.com/zjjfly] on 2020/3/14
  */
object Control extends App {
  //使用return可以用于切断分支和建立不变量,但用在频繁执行的代码中,会有性能影响
  def compare(a: AnyRef, b: AnyRef): Int = {
    if (a eq b)
      return 0
    val d = System.identityHashCode(a) compare System.identityHashCode(b)
    if (d != 0)
      return d
    else
      return -1
  }
  //for循环在扁平化很多序列时特别有用。
  val tuples: IndexedSeq[(Int, Int)] =
    for (i <- 1 to 10; j <- 1 to 5) yield (i, j)
  println(tuples)

  //要求(require)和断言(assert)都起到可执行文档的作用.assert用于代码假设的不变量,require用于表达API契约
  val stream = getClass.getResourceAsStream("build.sbt")
  assert(stream == null)
  def fib(n: Int): Int = {
    require(n > 0)
    n match {
      case x if x < 3 => 1
      case x          => fib(x - 1) + fib(x - 2)
    }
  }

}
