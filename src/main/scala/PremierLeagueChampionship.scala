

case class MatchResult(Team1Name: String, Team2Name: String, Team1Score: Int, Team2Score: Int)
/**
 * This class holds methods, specific for the Premier League, such as tournament result calculation.
 */
object PremierLeagueChampionship {

  sealed trait TeamTournamentResultTrait {
    def teamName: String
    def numberOfGames: Int
    def numberOfWins: Int
    def numberOfDraws: Int
    def numberOfLoses: Int
    def scoredGoals: Int
    def concededGoals: Int
  }

  case class TeamTournamentResult (
    teamName: String,
    numberOfGames: Int,
    numberOfWins: Int,
    numberOfDraws: Int,
    numberOfLoses: Int,
    scoredGoals: Int,
    concededGoals: Int
  ) extends TeamTournamentResultTrait
  /**
   * This function converts a List[(String, String, Int, Int)] (Team1Name, Team2Name, Team1Score, Team2Score)
   * to a list of teamTournamentResult.
   */
  def getTournamentResults(matchResults :List[MatchResult]): List[TeamTournamentResult] = {
    case class MatchOutcome (
      NumberOfWins: Int,
      NumberOfDraws: Int,
      NumberOfLoses: Int
    )

    def getMatchOutcome(team1Score: Int, team2Score: Int): MatchOutcome = {
      (team1Score, team2Score) match {
        case x if x._1 > x._2 => MatchOutcome(1, 0, 0)
        case x if x._1 < x._2 => MatchOutcome(0, 0, 1)
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
        teamMatchOutcome.NumberOfWins,
        teamMatchOutcome.NumberOfDraws,
        teamMatchOutcome.NumberOfLoses,
        matchInfo.Team1Score,
        matchInfo.Team2Score)
    }) ++
    matchResults.map(matchInfo => {
      val teamMatchOutcome = getMatchOutcome(matchInfo.Team2Score, matchInfo.Team1Score)
      TeamTournamentResult(
        matchInfo.Team2Name,
        1,
        teamMatchOutcome.NumberOfWins,
        teamMatchOutcome.NumberOfDraws,
        teamMatchOutcome.NumberOfLoses,
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
          val (totalGames, totalWins, totalDraws, totalLoses, totalScored, totalConceded) =
            teamTournamentResults.foldLeft((0,0,0,0,0,0)) {
              case ((numberOfGames, numberOfWins, numberOfDraws, numberOfLoses, scoredGoals, concededGoals), item) =>
                (
                  numberOfGames + item.numberOfGames,
                  numberOfWins + item.numberOfWins,
                  numberOfDraws + item.numberOfDraws,
                  numberOfLoses + item.numberOfLoses,
                  scoredGoals + item.scoredGoals,
                  concededGoals + item.concededGoals,
                )
            }
          TeamTournamentResult(teamName, totalGames, totalWins, totalDraws, totalLoses, totalScored, totalConceded)
      }
      .toList

    tournamentResults
  }

  trait TournamentScore {def tournamentScore: Int}
  case class TeamTournamentResultWithScore (
    teamName: String,
    numberOfGames: Int,
    numberOfWins: Int,
    numberOfDraws: Int,
    numberOfLoses: Int,
    scoredGoals: Int,
    concededGoals: Int,
    tournamentScore: Int
 ) extends TeamTournamentResultTrait with TournamentScore

  /**
   * This function converts a list of teamTournamentResult into a list of teamTournamentResultWithScore
   * by calculating a tournament score for each team.
   */
  def getTournamentResultsWithScore(teamTournamentResult: List[TeamTournamentResult]): List[TeamTournamentResultWithScore] = {
    // According to the Premier League rules, each win gets you 3 points, and each tie gets you one.
    def calculateTournamentPoints(winsAmount: Int, tiesAmount: Int): Int = {
      winsAmount * 3 + tiesAmount
    }
    teamTournamentResult.map(x => TeamTournamentResultWithScore(
      x.teamName,
      x.numberOfGames,
      x.numberOfWins,
      x.numberOfDraws,
      x.numberOfLoses,
      x.scoredGoals,
      x.concededGoals,
      calculateTournamentPoints(x.numberOfWins, x.numberOfDraws)))
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
        case x if x._1.tournamentScore != x._2.tournamentScore => x._1.tournamentScore > x._2.tournamentScore
        // Otherwise, if goals' differences are not the same, we can use them.
        case x if x._1.scoredGoals - x._1.concededGoals != x._2.scoredGoals - x._2.concededGoals =>
          x._1.scoredGoals - x._1.concededGoals > x._2.scoredGoals - x._2.concededGoals
        // If goals' differences are also the same, we should use a higher number of goals scored.
        case x if x._1.scoredGoals != x._2.scoredGoals => x._1.scoredGoals > x._2.scoredGoals
        // If they are also the same, there should be play-offs.
        // As a temporary measure let's use the number of matches played for now.
        case x if x._1.numberOfGames != x._2.numberOfGames => x._1.numberOfGames > x._2.numberOfGames
        // If even that the same, let's just sort them alphabetically.
        case _ => item1.teamName < item2.teamName
      }
    }

    tournamentResults.sortWith(sortFunction)
  }
}
