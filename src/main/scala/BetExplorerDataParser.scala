import org.jsoup.Jsoup
import scala.jdk.CollectionConverters.CollectionHasAsScala

/**
 * This class holds parsing routines for the betexplorer.com website.
 */
object BetExplorerDataParser {
  /**
   * This method takes the entire "Premier League results" page and returns a list of matches' results in the following format:
   * [(Team1Name, Team2Name, Team1Score, Team2Score)].
   */
  def parsePremierLeagueResults(inputHTML: String): List[MatchResult] = {
    val document = Jsoup.parse(inputHTML)

    document.select(".table-main tr:has(td)")
      .asScala
      .map(node => {
        val matchScore = node.select("td a").last().text()
        val scoreByTeam = matchScore.split(":").map(_.toInt)
        MatchResult(
          node.select("td a span").first().text(),
          node.select("td a span").last().text(),
          scoreByTeam(0),
          scoreByTeam(1)
        )
      })
      .toList
  }
}