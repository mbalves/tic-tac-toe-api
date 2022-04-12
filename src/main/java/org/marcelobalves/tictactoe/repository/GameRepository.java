package org.marcelobalves.tictactoe.repository;

import org.marcelobalves.tictactoe.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface GameRepository extends CrudRepository<Game, UUID> {
}
