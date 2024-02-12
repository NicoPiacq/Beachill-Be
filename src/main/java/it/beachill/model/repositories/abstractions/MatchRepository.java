package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.tournament.Match;
import it.beachill.model.entities.tournament.MatchType;
import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByTournamentIdAndMatchType(Long id, MatchType matchType);

    List<Match> findByTournamentIdAndMatchTypeAndWinnerTeamIsNotNull(Long id, MatchType matchType);

    List<Match> findByTournamentId(Long tournamentId);

    List<Match> findByTournamentIdAndMatchTypeNot(Long id, MatchType girone);

    List<Match> findByMatchAdmin(User user);

    List<Match> findByHomeTeamOrAwayTeam(Team homeTeam, Team awayTeam);
}
