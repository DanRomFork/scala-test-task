import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import play.api.libs.json.{JsValue, Json}
import PremierLeagueChampionship.{teamTournamentResultWithScore}

case class TeamTournamentResult(position: Int, team: String, games: Int, wins: Int, draws: Int, loses: Int, goalsScored: Int, conceded: Int, points: Int) {
}

/**
 * This class holds common methods, such as data IO, or data conversion.
 */
object CommonUtils {
  /**
   * This method converts a list of teamTournamentResultWithScore into a JsValue.
   */
  def getTournamentResultsAsJson(tournamentResults: List[teamTournamentResultWithScore]): JsValue ={
    val listOfObjects = tournamentResults
      .zipWithIndex
      .map{case(x, index) => TeamTournamentResult(index + 1, x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8)}

    implicit val tjs = Json.format[TeamTournamentResult]

    Json.toJson(listOfObjects)
  }

  /**
   * This method saves a JsValue into a file in the root directory.
   */
  def saveJSONToFile(json: JsValue, fileName: String): Unit ={
    Files.write(Paths.get(fileName), json.toString().getBytes(StandardCharsets.UTF_8))
  }
}
