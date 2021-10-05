package com.onair.scrapper

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

/**
 * The sole purpose of class is to send a HTTP request and get a website content.
 */
object HTTPComm {
  implicit val system: ActorSystem = ActorSystem()

  import system.dispatcher

  def sendRequest(URL: String): Future[String] = {
    val request = HttpRequest(
      method = HttpMethods.GET,
      uri = URL
    )

    for {
      response <- Http().singleRequest(request)
      entity <- response.entity.toStrict(2.seconds)
    } yield entity.data.utf8String
  }

  /**
   * Synchronously returns the content for the given URL.
   */
  def getWebSiteData(URL: String): String = {
    Await.result(sendRequest(URL), 5.seconds)
  }
}
