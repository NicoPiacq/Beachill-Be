package it.beachill.model.services.implementation;

import it.beachill.dtos.TournamentDto;
import it.beachill.model.entities.*;
import it.beachill.model.repositories.abstractions.*;
import it.beachill.model.services.abstraction.MatchsService;
import it.beachill.model.services.abstraction.TournamentsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JPATournamentsService implements TournamentsService {
    private final MatchsService matchsService;
    private final TournamentRepository tournamentRepository;
    private final TeamInTournamentRepository teamInTournamentRepository;
    private final MatchRepository matchRepository;
    private final MatchTypeRepository matchTypeRepository;
    private final GroupStageStandingRepository groupStageStandingRepository;

    //aggiunta solo per implementare la funzione di test che genera i risultati
    // casuali e quindi anche i punteggi dei set
    private final SetMatchRepository setMatchRepository;

    @Autowired
    public JPATournamentsService(TournamentRepository tournamentRepository, TeamInTournamentRepository teamInTournamentRepository,
                                 MatchRepository matchRepository, MatchTypeRepository matchTypeRepository,
                                 GroupStageStandingRepository groupStageStandingRepository, MatchsService matchsService,
                                 SetMatchRepository setMatchRepository){
        this.tournamentRepository = tournamentRepository;
        this.teamInTournamentRepository = teamInTournamentRepository;
        this.matchRepository = matchRepository;
        this.matchTypeRepository = matchTypeRepository;
        this.groupStageStandingRepository = groupStageStandingRepository;
        this.matchsService = matchsService;
        this.setMatchRepository = setMatchRepository;
    }

    // --------------------------------- METODI CREATE e UPDATE -----------------------------------
    public void createOrUpdateTeamInTournament(TeamInTournament teamInTournament){
        teamInTournamentRepository.save(teamInTournament);
    }
    public void createOrUpdateGroupStageStanding(GroupStageStanding groupStageStanding){
        groupStageStandingRepository.save(groupStageStanding);
    }

    // -------------------------------- FINE METODI CREATE e UPDATE -------------------------------
    @Override
    public List<Tournament> findAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Optional<Tournament> findTournamentById(Long tournamentId){
        return tournamentRepository.findById(tournamentId);
    }

    public List<TeamInTournament> findAllTeamInTournament(Long tournamentId){
        return teamInTournamentRepository.findByTournamentId(tournamentId);
    }


}