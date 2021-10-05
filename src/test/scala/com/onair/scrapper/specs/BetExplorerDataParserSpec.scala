package com.onair.scrapper.specs

import com.onair.scrapper.{BetExplorerDataParser, MatchResult}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.io.Source

class BetExplorerDataParserSpec extends AnyFunSuite {
  test("BetExplorerDataParser.parseBetExplorerData") {
    // Arrange
    val betExplorerSiteMatchesContent =
      Source.fromResource("resources/matches - betexplorer.com.html").getLines().mkString

    // Act
    val matchesResults: List[MatchResult] = BetExplorerDataParser.parseBetExplorerData(betExplorerSiteMatchesContent)

    // Assert
    assert(matchesResults.length === 10)
    // We are using matches result from the Premier League 2021 first round, so we can actually assert matches results.
    val expectedMatchesResults: List[MatchResult] = List(
      MatchResult("Newcastle", "West Ham", 2, 4),
      MatchResult("Tottenham", "Manchester City", 1, 0),
      MatchResult("Burnley", "Brighton", 1, 2),
      MatchResult("Chelsea", "Crystal Palace", 3, 0),
      MatchResult("Everton", "Southampton", 3, 1),
      MatchResult("Leicester", "Wolves", 1, 0),
      MatchResult("Manchester Utd", "Leeds", 5, 1),
      MatchResult("Norwich", "Liverpool", 0, 3),
      MatchResult("Watford", "Aston Villa", 3, 2),
      MatchResult("Brentford", "Arsenal", 2, 0)
    )

    expectedMatchesResults shouldEqual matchesResults
  }
}
