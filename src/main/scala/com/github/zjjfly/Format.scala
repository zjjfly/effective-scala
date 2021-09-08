package com.github.zjjfly

import scala.collection.mutable

/**
  * @author zjjfly[https://github.com/zjjfly] on 2020/3/14
  */
object Format extends App {
    //避免用``声明和保留字同名的变量
    val `type` = "type"
    val format: Format = Format()
    println(format)
    //一个类引入了mutable包的时候,使用集合的时候要加mutable.或者immutable.
    val ints = mutable.MutableList(1, 2)
    ints += 3
    println(ints)
    //尽可能直接使用模式匹配
    val list = (1 to 10).map {
      case a: Int if a >= 10 && a < 100 => 10
      case _: Int                       => 0
    }
    println(list)

  /**
    * 不必重复已经被package或object封装过的名字,所以直接用get而不是getFormat
    * @return
    */
  def get(): Format = {
    Format()
  }
  case class Format(var active: Boolean = false) {
    //用主动语法来命名有副作用的方法更好
    def activeIt() {
      println("Active!")
      this.active = true
    }

    //对有返回值的方法使用有描述性的名字
    def isActive(): Boolean = this.active
  }
}
