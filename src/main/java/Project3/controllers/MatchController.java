package Project3.controllers;

import Project3.models.FinishedMatch;
import Project3.models.OngoingMatch;
import Project3.models.Player;
import Project3.services.FinishedMatchesService;
import Project3.services.MatchScoreCalculationService;
import Project3.services.OngoingMatchesService;
import Project3.services.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class MatchController {
    private final OngoingMatchesService ongoingMatchesService;
    private final MatchScoreCalculationService matchScoreCalculationService;
    private final PlayersService playersService;
    private final FinishedMatchesService finishedMatchesService;


    @Autowired
    public MatchController(OngoingMatchesService ongoingMatchesService, MatchScoreCalculationService matchScoreCalculationService, PlayersService playersService, FinishedMatchesService finishedMatchesService) {
        this.ongoingMatchesService = ongoingMatchesService;
        this.matchScoreCalculationService = matchScoreCalculationService;
        this.playersService = playersService;
        this.finishedMatchesService = finishedMatchesService;
    }

    @GetMapping("/new-match")
    public String newMatch(Model model) {
        return "match/newMatch";
    }

    @PostMapping("/new-match")
    public String createNewMatch(@RequestParam("player1") String player1,
                                 @RequestParam("player2") String player2,
                                 Model model) {
        if ((playersService.isPersonUnique(player1)) && (playersService.isPersonUnique(player2))) {
            Player newPlayer1 = playersService.createPlayer(player1);
            Player newPlayer2 = playersService.createPlayer(player2);

            OngoingMatch ongoingMatch = new OngoingMatch(newPlayer1, newPlayer2);
            ongoingMatchesService.createMatch(ongoingMatch);
            return "redirect:/match-score?uuid=" + ongoingMatch.getId();
        } else {
            return "redirect:/new-match";
        }
    }

    @GetMapping("/match-score")
    public String getMatchScore(Model model, @RequestParam("uuid") Integer uuid) {
        OngoingMatch ongoingMatch = ongoingMatchesService.getMatchById(uuid);
        model.addAttribute("ongoingMatch", ongoingMatch);
        return "match/checkMatch";
    }

    @PostMapping("/match-score")
    public String postMatchScore(@RequestParam("matchId") Integer matchId,
                                 @RequestParam(value = "firstPlayerId", required = false) Integer firstPlayerId,
                                 @RequestParam(value = "secondPlayerId", required = false) Integer secondPlayerId) {
        OngoingMatch ongoingMatch = ongoingMatchesService.getMatchById(matchId);
        matchScoreCalculationService.addPointToPlayer(firstPlayerId, secondPlayerId, ongoingMatch, matchId);
        if (matchScoreCalculationService.isMatchExists()) {
            return "redirect:/match-score?uuid=" + matchId;
        } else {
            return "redirect:/matches";
        }
    }

    @GetMapping("/matches")
    public String getMatches(@RequestParam(value = "page", required = false) Integer page,
                             Model model) {
        if (page == null) {
            model.addAttribute("listOfFinishedMatches", finishedMatchesService.getAllFinishedMatches());
        } else {
            model.addAttribute("listOfFinishedMatches", finishedMatchesService.getFinishedMatchesWithPagination(page));
        }
        return "match/finishedMatches";
    }

    @PostMapping("/matches")
    public String postMatches(@RequestParam("playerToSearch") String name,
                              Model model) {
        Player player = playersService.getPlayerByName(name);
        if (player == null) {
            model.addAttribute("nullPlayer", player);
            return "match/finishedMatches";
        }
        FinishedMatch finishedMatch = finishedMatchesService.getMatchByPlayer(player.getId());
        model.addAttribute("foundMatch", finishedMatch);
        return "match/finishedMatches";
    }
}
