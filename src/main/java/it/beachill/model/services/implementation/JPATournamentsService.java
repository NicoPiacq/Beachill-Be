package it.beachill.model.services.implementation;

import it.beachill.model.entities.Match;
import it.beachill.model.entities.TeamInTournament;
import it.beachill.model.entities.Tournament;
import it.beachill.model.entities.TournamentType;
import it.beachill.model.repositories.abstractions.MatchRepository;
import it.beachill.model.repositories.abstractions.TeamInTournamentRepository;
import it.beachill.model.repositories.abstractions.TournamentRepository;
import it.beachill.model.services.abstraction.TournamentsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class JPATournamentsService implements TournamentsService {

    private final TournamentRepository tournamentRepository;
    private final TeamInTournamentRepository teamInTournamentRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public JPATournamentsService(TournamentRepository tournamentRepository, TeamInTournamentRepository teamInTournamentRepository,
                                 MatchRepository matchRepository){
        this.tournamentRepository = tournamentRepository;
        this.teamInTournamentRepository = teamInTournamentRepository;
        this.matchRepository = matchRepository;
    }

    // --------------------------------- METODI CREATE -------------------------------
    public void createOrUpdateMatch(Match match){
        matchRepository.save(match);
    }
    public void createOrUpdateTeamInTournament(TeamInTournament teamInTournament){
        teamInTournamentRepository.save(teamInTournament);
    }
    @Override
    public List<Tournament> findAllTournaments() {
        return tournamentRepository.findAll();
    }

    public List<TeamInTournament> findAllTeamInTournament(Long tournamentId){
        return teamInTournamentRepository.findByTournamentId(tournamentId);
    }

    public boolean generateMatchTournament(Long id){
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
        if(tournamentOptional.isPresent()) {
            Tournament tournament = tournamentOptional.get();
            switch (tournament.getTournamentType().getTournamentTypeName()) {
                case "10-corto":
                    return generateMatchTournament10Short(tournament);
                case "10-lungo":
                    return generateMatchTournament10Long(tournament);
                default:
                    System.out.println("Tipo torneo non valido");
                    break;
            }
        }
        return false;
    }

    @Transactional
    private boolean generateMatchTournament10Short(Tournament tournament) {
        List<TeamInTournament> enrolledTeams = teamInTournamentRepository.findByTournamentId(tournament.getId());
        List<TeamInTournament> listRound1 = setRoundNumberFor5Teams(enrolledTeams, 1);
        List<TeamInTournament> listRound2 = setRoundNumberFor5Teams(enrolledTeams, 2);
        int matchNumber = 1;
        generateMatchFor5TeamsRoundPhase(listRound1, 1, matchNumber);
        generateMatchFor5TeamsRoundPhase(listRound2, 2, matchNumber);
        generateMatchforSecondPhase(21, 26, tournament, 1);
        generateMatchforSecondPhase(26, 30, tournament, 2);
        return true;
    }

    private void generateMatchforSecondPhase(int start, int end, Tournament tournament, int field) {
        for(int i = start; i < end; i++){
            createOrUpdateMatch(new Match(i, tournament, field));
        }
    }

    private void generateMatchFor5TeamsRoundPhase(List<TeamInTournament> roundTeams, int field, int matchNumber){
        createOrUpdateMatch(new Match(matchNumber++, roundTeams.get(0).getTournament(),
                roundTeams.get(0).getTeam(), roundTeams.get(1).getTeam(), field));
        createOrUpdateMatch(new Match(matchNumber++, roundTeams.get(0).getTournament(),
                roundTeams.get(2).getTeam(), roundTeams.get(3).getTeam(), field));
        createOrUpdateMatch(new Match(matchNumber++, roundTeams.get(0).getTournament(),
                roundTeams.get(0).getTeam(), roundTeams.get(4).getTeam(), field));
        createOrUpdateMatch(new Match(matchNumber++, roundTeams.get(0).getTournament(),
                roundTeams.get(1).getTeam(), roundTeams.get(2).getTeam(), field));
        createOrUpdateMatch(new Match(matchNumber++, roundTeams.get(0).getTournament(),
                roundTeams.get(3).getTeam(), roundTeams.get(4).getTeam(), field));
        createOrUpdateMatch(new Match(matchNumber++, roundTeams.get(0).getTournament(),
                roundTeams.get(0).getTeam(), roundTeams.get(2).getTeam(), field));
        createOrUpdateMatch(new Match(matchNumber++, roundTeams.get(0).getTournament(),
                roundTeams.get(1).getTeam(), roundTeams.get(3).getTeam(), field));
        createOrUpdateMatch(new Match(matchNumber++, roundTeams.get(0).getTournament(),
                roundTeams.get(2).getTeam(), roundTeams.get(4).getTeam(), field));
        createOrUpdateMatch(new Match(matchNumber++, roundTeams.get(0).getTournament(),
                roundTeams.get(0).getTeam(), roundTeams.get(3).getTeam(), field));
        createOrUpdateMatch(new Match(matchNumber++, roundTeams.get(0).getTournament(),
                roundTeams.get(1).getTeam(), roundTeams.get(4).getTeam(), field));
    }

    private List<TeamInTournament> setRoundNumberFor5Teams(List<TeamInTournament> enrolledTeams, int round){
        Random random = new Random();
        List<TeamInTournament> listRound = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            int randomIndex = random.nextInt(enrolledTeams.size());
            TeamInTournament team = enrolledTeams.get(randomIndex);
            enrolledTeams.remove(randomIndex);
            listRound.add(team);
            team.setRound(round);
            createOrUpdateTeamInTournament(team);
        }
        return listRound;
    }

    private boolean generateMatchTournament10Long(Tournament tournament) {
        List<TeamInTournament> enrolledTeams = findAllTeamInTournament(tournament.getId());
        return false;
    }

}