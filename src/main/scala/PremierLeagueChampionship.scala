/**
 * This class holds methods, specific for the Premier League, such as tournament result calculation.
 */
object PremierLeagueChampionship {

  type teamTournamentResult = (String, Int, Int, Int, Int, Int, Int)

  /**
   * This function converts a List[(String, String, Int, Int)] (Team1Name, Team2Name, Team1Score, Team2Score)
   * to a list of teamTournamentResult.
   */
  def getTournamentResults(matchResults :List[(String, String, Int, Int)]): List[teamTournamentResult] = {
    def getMatchOutcome(team1Score: Int, team2Score: Int):(Int, Int, Int) = {
      if (team1Score > team2Score) {
        (1, 0, 0)
      } else if (team1Score < team2Score) {
        (0, 0, 1)
      } else {
        (0, 1, 0)
      }
    }

    /*
     First, let's take all matches' outcomes, split them in two (by team), and for each team determine,
     1. was it a win, a lose, or a draw.
     2. number of goals scored and conceded.
     */
    val teamsList: List[teamTournamentResult] = matchResults.map(matchInfo => {
      val matchTeamResults = getMatchOutcome(matchInfo._3, matchInfo._4)
      (matchInfo._1, 1, matchTeamResults._1, matchTeamResults._2, matchTeamResults._3, matchInfo._3, matchInfo._4)
    }) ++
    matchResults.map(matchInfo => {
      val matchTeamResults = getMatchOutcome(matchInfo._4, matchInfo._3)
      (matchInfo._2, 1, matchTeamResults._1, matchTeamResults._2, matchTeamResults._3, matchInfo._4, matchInfo._3)
    })

    /*
      Then, let's group these values by a team.
      As a result, we'll get a list of teamTournamentResult.
      It has the following format:
      [(TeamName, NumberOfGames, NumberOfWins, NumberOfDraws, NumberOfLoses, TotalScoredGoals, TotalConcededGoals)]
    */
    val tournamentResults: List[teamTournamentResult] = teamsList
      .groupBy(_._1)
      .view
      .mapValues {
        _.map(s => (s._2, s._3, s._4, s._5, s._6, s._7))
          .reduce((x, y) => (x._1 + y._1, x._2 + y._2, x._3 + y._3, x._4 + y._4, x._5 + y._5, x._6 + y._6))
      }
      .map(x => (x._1, x._2._1, x._2._2, x._2._3, x._2._4, x._2._5, x._2._6))
      .toList

    tournamentResults
  }

  type teamTournamentResultWithScore = (String, Int, Int, Int, Int, Int, Int, Int)

  /**
   * This function converts a list of teamTournamentResult into a list of teamTournamentResultWithScore
   * by calculating a tournament score for each team.
   */
  def getTournamentResultsWithScore(teamTournamentResult: List[teamTournamentResult]): List[teamTournamentResultWithScore] = {
    // According to the Premier League rules, each win gets you 3 points, and each tie gets you one.
    def calculateTournamentPoints(winsAmount: Int, tiesAmount: Int): Int = {
      winsAmount * 3 + tiesAmount
    }

    teamTournamentResult.map(x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, calculateTournamentPoints(x._3, x._4)))
  }

  /**
   * This function takes a list of teamTournamentResultWithScore and returns
   * a sorted list of teamTournamentResultWithScore.
   * It is sorted based on Premier Leagues' rules.
   */
  def getSortedTournamentResults(tournamentResults: List[teamTournamentResultWithScore]): List[teamTournamentResultWithScore] = {
    def sortFunction(item1: teamTournamentResultWithScore, item2: teamTournamentResultWithScore): Boolean = {
      // If tournament points are not the same, we can use them to rank teams.
      if (item1._8 != item2._8) {
        item1._8 > item2._8
      }
      // Otherwise, if goals' differences are not the same, we can use them.
      else if (item1._6 - item1._7 != item2._6 - item2._7) {
        item1._6 - item1._7 > item2._6 - item2._7
      }
      // If goals' differences are also the same, we should use a higher number of goals scored.
      else if (item1._6 != item2._6) {
        item1._6 > item2._6
      }
      // If they are also the same, there should be play-offs.
      // As a temporary measure let's use the number of matches played for now.
      else {
        item1._2 < item2._2
      }
    }

    tournamentResults.sortWith(sortFunction)
  }
}
