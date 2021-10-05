package com.onair.scrapper.specs

import com.onair.scrapper.Domain._
import com.onair.scrapper.PremierLeagueChampionship._
import com.onair.scrapper.{BetExplorerDataParser, MatchResult}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.io.Source

class PremierLeagueChampionshipSpec extends AnyFunSuite {
  def getBetExplorerSiteMatchesContent:String = {
    Source.fromResource("resources/matches - betexplorer.com.html").getLines().mkString
  }

  test("com.onair.scrapper.PremierLeagueChampionship.getTournamentResults") {
    // Arrange
    val matchesResults: List[MatchResult] = BetExplorerDataParser.parseBetExplorerData(getBetExplorerSiteMatchesContent)

    // Act
    val tournamentResults = getTournamentResults(matchesResults)

    // Assert
    assert(tournamentResults.length === 20)

    val expectedTournamentResults: List[TeamTournamentResult] = List(
      TeamTournamentResult(teamName = "Crystal Palace",numberOfGames = 1,numberOfWins = 0,numberOfDraws = 0,numberOfLoses = 1,scoredGoals = 0,concededGoals = 3),
        TeamTournamentResult(teamName = "Brentford",numberOfGames = 1,numberOfWins = 1,numberOfDraws = 0,numberOfLoses = 0,scoredGoals = 2,concededGoals = 0),
        TeamTournamentResult(teamName = "Newcastle",numberOfGames = 1,numberOfWins = 0,numberOfDraws = 0,numberOfLoses = 1,scoredGoals = 2,concededGoals = 4),
        TeamTournamentResult(teamName = "Arsenal",numberOfGames = 1,numberOfWins = 0,numberOfDraws = 0,numberOfLoses = 1,scoredGoals = 0,concededGoals = 2),
        TeamTournamentResult(teamName = "Liverpool",numberOfGames = 1,numberOfWins = 1,numberOfDraws = 0,numberOfLoses = 0,scoredGoals = 3,concededGoals = 0),
        TeamTournamentResult(teamName = "Burnley",numberOfGames = 1,numberOfWins = 0,numberOfDraws = 0,numberOfLoses = 1,scoredGoals = 1,concededGoals = 2),
        TeamTournamentResult(teamName = "Leeds",numberOfGames = 1,numberOfWins = 0,numberOfDraws = 0,numberOfLoses = 1,scoredGoals = 1,concededGoals = 5),
        TeamTournamentResult(teamName = "Manchester Utd",numberOfGames = 1,numberOfWins = 1,numberOfDraws = 0,numberOfLoses = 0,scoredGoals = 5,concededGoals = 1),
        TeamTournamentResult(teamName = "Aston Villa",numberOfGames = 1,numberOfWins = 0,numberOfDraws = 0,numberOfLoses = 1,scoredGoals = 2,concededGoals = 3),
        TeamTournamentResult(teamName = "West Ham",numberOfGames = 1,numberOfWins = 1,numberOfDraws = 0,numberOfLoses = 0,scoredGoals = 4,concededGoals = 2),
        TeamTournamentResult(teamName = "Tottenham",numberOfGames = 1,numberOfWins = 1,numberOfDraws = 0,numberOfLoses = 0,scoredGoals = 1,concededGoals = 0),
        TeamTournamentResult(teamName = "Norwich",numberOfGames = 1,numberOfWins = 0,numberOfDraws = 0,numberOfLoses = 1,scoredGoals = 0,concededGoals = 3),
        TeamTournamentResult(teamName = "Southampton",numberOfGames = 1,numberOfWins = 0,numberOfDraws = 0,numberOfLoses = 1,scoredGoals = 1,concededGoals = 3),
        TeamTournamentResult(teamName = "Everton",numberOfGames = 1,numberOfWins = 1,numberOfDraws = 0,numberOfLoses = 0,scoredGoals = 3,concededGoals = 1),
        TeamTournamentResult(teamName = "Chelsea",numberOfGames = 1,numberOfWins = 1,numberOfDraws = 0,numberOfLoses = 0,scoredGoals = 3,concededGoals = 0),
        TeamTournamentResult(teamName = "Watford",numberOfGames = 1,numberOfWins = 1,numberOfDraws = 0,numberOfLoses = 0,scoredGoals = 3,concededGoals = 2),
        TeamTournamentResult(teamName = "Leicester",numberOfGames = 1,numberOfWins = 1,numberOfDraws = 0,numberOfLoses = 0,scoredGoals = 1,concededGoals = 0),
        TeamTournamentResult(teamName = "Manchester City",numberOfGames = 1,numberOfWins = 0,numberOfDraws = 0,numberOfLoses = 1,scoredGoals = 0,concededGoals = 1),
        TeamTournamentResult(teamName = "Brighton",numberOfGames = 1,numberOfWins = 1,numberOfDraws = 0,numberOfLoses = 0,scoredGoals = 2,concededGoals = 1),
        TeamTournamentResult(teamName = "Wolves",numberOfGames = 1,numberOfWins = 0,numberOfDraws = 0,numberOfLoses = 1,scoredGoals = 0,concededGoals = 1)
    )

    expectedTournamentResults shouldEqual tournamentResults
  }

  test("com.onair.scrapper.PremierLeagueChampionship.getTournamentResultsWithScore") {
    // Arrange
    val tournamentResults = getTournamentResults(
      BetExplorerDataParser.parseBetExplorerData(getBetExplorerSiteMatchesContent))

    // Act
    val tournamentResultsWithScore = getTournamentResultsWithScore(tournamentResults)

    // Assert
    assert(tournamentResultsWithScore.length === 20)

    val expectedTournamentResultsWithScore: List[TeamTournamentResultWithScore] = List(
      TeamTournamentResultWithScore(teamName = "Crystal Palace", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 0, concededGoals = 3, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Brentford", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 2, concededGoals = 0, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Newcastle", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 2, concededGoals = 4, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Arsenal", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 0, concededGoals = 2, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Liverpool", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 3, concededGoals = 0, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Burnley", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 1, concededGoals = 2, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Leeds", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 1, concededGoals = 5, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Manchester Utd", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 5, concededGoals = 1, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Aston Villa", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 2, concededGoals = 3, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "West Ham", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 4, concededGoals = 2, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Tottenham", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 1, concededGoals = 0, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Norwich", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 0, concededGoals = 3, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Southampton", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 1, concededGoals = 3, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Everton", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 3, concededGoals = 1, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Chelsea", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 3, concededGoals = 0, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Watford", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 3, concededGoals = 2, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Leicester", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 1, concededGoals = 0, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Manchester City", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 0, concededGoals = 1, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Brighton", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 2, concededGoals = 1, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Wolves", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 0, concededGoals = 1, tournamentScore = 0)
    )

    expectedTournamentResultsWithScore shouldEqual tournamentResultsWithScore
  }

  test("com.onair.scrapper.PremierLeagueChampionship.getSortedTournamentResults") {
    // Arrange
    val tournamentResultsWithScore = getTournamentResultsWithScore(getTournamentResults(
      BetExplorerDataParser.parseBetExplorerData(getBetExplorerSiteMatchesContent)))

    // Act
    val sortedTournamentResults = getSortedTournamentResults(tournamentResultsWithScore)

    // Assert
    assert(sortedTournamentResults.length === 20)

    val expectedSortedTournamentResults: List[TeamTournamentResultWithScore] = List(
      TeamTournamentResultWithScore(teamName = "Manchester Utd", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 5, concededGoals = 1, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Chelsea", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 3, concededGoals = 0, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Liverpool", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 3, concededGoals = 0, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "West Ham", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 4, concededGoals = 2, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Everton", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 3, concededGoals = 1, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Brentford", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 2, concededGoals = 0, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Watford", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 3, concededGoals = 2, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Brighton", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 2, concededGoals = 1, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Leicester", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 1, concededGoals = 0, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Tottenham", numberOfGames = 1, numberOfWins = 1, numberOfDraws = 0, numberOfLoses = 0, scoredGoals = 1, concededGoals = 0, tournamentScore = 3),
      TeamTournamentResultWithScore(teamName = "Aston Villa", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 2, concededGoals = 3, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Burnley", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 1, concededGoals = 2, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Manchester City", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 0, concededGoals = 1, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Wolves", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 0, concededGoals = 1, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Newcastle", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 2, concededGoals = 4, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Southampton", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 1, concededGoals = 3, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Arsenal", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 0, concededGoals = 2, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Crystal Palace", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 0, concededGoals = 3, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Norwich", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 0, concededGoals = 3, tournamentScore = 0),
      TeamTournamentResultWithScore(teamName = "Leeds", numberOfGames = 1, numberOfWins = 0, numberOfDraws = 0, numberOfLoses = 1, scoredGoals = 1, concededGoals = 5, tournamentScore = 0)
    )

    expectedSortedTournamentResults shouldEqual sortedTournamentResults
  }
}