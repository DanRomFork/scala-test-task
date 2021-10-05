package com.onair.scrapper

//import com.onair.scrapper.PremierLeagueChampionship.TeamTournamentResultWithScore
import play.api.libs.json.{JsValue, Json}

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import Domain._

/**
 * This class holds common methods, such as data IO, or data conversion.
 */
object CommonUtils {
  /**
   * This method converts a list of teamTournamentResultWithScore into a JsValue.
   */
  def getTournamentResultsAsJson(tournamentResults: List[TeamTournamentResultWithScore]): JsValue = {
    val listOfObjects = tournamentResults
      .zipWithIndex
      .map { case (x, index) => TeamTournamentResultJSON(position = index + 1, teamTournamentResult = x)
      }

    implicit val tjs = Json.format[TeamTournamentResultJSON]

    Json.toJson(listOfObjects)
  }

  /**
   * This method saves a JsValue into a file in the root directory.
   */
  def saveJSONToFile(json: JsValue, fileName: String): Unit = {
    Files.write(Paths.get(fileName), json.toString().getBytes(StandardCharsets.UTF_8))
  }
}
