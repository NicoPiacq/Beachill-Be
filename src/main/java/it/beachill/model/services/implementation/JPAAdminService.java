package it.beachill.model.services.implementation;

import it.beachill.model.entities.*;
import it.beachill.model.repositories.abstractions.*;
import it.beachill.model.services.abstraction.AdminsService;
import it.beachill.model.services.abstraction.MatchsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JPAAdminService implements AdminsService {

    private final MatchsService matchsService;
    private final TournamentRepository tournamentRepository;
    private final TeamInTournamentRepository teamInTournamentRepository;
    private final MatchRepository matchRepository;
    private final MatchTypeRepository matchTypeRepository;
    private final GroupStageStandingRepository groupStageStandingRepository;
    private final SetMatchRepository setMatchRepository;

    @Autowired
    public JPAAdminService(TournamentRepository tournamentRepository, TeamInTournamentRepository teamInTournamentRepository,
                           MatchRepository matchRepository, MatchTypeRepository matchTypeRepository,
                           GroupStageStandingRepository groupStageStandingRepository, MatchsService matchsService,
                           SetMatchRepository setMatchRepository) {
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

    //    // --------------------------------- METODI CREATE e UPDATE -----------------------------------
    @Override

    public List<Tournament> findAllTournaments() {
        return tournamentRepository.findAll();
    }
    @Override

    public Optional<Tournament> findTournamentById(Long tournamentId){
        return tournamentRepository.findById(tournamentId);
    }

    @Override
    public void createTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    @Override
    public Optional<Tournament> deleteTournament(Long id) {
        Optional<Tournament> oc = tournamentRepository.findById(id);
        oc.ifPresent(c -> tournamentRepository.delete(c));
        return oc;
    }


    public List<TeamInTournament> findAllTeamInTournament(Long tournamentId){
        return teamInTournamentRepository.findByTournamentId(tournamentId);
    }
    @Override

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
                Team homeTeam = match.getHomeTeam();
                Team awayTeam = match.getAwayTeam();

                for(int i = 0; i < groupStageStandingList.size(); i++){
                    if(groupStageStandingList.get(i).getTeam().getId().equals(homeTeam.getId())) {
                        if (match.getWinnerTeam()) { //vince il team casa
                            groupStageStandingList.get(i).addWinPoints();
                        }
                        List<SetMatch> setsMatch = setMatchRepository.findByMatchId(match.getId());
                        for (SetMatch setMatch : setsMatch) {
                            groupStageStandingList.get(i).addPointScoredAndPointConceded(setMatch.getHomeTeamScore(), setMatch.getAwayTeamScore());
                        }
                    }
                    if(groupStageStandingList.get(i).getTeam().getId().equals(awayTeam.getId())) {
                        if (!match.getWinnerTeam()) {
                            groupStageStandingList.get(i).addWinPoints();
                        }
                        List<SetMatch> setsMatch = setMatchRepository.findByMatchId(match.getId());
                        for (SetMatch setMatch : setsMatch) {
                            groupStageStandingList.get(i).addPointScoredAndPointConceded(setMatch.getAwayTeamScore(), setMatch.getHomeTeamScore());
                        }
                    }
                }
                //forse si riuscirebbe a non eseguire volta per volta una query ma creando una lista
                // con tutti i group stage, aggiornandoli qua, e poi salvando su database tutta la lista
                //GroupStageStanding groupStageStanding = groupStageStandingRepository.findByTournamentIdAndTeamId(tournament, winnerTeam);
                //groupStageStanding.addWinPoints();
            }
            //------------------------------------ SONO ARRIVATO QUA --------------------------------
            //sempre chat gpt, dovrebbe divedermi la lista in tante sottoliste groopedBy groupStage
            Map<Integer, List<GroupStageStanding>> groupedByGroupStage = GroupStageStanding.groupByGroupStage(groupStageStandingList);

            //Collections.sort(groupedByGroupStage.get(0));

            List<Integer> groupStageKeys = new ArrayList<>(groupedByGroupStage.keySet());

            boolean thereAreDuplicate;

            for (Integer groupStage : groupStageKeys) {
                List<GroupStageStanding> standingsList = groupedByGroupStage.get(groupStage);
                Collections.sort(standingsList);

                // proviamo a vedere qualcosa per le squadre pari punti
                thereAreDuplicate = standingsList.stream()
                        .map(GroupStageStanding::getPointScored)
                        .collect(Collectors.toSet())
                        .size() < standingsList.size();
                /////////// fine prova

                for(int i = 0; i < standingsList.size(); i++){
                    //AGGIUNTA PER LO SWAP DEI TEAM CON GLI STESSI PUNTEGGI
                    //implementato il caso di due team con lo stesso punteggio ma non generalizzato a n team con lo stesso punteggio
                    if(thereAreDuplicate && i < standingsList.size()-1){
                        //DA RICORDARCI CHE SVILUPPIAMO SOLO IL CASO CON 2 SCORE UGUALI
                        if(standingsList.get(i).getPoints() == standingsList.get(i+1).getPoints()){
                            double quotientPointsA = (double) standingsList.get(i).getPointScored() / standingsList.get(i).getPointConceded();
                            double quotientPointsB = (double) standingsList.get(i+1).getPointScored() / standingsList.get(i+1).getPointConceded();
                            if(quotientPointsA < quotientPointsB){
                                standingsList.get(i).setStanding(i+2);
                                standingsList.get(i).setStanding(i+1);
                            }

                        }
                    } else {
                        standingsList.get(i).setStanding(i + 1);
                    }
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
                List<SetMatch> setsMatch;
                if(random.nextBoolean()) {
                    Match match = matches.get(i);
                    match.setWinnerTeam(true);
                    setsMatch = setMatchRepository.findByMatchId(match.getId());
                    for(SetMatch setMatch : setsMatch){
                        setMatch.setHomeTeamScore(21);
                        setMatch.setAwayTeamScore(random.nextInt(19));
                    }
                } else {
                    Match match = matches.get(i);
                    matches.get(i).setWinnerTeam(false);
                    setsMatch = setMatchRepository.findByMatchId(match.getId());
                    for(SetMatch setMatch : setsMatch){
                        setMatch.setHomeTeamScore(random.nextInt(19));
                        setMatch.setAwayTeamScore(21);
                    }
                    setMatchRepository.saveAll(setsMatch);
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

        matches.addAll(generateRoundPhaseMatches(listRound1, tournamentRoundPhaseSchema, 1, 1, 1, 1));
        matches.addAll(generateRoundPhaseMatches(listRound2, tournamentRoundPhaseSchema, 2, matches.size()+1, 2, 1));

        matches.addAll(generateSecondPhaseMatches(tournamentSecondPhaseSchema, matches.size() + 1, tournament, 1));
        matchRepository.saveAll(matches);
        return true;
    }

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

        matches.addAll(generateRoundPhaseMatches(listRound1, tournamentRoundPhaseSchema, 1, 1, 1, 1));
        matches.addAll(generateRoundPhaseMatches(listRound2, tournamentRoundPhaseSchema, 2, matches.size()+1, 2, 1));

        matches.addAll(generateSecondPhaseMatches(tournamentSecondPhaseSchema, matches.size() + 1, tournament, 1));
        matchRepository.saveAll(matches);
        return true;
    }

    private List<Match> generateRoundPhaseMatches(List<TeamInTournament> roundTeams, int[][] tournamentSchema, int field, int matchNumber, int groupStage, int setsNumber){
        List<Match> matches = new ArrayList<>();
        MatchType matchType = matchTypeRepository.findById("GIRONE").get();
        Tournament tournament = roundTeams.get(0).getTournament();
        for(int i = 0; i < tournamentSchema.length; i++){
            matches.add(matchsService.createMatchAndSets(matchNumber++, matchType, groupStage, tournament, roundTeams.get(tournamentSchema[i][0]).getTeam(),
                    roundTeams.get(tournamentSchema[i][1]).getTeam(), field, setsNumber));
        }
        return matches;
    }

    public List<Match> generateSecondPhaseMatches(String[][] tournamentSchema, int matchNumber, Tournament tournament, int setsNumber){
        List<Match> matches = new ArrayList<>();
        for(int i = 0; i < tournamentSchema.length; i++){
            matches.add(matchsService.createMatchAndSets(matchNumber++, matchTypeRepository.findById(tournamentSchema[i][0]).get(), tournament, Integer.parseInt(tournamentSchema[i][1]), setsNumber));
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
