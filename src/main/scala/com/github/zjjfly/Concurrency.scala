package com.github.zjjfly

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Concurrency extends App {
  import scala.concurrent.ExecutionContext.Implicits.global
  def doRpc(): Future[Result] = {
    Thread.sleep(100)
    Future[Result](Result("Success"))
  }
  def collect(results: List[Result] = Nil): Future[List[Result]] =
    //使用flatMap,把结果预先追加到list中,这样写的代码更少,而且不容易出错
    doRpc() flatMap { result =>
      if (results.lengthCompare(9) >= 0) Future[List[Result]] {
        result :: results
      } else
        collect { result :: results }
    }

  collect() onComplete {
    case Success(results) =>
      printf("Got results %s\n", results.mkString(","))
    case Failure(e) => printf("Got error %s\n", e.getMessage)
  }
  Thread.sleep(1000)
}
case class Result(result: String)
