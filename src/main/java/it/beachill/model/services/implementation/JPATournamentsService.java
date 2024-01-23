package it.beachill.model.services.implementation;

import it.beachill.model.entities.*;
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

    private boolean generateMatchTournament10Long(Tournament tournament) {
        int[][] tournamentRoundPhaseSchema = {{0, 1}, {2, 3}, {0, 4}, {1, 2}, {3, 4},
                {0, 2}, {1, 3}, {2, 4}, {0, 3}, {1, 4}};

        String[][] tournamentSecondPhaseSchema = {{"QUARTI1x8", "1"}, {"QUARTI1x8", "1"}, {"SEMIFINALE1x4", "1"},
                {"SEMIFINALE1x4", "1"}, {"FINALE1x2", "1"}, {"FINALE3x4", "1"}, {"QUARTI1x8", "2"},
                {"QUARTI1x8", "2"}, {"SEMIFINALE5x8", "2"}, {"SEMIFINALE5x8",  "2"}, {"FINALE9x10", "2"},
                {"FINALE5x6", "2"}, {"FINALE7x8", "2"}};

        //lista dei team iscritti al torneo
        List<TeamInTournament> enrolledTeams = teamInTournamentRepository.findByTournamentId(tournament.getId());

        //funzione che genera un girone ed elimina le partite selezionate da enrolledTeams
        List<TeamInTournament> listRound1 = setRoundForNumOfRandomTeams(enrolledTeams, 1, 5);

        //lista risultato contenente i match del torneo
        List<Match> matches = new ArrayList<>();

        matches.addAll(generateRoundPhaseMatches(listRound1, tournamentRoundPhaseSchema, 1, 1));
        matches.addAll(generateRoundPhaseMatches(enrolledTeams, tournamentRoundPhaseSchema, 2, matches.size()+1));

        matches.addAll(generateSecondPhaseMatches(tournamentSecondPhaseSchema, matches.size() + 1, tournament));
        matchRepository.saveAll(matches);
        return true;
    }
    @Transactional
    private boolean generateMatchTournament10Short(Tournament tournament) {
        //schema per ordinare i match del girone
        int[][] tournamentRoundPhaseSchema = {{0, 1}, {2, 3}, {0, 4}, {1, 2}, {3, 4},
                {0, 2}, {1, 3}, {2, 4}, {0, 3}, {1, 4}};

        //schema per ordinare i match della seconda fase
        String[][] tournamentSecondPhaseSchema = {{"SEMIFINALE1x4", "1"}, {"SEMIFINALE5x8", "1"},
                {"FINALE1x2", "1"}, {"FINALE3x4", "1"}, {"FINALE7x8", "1"}, {"SEMIFINALE1x4", "2"},
                {"SEMIFINALE5x8",  "2"}, {"FINALE9x10", "2"}, {"FINALE5x6", "2"}};

        //lista dei team iscritti al torneo
        List<TeamInTournament> enrolledTeams = teamInTournamentRepository.findByTournamentId(tournament.getId());

        //funzione che genera un girone ed elimina le partite selezionate da enrolledTeams
        List<TeamInTournament> listRound1 = setRoundForNumOfRandomTeams(enrolledTeams, 1, 5);

        //lista risultato contenente i match del torneo
        List<Match> matches = new ArrayList<>();

        matches.addAll(generateRoundPhaseMatches(listRound1, tournamentRoundPhaseSchema, 1, 1));
        matches.addAll(generateRoundPhaseMatches(enrolledTeams, tournamentRoundPhaseSchema, 2, matches.size()+1));

        matches.addAll(generateSecondPhaseMatches(tournamentSecondPhaseSchema, matches.size() + 1, tournament));
        matchRepository.saveAll(matches);
        return true;
    }


    private List<Match> generateRoundPhaseMatches(List<TeamInTournament> roundTeams, int[][] tournamentSchema, int field, int matchNumber){
        List<Match> matches = new ArrayList<>();
        MatchType matchType = matchTypeRepository.findById("GIRONE").get();
        Tournament tournament = roundTeams.get(0).getTournament();
        for(int i = 0; i < tournamentSchema.length; i++){
            matches.add(new Match(matchNumber++, matchType, tournament, roundTeams.get(tournamentSchema[i][0]).getTeam(),
                    roundTeams.get(tournamentSchema[i][1]).getTeam(), field));
        }
        return matches;
    }
    public List<Match> generateSecondPhaseMatches(String[][] tournamentSchema, int matchNumber, Tournament tournament){
        List<Match> matches = new ArrayList<>();
        for(int i = 0; i < tournamentSchema.length; i++){
            matches.add(new Match(matchNumber++, matchTypeRepository.findById(tournamentSchema[i][0]).get(), tournament, Integer.parseInt(tournamentSchema[i][1])));
        }
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

}