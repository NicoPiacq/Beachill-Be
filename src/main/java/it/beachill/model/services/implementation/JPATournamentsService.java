package it.beachill.model.services.implementation;

import it.beachill.model.entities.*;
import it.beachill.model.repositories.abstractions.*;
import it.beachill.model.services.abstraction.TournamentsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JPATournamentsService implements TournamentsService {
    private final TournamentRepository tournamentRepository;
    private final TeamInTournamentRepository teamInTournamentRepository;
    private final MatchRepository matchRepository;
    private final MatchTypeRepository matchTypeRepository;
    private final GroupStageStandingRepository groupStageStandingRepository;

    @Autowired
    public JPATournamentsService(TournamentRepository tournamentRepository, TeamInTournamentRepository teamInTournamentRepository,
                                 MatchRepository matchRepository, MatchTypeRepository matchTypeRepository,
                                 GroupStageStandingRepository groupStageStandingRepository){
        this.tournamentRepository = tournamentRepository;
        this.teamInTournamentRepository = teamInTournamentRepository;
        this.matchRepository = matchRepository;
        this.matchTypeRepository = matchTypeRepository;
        this.groupStageStandingRepository = groupStageStandingRepository;
    }

    // --------------------------------- METODI CREATE e UPDATE -----------------------------------
    public void createOrUpdateMatch(Match match){
        matchRepository.save(match);
    }
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

    @Override
    public boolean calculateGroupStageStanding(Long id) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
        if(tournamentOptional.isPresent()) {
            Tournament tournament = tournamentOptional.get();
            MatchType matchType = matchTypeRepository.findById("GIRONE").get();
            List<Match> matches = matchRepository.findByTournamentIdAndMatchTypeAndWinnerTeamIsNotNull(id, matchType);
            List<GroupStageStanding> groupStageStandingList = groupStageStandingRepository.findByTournamentId(id);
            for (Match match : matches) {
                Team winnerTeam = match.getWinnerTeam();
                for(int i = 0; i < groupStageStandingList.size(); i++){
                    if(groupStageStandingList.get(i).getTeam().getId().equals(winnerTeam.getId())){
                        groupStageStandingList.get(i).addWinPoints();
                    }
                }
                //forse si riuscirebbe a non eseguire volta per volta una query ma creando una lista
                // con tutti i group stage, aggiornandoli qua, e poi salvando su database tutta la lista
                //GroupStageStanding groupStageStanding = groupStageStandingRepository.findByTournamentIdAndTeamId(tournament, winnerTeam);
                //groupStageStanding.addWinPoints();
            }
            //sempre chat gpt, dovrebbe divedermi la lista in tante sottoliste groopedBy groupStage
            Map<Integer, List<GroupStageStanding>> groupedByGroupStage = GroupStageStanding.groupByGroupStage(groupStageStandingList);

            List<Integer> groupStageKeys = new ArrayList<>(groupedByGroupStage.keySet());

            for (Integer groupStage : groupStageKeys) {
                List<GroupStageStanding> standingsList = groupedByGroupStage.get(groupStage);
                Collections.sort(standingsList);
                for(int i = 0; i < standingsList.size(); i++){
                    standingsList.get(i).setStanding(i+1);
                }
                groupStageStandingRepository.saveAll(standingsList);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addRandomResultToGroupPhaseMatches(Long id) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
        if(tournamentOptional.isPresent()) {
            Tournament tournament = tournamentOptional.get();
            MatchType matchType = matchTypeRepository.findById("GIRONE").get();
            Random random = new Random();
            List<Match> matches = matchRepository.findByTournamentIdAndMatchType(id, matchType);
            for(int i = 0; i < matches.size(); i++){
                if(random.nextBoolean()) {
                    matches.get(i).setWinnerTeam(matches.get(i).getHomeTeam());
                } else {
                    matches.get(i).setWinnerTeam(matches.get(i).getAwayTeam());
                }
            }
            matchRepository.saveAll(matches);
            return true;
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
        List<TeamInTournament> enrolledTeams = findAllTeamInTournament(tournament.getId());

        //funzione che genera un girone ed elimina le partite selezionate da enrolledTeams
        //l' ho richiamata di nuovo due volte perchè ho aggiunto che intanto crea le righe per la tabella della classifica
        List<TeamInTournament> listRound1 = setRoundForNumOfRandomTeams(tournament, enrolledTeams, 1, 5);
        List<TeamInTournament> listRound2 = setRoundForNumOfRandomTeams(tournament, enrolledTeams, 2, 5);

        //lista risultato contenente i match del torneo
        List<Match> matches = new ArrayList<>();

        matches.addAll(generateRoundPhaseMatches(listRound1, tournamentRoundPhaseSchema, 1, 1, 1));
        matches.addAll(generateRoundPhaseMatches(listRound2, tournamentRoundPhaseSchema, 2, matches.size()+1, 2));

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
        List<TeamInTournament> enrolledTeams = findAllTeamInTournament(tournament.getId());

        //funzione che genera un girone ed elimina le partite selezionate da enrolledTeams
        //l' ho richiamata di nuovo due volte perchè ho aggiunto che intanto crea le righe per la tabella della classifica
        List<TeamInTournament> listRound1 = setRoundForNumOfRandomTeams(tournament, enrolledTeams, 1, 5);
        List<TeamInTournament> listRound2 = setRoundForNumOfRandomTeams(tournament, enrolledTeams, 2, 5);

        //lista risultato contenente i match del torneo
        List<Match> matches = new ArrayList<>();

        matches.addAll(generateRoundPhaseMatches(listRound1, tournamentRoundPhaseSchema, 1, 1, 1));
        matches.addAll(generateRoundPhaseMatches(listRound2, tournamentRoundPhaseSchema, 2, matches.size()+1, 2));

        matches.addAll(generateSecondPhaseMatches(tournamentSecondPhaseSchema, matches.size() + 1, tournament));
        matchRepository.saveAll(matches);
        return true;
    }


    private List<Match> generateRoundPhaseMatches(List<TeamInTournament> roundTeams, int[][] tournamentSchema, int field, int matchNumber, int groupStage){
        List<Match> matches = new ArrayList<>();
        MatchType matchType = matchTypeRepository.findById("GIRONE").get();
        Tournament tournament = roundTeams.get(0).getTournament();
        for(int i = 0; i < tournamentSchema.length; i++){
            matches.add(new Match(matchNumber++, matchType, groupStage, tournament, roundTeams.get(tournamentSchema[i][0]).getTeam(),
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

    private List<TeamInTournament> setRoundForNumOfRandomTeams(Tournament tournament, List<TeamInTournament> enrolledTeams, int round, int num){
        Random random = new Random();
        List<TeamInTournament> listRound = new ArrayList<>();
        for(int i = 0; i < num; i++){
            int randomIndex = random.nextInt(enrolledTeams.size());
            TeamInTournament team = enrolledTeams.get(randomIndex);
            //aggiunta creazione righe nella tabella per la classifica fase a gironi
            createOrUpdateGroupStageStanding(new GroupStageStanding(round, tournament, team.getTeam()));
            //fine
            enrolledTeams.remove(randomIndex);
            listRound.add(team);
            team.setRound(round);
            createOrUpdateTeamInTournament(team);
        }
        return listRound;
    }

}