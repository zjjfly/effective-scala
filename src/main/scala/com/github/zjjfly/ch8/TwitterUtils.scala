package com.github.zjjfly.ch8

import com.twitter.util.Future

object TwitterUtils extends App {
  val result: Future[Int] = Future {
    Thread.sleep(100)
    1
  }
  val resultStr: Future[String] = result.map(i => i.toString)
}
trait RPC {
  def getUser(id: Int): Future[User]
  def authenticate(user: User): Future[Boolean]
  //使用flatMap我们可以定义一个 Future 作为 两个Future序列的结果。第二个future 的计算基于第一个的结果
  def isIdAuthed(id: Int): Future[Boolean] = getUser(id) flatMap { user =>
    authenticate(user)
  }
}
case class User(id: Int)
