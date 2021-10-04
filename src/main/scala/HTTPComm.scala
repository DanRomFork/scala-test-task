import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.Await

/**
* The sole purpose of class is to send a HTTP request and get a website content.
*/
object HTTPComm {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  import system.dispatcher

  def sendRequest(URL: String): Future[String] = {
    val request = HttpRequest (
      method = HttpMethods.GET,
      uri = URL
    )

    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
    val entityFuture: Future[HttpEntity.Strict] = responseFuture.flatMap(response => response.entity.toStrict(2.seconds))
    entityFuture.map(entity => entity.data.utf8String)
  }

  /**
   * Synchronously returns the content for the given URL.
   */
  def getWebSiteData(URL: String): String = {
    Await.result(sendRequest(URL), 5.seconds)
  }
}