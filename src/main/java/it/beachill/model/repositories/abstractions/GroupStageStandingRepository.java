package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.GroupStageStanding;
import it.beachill.model.entities.Team;
import it.beachill.model.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupStageStandingRepository extends JpaRepository<GroupStageStanding, Long> {
    GroupStageStanding findByTournamentIdAndTeamId(Long id, Team team);

    List<GroupStageStanding>findByTournamentId(Long id);

    List<GroupStageStanding> findByTournamentIdAndGroupStageEqualsOrderByStandingDesc(Long id, int groupStage);

    Optional<GroupStageStanding> findFirstByTournamentIdOrderByGroupStageDesc(Long id);
}
