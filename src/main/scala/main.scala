import PremierLeagueChampionship._
import CommonUtils._

/*
* The purpose of this program is to:
* 1. Scrape results of Premier League matches from: https://www.betexplorer.com/soccer/england/premier-league/results/
* 2. Calculate ranked standings based on matches data.
*   https://www.betexplorer.com/soccer/england/premier-league/ might be used as a reference.
* 3. Save these standings to a file in the JSON format.
*/

object main extends App {
  val betExplorerMatchTableURL = "https://www.betexplorer.com/soccer/england/premier-league/results/"
  // Getting data from the betexplorer.com.
  val webSiteData = HTTPComm.getWebSiteData(betExplorerMatchTableURL)
  // Parsing the data; getting only the match results.
  val matchesOutcomes = BetExplorerDataParser.parsePremierLeagueResults(webSiteData)
  // Calculating tournament points for given matches' outcomes.
  val tournamentResults = getSortedTournamentResults(getTournamentResultsWithScore(getTournamentResults(matchesOutcomes)))

  // Sorting tournament results according to the Premier League rules and saving them to a file in JSON format.
  val json = getTournamentResultsAsJson(getSortedTournamentResults(tournamentResults))
  CommonUtils.saveJSONToFile(json, "file.json")

  println(s"JSON array :${json.toString()}")

  println("Done")
}
