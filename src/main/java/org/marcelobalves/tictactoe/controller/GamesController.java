package org.marcelobalves.tictactoe.controller;

import org.marcelobalves.tictactoe.api.GameDTO;
import org.marcelobalves.tictactoe.api.MakeMoveRequestDTO;
import org.marcelobalves.tictactoe.domain.Game;
import org.marcelobalves.tictactoe.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GamesController {
	private static final Logger LOG = LoggerFactory.getLogger(GamesController.class);

	@Autowired
	GameService gameService;

	/**
	 * createGame
	 *
	 * A user can POST to this endpoint to create a new game, and we will return the unique id of that game.
	 * They can then make moves using that game id.
	 */
	@PostMapping
	public ResponseEntity<GameDTO> createGame() {
		LOG.debug("createGame");

		Game game = gameService.createGame();
		UUID gameId = game.getId();

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(gameId).toUri();

		GameDTO response = mapper(game);

		return ResponseEntity.created(location).body(response);
	}

	private GameDTO mapper(Game game){
		GameDTO dto = new GameDTO();
		dto.setId(game.getId());
		dto.setBoardGame(game.getBoardGame());
		dto.setNextPlayer(game.getNextPlayer());
		dto.setStatus(game.getStatus());
		dto.setWinner(game.getWinner());
		return dto;
	}

	/**
	 * makeMove
	 *
	 * A user can make a move for a particular game.
	 * This should validate the move and change the current game state.
	 * It should return the new game state to the caller
	 */
	@PostMapping(path = "/{gameId}/moves")
	public ResponseEntity<Object> makeMove(@PathVariable("gameId") UUID gameId, @RequestBody MakeMoveRequestDTO makeMoveRequestDTO) {

		// Find the game
		Optional<Game> game = gameService.getGame(gameId);

		if (game.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FATAL ERROR: Game Id Not Found");
		}

		// Validate move
		String validateMsg = gameService.validateMove(game.get(), makeMoveRequestDTO);

		if (!StringUtils.isEmpty(validateMsg)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID MOVE: " + validateMsg);
		}

		// Make move - call gameService

		GameDTO gameDTO = mapper(gameService.makeMove(game.get(), makeMoveRequestDTO));

		return ResponseEntity.ok(gameDTO);
	}

}
