package it.beachill.model.services.implementation;

import it.beachill.model.entities.Match;
import it.beachill.model.entities.MatchType;
import it.beachill.model.entities.TeamInTournament;
import it.beachill.model.entities.Tournament;
import it.beachill.model.repositories.abstractions.MatchRepository;
import it.beachill.model.repositories.abstractions.MatchTypeRepository;
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
    private final MatchTypeRepository matchTypeRepository;

    @Autowired
    public JPATournamentsService(TournamentRepository tournamentRepository, TeamInTournamentRepository teamInTournamentRepository,
                                 MatchRepository matchRepository, MatchTypeRepository matchTypeRepository){
        this.tournamentRepository = tournamentRepository;
        this.teamInTournamentRepository = teamInTournamentRepository;
        this.matchRepository = matchRepository;
        this.matchTypeRepository = matchTypeRepository;
    }

    // --------------------------------- METODI CREATE -----------------------------------
    public void createOrUpdateMatch(Match match){
        matchRepository.save(match);
    }
    public void createOrUpdateTeamInTournament(TeamInTournament teamInTournament){
        teamInTournamentRepository.save(teamInTournament);
    }

    // -------------------------------- FINE METODI CREATE -------------------------------
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
        //lista dei team iscritti al torneo
        List<TeamInTournament> enrolledTeams = teamInTournamentRepository.findByTournamentId(tournament.getId());
        //funzione che genera i due gironi
        List<TeamInTournament> listRound1 = setRoundForNumOfRandomTeams(enrolledTeams, 1, 5);
        List<TeamInTournament> listRound2 = setRoundForNumOfRandomTeams(enrolledTeams, 2, 5);
        //inizializzo una variabile che utilizzo come counter dei match
        int matchNumber = 1;
        List<Match> matches = new ArrayList<>();
        //funzione che genera le partite FASE A GIRONE per i due gironi diversi, nei due campi a disposizione
        matches.addAll(generateMatchFor5TeamsRoundPhase(listRound1, 1, matchNumber));
        matchNumber = 11;
        matches.addAll(generateMatchFor5TeamsRoundPhase(listRound2, 2, matchNumber));
        matchNumber = 21;
        //genero le partite per la SECONDA FASE nei due campi a disposizione
        //generateMatchforSecondPhase(21, 26, tournament, 1);
        //generateMatchforSecondPhase(26, 30, tournament, 2);
        MatchType semifinal1x4 = matchTypeRepository.findById("SEMIFINALE1x4").get();
        MatchType semifinal5x8 = matchTypeRepository.findById("SEMIFINALE5x8").get();

        matches.add(new Match(matchNumber++, semifinal1x4, tournament, 1));
        matches.add(new Match(matchNumber++, semifinal5x8, tournament, 1));
        matches.add(new Match(matchNumber++, matchTypeRepository.findById("FINALE1x2").get(), tournament, 1));
        matches.add(new Match(matchNumber++, matchTypeRepository.findById("FINALE3x4").get(), tournament, 1));
        matches.add(new Match(matchNumber++, matchTypeRepository.findById("FINALE7x8").get(), tournament, 1));
        matches.add(new Match(matchNumber++, semifinal1x4, tournament, 2));
        matches.add(new Match(matchNumber++, semifinal5x8, tournament, 2));
        matches.add(new Match(matchNumber++, matchTypeRepository.findById("FINALE9x10").get(), tournament, 2));
        matches.add(new Match(matchNumber, matchTypeRepository.findById("FINALE5x6").get(), tournament, 2));
        matchRepository.saveAll(matches);
        return true;
    }

//    private void generateMatchforSecondPhase(int start, int end, Tournament tournament, int field) {
//        for(int matchNumber = start; matchNumber < end; matchNumber++){
//            createOrUpdateMatch(new Match(matchNumber, tournament, field));
//        }
//    }

    private List<Match> generateMatchFor5TeamsRoundPhase(List<TeamInTournament> roundTeams, int field, int matchNumber){
        List<Match> matches = new ArrayList<>();
        MatchType matchType = matchTypeRepository.findById("GIRONE").get();

        matches.add(new Match(matchNumber++, matchType, roundTeams.get(0).getTournament(),
                    roundTeams.get(0).getTeam(), roundTeams.get(1).getTeam(), field));
        matches.add(new Match(matchNumber++, matchType, roundTeams.get(0).getTournament(),
                    roundTeams.get(2).getTeam(), roundTeams.get(3).getTeam(), field));
        matches.add(new Match(matchNumber++, matchType, roundTeams.get(0).getTournament(),
                    roundTeams.get(0).getTeam(), roundTeams.get(4).getTeam(), field));
        matches.add(new Match(matchNumber++, matchType, roundTeams.get(0).getTournament(),
                    roundTeams.get(1).getTeam(), roundTeams.get(2).getTeam(), field));
        matches.add(new Match(matchNumber++, matchType, roundTeams.get(0).getTournament(),
                    roundTeams.get(3).getTeam(), roundTeams.get(4).getTeam(), field));
        matches.add(new Match(matchNumber++, matchType, roundTeams.get(0).getTournament(),
                    roundTeams.get(0).getTeam(), roundTeams.get(2).getTeam(), field));
        matches.add(new Match(matchNumber++, matchType, roundTeams.get(0).getTournament(),
                    roundTeams.get(1).getTeam(), roundTeams.get(3).getTeam(), field));
        matches.add(new Match(matchNumber++, matchType, roundTeams.get(0).getTournament(),
                    roundTeams.get(2).getTeam(), roundTeams.get(4).getTeam(), field));
        matches.add(new Match(matchNumber++, matchType, roundTeams.get(0).getTournament(),
                    roundTeams.get(0).getTeam(), roundTeams.get(3).getTeam(), field));
        matches.add(new Match(matchNumber++, matchType, roundTeams.get(0).getTournament(),
                    roundTeams.get(1).getTeam(), roundTeams.get(4).getTeam(), field));
        return matches;
    }

    private List<TeamInTournament> setRoundForNumOfRandomTeams(List<TeamInTournament> enrolledTeams, int round, int num){
        Random random = new Random();
        List<TeamInTournament> listRound = new ArrayList<>();
        for(int i = 0; i < num; i++){
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