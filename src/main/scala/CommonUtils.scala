import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import play.api.libs.json.{JsValue, Json}
import PremierLeagueChampionship.TeamTournamentResultWithScore

/**
 * This class holds common methods, such as data IO, or data conversion.
 */
object CommonUtils {
  /**
   * This method converts a list of teamTournamentResultWithScore into a JsValue.
   */
  case class TeamTournamentResult(
    position: Int,
    team: String,
    games: Int,
    wins: Int,
    draws: Int,
    loses: Int,
    goalsScored: Int,
    conceded: Int,
    points: Int
  ) {}

  def getTournamentResultsAsJson(tournamentResults: List[TeamTournamentResultWithScore]): JsValue = {
    val listOfObjects = tournamentResults
      .zipWithIndex
      .map{case(x, index) => TeamTournamentResult(
        index + 1,
        x.teamName,
        x.numberOfGames,
        x.numberOfWins,
        x.numberOfDraws,
        x.numberOfLoses,
        x.scoredGoals,
        x.concededGoals,
        x.tournamentScore
      )}

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
