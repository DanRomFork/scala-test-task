package com.onair.scrapper

import com.onair.scrapper.Domain._


case class MatchResult(Team1Name: String, Team2Name: String, Team1Score: Int, Team2Score: Int)

/**
 * This class holds methods, specific for the Premier League, such as tournament result calculation.
 */
object PremierLeagueChampionship {
  /**
   * This function converts a List[(String, String, Int, Int)] (Team1Name, Team2Name, Team1Score, Team2Score)
   * to a list of teamTournamentResult.
   */
  def getTournamentResults(matchResults: List[MatchResult]): List[TeamTournamentResult] = {
    case class MatchOutcome(
                             numberOfWins: Int,
                             numberOfDraws: Int,
                             numberOfLoses: Int
                           )

    def getMatchOutcome(team1Score: Int, team2Score: Int): MatchOutcome = {
      (team1Score, team2Score) match {
        case (x, y) if x > y => MatchOutcome(1, 0, 0)
        case (x, y) if x < y => MatchOutcome(0, 0, 1)
        case _ => MatchOutcome(0, 1, 0)
      }
    }

    /*
     First, let's take all matches' outcomes, split them in two (by team), and for each team determine,
     1. was it a win, a lose, or a draw.
     2. number of goals scored and conceded.
     */
    val teamsList: List[TeamTournamentResult] = matchResults.map(matchInfo => {
      val teamMatchOutcome = getMatchOutcome(matchInfo.Team1Score, matchInfo.Team2Score)
      TeamTournamentResult(
        matchInfo.Team1Name,
        1,
        teamMatchOutcome.numberOfWins,
        teamMatchOutcome.numberOfDraws,
        teamMatchOutcome.numberOfLoses,
        matchInfo.Team1Score,
        matchInfo.Team2Score)
    }) ++
      matchResults.map(matchInfo => {
        val teamMatchOutcome = getMatchOutcome(matchInfo.Team2Score, matchInfo.Team1Score)
        TeamTournamentResult(
          matchInfo.Team2Name,
          1,
          teamMatchOutcome.numberOfWins,
          teamMatchOutcome.numberOfDraws,
          teamMatchOutcome.numberOfLoses,
          matchInfo.Team2Score,
          matchInfo.Team1Score)
      })

    /*
      Then, let's group these values by a team.
      As a result, we'll get a list of teamTournamentResult.
      It has the following format:
      [(TeamName, NumberOfGames, NumberOfWins, NumberOfDraws, NumberOfLoses, TotalScoredGoals, TotalConcededGoals)]
    */
    val tournamentResults: List[TeamTournamentResult] = teamsList
      .groupBy(_.teamName)
      .map {
        case (teamName, teamTournamentResults) =>
          teamTournamentResults.foldLeft(TeamTournamentResult.Empty) {
            case (result1, item) =>
              TeamTournamentResult(
                item.teamName,
                result1.numberOfGames + item.numberOfGames,
                result1.numberOfWins + item.numberOfWins,
                result1.numberOfDraws + item.numberOfDraws,
                result1.numberOfLoses + item.numberOfLoses,
                result1.scoredGoals + item.scoredGoals,
                result1.concededGoals + item.concededGoals,
              )
          }
      }
      .toList

    tournamentResults
  }

  /**
   * This function converts a list of teamTournamentResult into a list of teamTournamentResultWithScore
   * by calculating a tournament score for each team.
   */
  def getTournamentResultsWithScore(teamTournamentResult: List[TeamTournamentResult]): List[TeamTournamentResultWithScore] = {
    // According to the Premier League rules, each win gets you 3 points, and each draw gets you one.
    def calculateTournamentPoints(winsAmount: Int, drawsAmount: Int): Int = {
      winsAmount * 3 + drawsAmount
    }

    teamTournamentResult.map(x => TeamTournamentResultWithScore(
      teamTournamentResult = x,
      tournamentScore = calculateTournamentPoints(x.numberOfWins, x.numberOfDraws)))
  }

  /**
   * This function takes a list of teamTournamentResultWithScore and returns
   * a sorted list of teamTournamentResultWithScore.
   * It is sorted based on Premier Leagues' rules.
   */
  def getSortedTournamentResults(tournamentResults: List[TeamTournamentResultWithScore]): List[TeamTournamentResultWithScore] = {
    def sortFunction(item1: TeamTournamentResultWithScore, item2: TeamTournamentResultWithScore): Boolean = {
      (item1, item2) match {
        // If tournament points are not the same, we can use them to rank teams.
        case (x, y) if x.tournamentScore != y.tournamentScore => x.tournamentScore > y.tournamentScore
        // Otherwise, if goals' differences are not the same, we can use them.
        case (x, y) if x.goalDifference != y.goalDifference => x.goalDifference > y.goalDifference
        // If goals' differences are also the same, we should use a higher number of goals scored.
        case (x, y) if x.scoredGoals != y.scoredGoals => x.scoredGoals > y.scoredGoals
        // If they are also the same, there should be play-offs.
        // As a temporary measure let's use the number of matches played for now.
        case (x, y) if x.numberOfGames != y.numberOfGames => x.numberOfGames < y.numberOfGames
        // If even is that the same, let's just sort them alphabetically.
        case _ => item1.teamName < item2.teamName
      }
    }

    tournamentResults.sortWith(sortFunction)
  }
}
