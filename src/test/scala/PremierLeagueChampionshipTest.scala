import PremierLeagueChampionship._
import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class PremierLeagueChampionshipTest extends AnyFunSuite {
  def getBetExplorerSiteMatchesContent:String = {
    Source.fromResource("resources/matches - betexplorer.com.html").getLines().mkString
  }

  test("PremierLeagueChampionship.getTournamentResults") {
    // Arrange
    val matchesResults: List[MatchResult] = BetExplorerDataParser.parseBetExplorerData(getBetExplorerSiteMatchesContent)

    // Act
    val tournamentResults = getTournamentResults(matchesResults)

    // Assert
    assert(tournamentResults.length === 20)

    val expectedTournamentResults: List[TeamTournamentResult] = List(
      TeamTournamentResult("Crystal Palace",1,0,0,1,0,3),
        TeamTournamentResult("Brentford",1,1,0,0,2,0),
        TeamTournamentResult("Newcastle",1,0,0,1,2,4),
        TeamTournamentResult("Arsenal",1,0,0,1,0,2),
        TeamTournamentResult("Liverpool",1,1,0,0,3,0),
        TeamTournamentResult("Burnley",1,0,0,1,1,2),
        TeamTournamentResult("Leeds",1,0,0,1,1,5),
        TeamTournamentResult("Manchester Utd",1,1,0,0,5,1),
        TeamTournamentResult("Aston Villa",1,0,0,1,2,3),
        TeamTournamentResult("West Ham",1,1,0,0,4,2),
        TeamTournamentResult("Tottenham",1,1,0,0,1,0),
        TeamTournamentResult("Norwich",1,0,0,1,0,3),
        TeamTournamentResult("Southampton",1,0,0,1,1,3),
        TeamTournamentResult("Everton",1,1,0,0,3,1),
        TeamTournamentResult("Chelsea",1,1,0,0,3,0),
        TeamTournamentResult("Watford",1,1,0,0,3,2),
        TeamTournamentResult("Leicester",1,1,0,0,1,0),
        TeamTournamentResult("Manchester City",1,0,0,1,0,1),
        TeamTournamentResult("Brighton",1,1,0,0,2,1),
        TeamTournamentResult("Wolves",1,0,0,1,0,1)
    )

    assert(tournamentResults.equals(expectedTournamentResults))
  }

  test("PremierLeagueChampionship.getTournamentResultsWithScore") {
    // Arrange
    val tournamentResults = getTournamentResults(
      BetExplorerDataParser.parseBetExplorerData(getBetExplorerSiteMatchesContent))

    // Act
    val tournamentResultsWithScore = getTournamentResultsWithScore(tournamentResults)

    // Assert
    assert(tournamentResultsWithScore.length === 20)

    val expectedTournamentResultsWithScore: List[TeamTournamentResultWithScore] = List(
      TeamTournamentResultWithScore("Crystal Palace", 1, 0, 0, 1, 0, 3, 0),
      TeamTournamentResultWithScore("Brentford", 1, 1, 0, 0, 2, 0, 3),
      TeamTournamentResultWithScore("Newcastle", 1, 0, 0, 1, 2, 4, 0),
      TeamTournamentResultWithScore("Arsenal", 1, 0, 0, 1, 0, 2, 0),
      TeamTournamentResultWithScore("Liverpool", 1, 1, 0, 0, 3, 0, 3),
      TeamTournamentResultWithScore("Burnley", 1, 0, 0, 1, 1, 2, 0),
      TeamTournamentResultWithScore("Leeds", 1, 0, 0, 1, 1, 5, 0),
      TeamTournamentResultWithScore("Manchester Utd", 1, 1, 0, 0, 5, 1, 3),
      TeamTournamentResultWithScore("Aston Villa", 1, 0, 0, 1, 2, 3, 0),
      TeamTournamentResultWithScore("West Ham", 1, 1, 0, 0, 4, 2, 3),
      TeamTournamentResultWithScore("Tottenham", 1, 1, 0, 0, 1, 0, 3),
      TeamTournamentResultWithScore("Norwich", 1, 0, 0, 1, 0, 3, 0),
      TeamTournamentResultWithScore("Southampton", 1, 0, 0, 1, 1, 3, 0),
      TeamTournamentResultWithScore("Everton", 1, 1, 0, 0, 3, 1, 3),
      TeamTournamentResultWithScore("Chelsea", 1, 1, 0, 0, 3, 0, 3),
      TeamTournamentResultWithScore("Watford", 1, 1, 0, 0, 3, 2, 3),
      TeamTournamentResultWithScore("Leicester", 1, 1, 0, 0, 1, 0, 3),
      TeamTournamentResultWithScore("Manchester City", 1, 0, 0, 1, 0, 1, 0),
      TeamTournamentResultWithScore("Brighton", 1, 1, 0, 0, 2, 1, 3),
      TeamTournamentResultWithScore("Wolves", 1, 0, 0, 1, 0, 1, 0)
    )

    assert(tournamentResultsWithScore.equals(expectedTournamentResultsWithScore))
  }

  test("PremierLeagueChampionship.getSortedTournamentResults") {
    // Arrange
    val tournamentResultsWithScore = getTournamentResultsWithScore(getTournamentResults(
      BetExplorerDataParser.parseBetExplorerData(getBetExplorerSiteMatchesContent)))

    // Act
    val sortedTournamentResults = getSortedTournamentResults(tournamentResultsWithScore)

    // Assert
    assert(sortedTournamentResults.length === 20)

    val expectedSortedTournamentResults: List[TeamTournamentResultWithScore] = List(
      TeamTournamentResultWithScore("Manchester Utd", 1, 1, 0, 0, 5, 1, 3),
      TeamTournamentResultWithScore("Chelsea", 1, 1, 0, 0, 3, 0, 3),
      TeamTournamentResultWithScore("Liverpool", 1, 1, 0, 0, 3, 0, 3),
      TeamTournamentResultWithScore("West Ham", 1, 1, 0, 0, 4, 2, 3),
      TeamTournamentResultWithScore("Everton", 1, 1, 0, 0, 3, 1, 3),
      TeamTournamentResultWithScore("Brentford", 1, 1, 0, 0, 2, 0, 3),
      TeamTournamentResultWithScore("Watford", 1, 1, 0, 0, 3, 2, 3),
      TeamTournamentResultWithScore("Brighton", 1, 1, 0, 0, 2, 1, 3),
      TeamTournamentResultWithScore("Leicester", 1, 1, 0, 0, 1, 0, 3),
      TeamTournamentResultWithScore("Tottenham", 1, 1, 0, 0, 1, 0, 3),
      TeamTournamentResultWithScore("Aston Villa", 1, 0, 0, 1, 2, 3, 0),
      TeamTournamentResultWithScore("Burnley", 1, 0, 0, 1, 1, 2, 0),
      TeamTournamentResultWithScore("Manchester City", 1, 0, 0, 1, 0, 1, 0),
      TeamTournamentResultWithScore("Wolves", 1, 0, 0, 1, 0, 1, 0),
      TeamTournamentResultWithScore("Newcastle", 1, 0, 0, 1, 2, 4, 0),
      TeamTournamentResultWithScore("Southampton", 1, 0, 0, 1, 1, 3, 0),
      TeamTournamentResultWithScore("Arsenal", 1, 0, 0, 1, 0, 2, 0),
      TeamTournamentResultWithScore("Crystal Palace", 1, 0, 0, 1, 0, 3, 0),
      TeamTournamentResultWithScore("Norwich", 1, 0, 0, 1, 0, 3, 0),
      TeamTournamentResultWithScore("Leeds", 1, 0, 0, 1, 1, 5, 0)
    )

    assert(sortedTournamentResults.equals(expectedSortedTournamentResults))
  }
}