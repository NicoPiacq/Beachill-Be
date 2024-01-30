package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.Match;
import it.beachill.model.entities.MatchType;
import it.beachill.model.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByTournamentIdAndMatchType(Long id, MatchType matchType);

    List<Match> findByTournamentIdAndMatchTypeAndWinnerTeamIsNotNull(Long id, MatchType matchType);

    List<Match> findByTournamentId(Long tournamentId);

    List<Match> findByTournamentIdAndMatchTypeNot(Long id, MatchType girone);
}
