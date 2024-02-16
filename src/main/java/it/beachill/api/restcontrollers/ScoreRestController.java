package it.beachill.api.restcontrollers;

import it.beachill.dtos.ReservationDto;
import it.beachill.dtos.ScoreDto;
import it.beachill.dtos.ScoreTypeDto;
import it.beachill.model.entities.tournament.Score;
import it.beachill.model.entities.tournament.ScoreType;
import it.beachill.model.services.abstraction.ScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@CrossOrigin
public class ScoreRestController {
    private final ScoresService scoresService;

    @Autowired
    public ScoreRestController(ScoresService scoresService) {
        this.scoresService = scoresService;
    }

    @GetMapping("")
    public ResponseEntity<?> getRankingByScoreType(@RequestParam String type){
        ScoreTypeDto scoreTypeDto = new ScoreTypeDto(new ScoreType((type)));
        List<Score> scoreList = scoresService.getRankingByScoreType(scoreTypeDto.fromDto());
        List<ScoreDto> result = scoreList.stream().map(ScoreDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<?> getRankingByPlayerId(@PathVariable Long playerId){
        List<Score> scoreList = scoresService.getRankingByPlayerId(playerId);
        List<ScoreDto> result = scoreList.stream().map(ScoreDto::new).toList();
        return ResponseEntity.ok(result);
    }
}
