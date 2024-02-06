package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.tournament.GroupStageStanding;
import it.beachill.model.entities.tournament.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupStageStandingRepository extends JpaRepository<GroupStageStanding, Long> {
    GroupStageStanding findByTournamentIdAndTeamId(Long id, Team team);

    List<GroupStageStanding>findByTournamentId(Long id);

    List<GroupStageStanding> findByTournamentIdAndGroupStageEqualsOrderByStandingAsc(Long id, int groupStage);

    Optional<GroupStageStanding> findFirstByTournamentIdOrderByGroupStageDesc(Long id);
}
