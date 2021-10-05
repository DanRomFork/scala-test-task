package com.onair.scrapper

object Domain {
  case class TeamTournamentResultJSON(
                                       position: Int,
                                       team: String,
                                       games: Int,
                                       wins: Int,
                                       draws: Int,
                                       loses: Int,
                                       goalsScored: Int,
                                       conceded: Int,
                                       points: Int
                                     )

  object TeamTournamentResultJSON {
    def apply(position: Int, teamTournamentResult: TeamTournamentResultWithScore): TeamTournamentResultJSON = {
      TeamTournamentResultJSON(
        position,
        teamTournamentResult.teamTournamentResult.teamName,
        teamTournamentResult.teamTournamentResult.numberOfGames,
        teamTournamentResult.teamTournamentResult.numberOfWins,
        teamTournamentResult.teamTournamentResult.numberOfDraws,
        teamTournamentResult.teamTournamentResult.numberOfLoses,
        teamTournamentResult.teamTournamentResult.scoredGoals,
        teamTournamentResult.teamTournamentResult.concededGoals,
        teamTournamentResult.tournamentScore)
    }
  }

  case class TeamTournamentResult(
                                   teamName: String,
                                   numberOfGames: Int,
                                   numberOfWins: Int,
                                   numberOfDraws: Int,
                                   numberOfLoses: Int,
                                   scoredGoals: Int,
                                   concededGoals: Int
                                 )

  object TeamTournamentResult {
    val Empty: TeamTournamentResult = TeamTournamentResult(teamName = "",
      numberOfGames = 0,
      numberOfWins = 0,
      numberOfDraws = 0,
      numberOfLoses = 0,
      scoredGoals = 0,
      concededGoals = 0)
  }

  case class TeamTournamentResultWithScore(
                                            teamTournamentResult: TeamTournamentResult,
                                            tournamentScore: Int
                                          ) {
    val goalDifference: Int = teamTournamentResult.scoredGoals - teamTournamentResult.concededGoals
    val scoredGoals: Int = teamTournamentResult.scoredGoals
    val numberOfGames: Int = teamTournamentResult.numberOfGames
    val teamName: String = teamTournamentResult.teamName
  }

  object TeamTournamentResultWithScore {
    def apply(teamName: String,
              numberOfGames: Int,
              numberOfWins: Int,
              numberOfDraws: Int,
              numberOfLoses: Int,
              scoredGoals: Int,
              concededGoals: Int,
              tournamentScore: Int): TeamTournamentResultWithScore =
      TeamTournamentResultWithScore(
        TeamTournamentResult(
          teamName, numberOfGames,
          numberOfWins,
          numberOfDraws,
          numberOfLoses,
          scoredGoals,
          concededGoals,
        ),
        tournamentScore)
  }
}
