package it.beachill.model.services.implementation;

import it.beachill.model.entities.tournament.*;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.TournamentCheckFailedException;
import it.beachill.model.repositories.abstractions.*;
import it.beachill.model.services.abstraction.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JPATournamentsService implements TournamentsService {
    private final TournamentRepository tournamentRepository;
    private final TeamInTournamentRepository teamInTournamentRepository;
    private final TournamentPlaceRepository tournamentPlaceRepository;
    private final TournamentTypeRepository tournamentTypeRepository;
    private final GroupStageStandingRepository groupStageStandingRepository;
    private final TeamRepository teamRepository;
    private final TournamentLevelRepository tournamentLevelRepository;

    //aggiunta solo per implementare la funzione di test che genera i risultati
    // casuali e quindi anche i punteggi dei set
    private final SetMatchRepository setMatchRepository;

    @Autowired
    public JPATournamentsService(TournamentRepository tournamentRepository, TeamInTournamentRepository teamInTournamentRepository,
                                 GroupStageStandingRepository groupStageStandingRepository,
                                 SetMatchRepository setMatchRepository, TournamentPlaceRepository tournamentPlaceRepository,
                                 TournamentTypeRepository tournamentTypeRepository, TeamRepository teamRepository, TournamentLevelRepository tournamentLevelRepository){
        this.tournamentRepository = tournamentRepository;
        this.teamInTournamentRepository = teamInTournamentRepository;
        this.teamRepository = teamRepository;
        this.groupStageStandingRepository = groupStageStandingRepository;
        this.setMatchRepository = setMatchRepository;
        this.tournamentPlaceRepository = tournamentPlaceRepository;
        this.tournamentTypeRepository = tournamentTypeRepository;
        this.tournamentLevelRepository = tournamentLevelRepository;
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

    public List<TournamentPlace> findAllPlaces(){
        return tournamentPlaceRepository.findAll();
    }
    
    @Override
    public void enrolledTeam(User user, Long tournamentId, Long teamId) throws TournamentCheckFailedException {
        Optional<Tournament> optionalTournament=tournamentRepository.findById(tournamentId);
        Optional<Team> optionalTeam=teamRepository.findById(teamId);
        if(optionalTeam.isEmpty()){
            throw new TournamentCheckFailedException("Il team non è presente!");
        }
        if(optionalTournament.isEmpty()){
            throw new TournamentCheckFailedException("Il torneo non è presente!");
        }
        if(!Objects.equals(user.getPlayer().getId(), optionalTeam.get().getTeamLeader().getId())){
            throw new TournamentCheckFailedException("Non sei il capitano del team!");
        }
        TeamInTournament teamInTournament= new TeamInTournament();
        teamInTournament.setTournament(optionalTournament.get());
        teamInTournament.setTeam(optionalTeam.get());
        teamInTournament.setStatus(2);
        teamInTournamentRepository.save(teamInTournament);
    }

    @Override
    public List<TournamentLevel> findAllLevels() {
        return tournamentLevelRepository.findAll();
    }

    public List<TournamentType> findAllTournamentsTypes(){
        return tournamentTypeRepository.findAll();
    }


}