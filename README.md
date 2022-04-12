
# Tic Tac Toe API
### By Marcelo Alves, October 2021

## Overview
It's a Spring Boot Application to provide backend API to games of Noughts and Crosses aka Tic Tac Toe.  

The main functionality is:

### New game: 	POST /games

- Create a new game and return the fresh game state including the game id

### Make move:  POST /games/{gameId}/moves  
- Make a move in an existing game, and return the new game board state.    
- This will be called multiple times for a particular game, once per move.  
- After each move calculate who win.

## Constraints and Timescales
- There will be no AI or Computer player involved - the application allow two human players to make moves and play a game.
- The gameplay for Tic Tac Toe is a 3x3 board  

## Build

To test your application you can run the integration test in your IDE:
    
    FullEndToEndTest

This test should be expanded on to test multiple moves etc

Notes:
- The project is setup to use H2 database. We would normally only use this for testing, but it's OK to use that for runtime also for this exercise. 

## Testing
There are several ideas for test classes you may wish to write in the "src/test/java" folder.
Please fill these out as needed, and add any others you may need.
Ideally we will see unit tests at the service level, mock mvc tests for the controller including error conditions and at least one integration or "end to end" test covering the happy path.
Remember the timescales mentioned above - if you run out of time, feel free to add TODO comments for any extra test cases you'd like to cover.


## Sample Curl commands

Your solution should be tested using Spring Boot tests, but you can also run the main application file and make requests with curl commands.  
e.g.

New game:
  
    curl -v -X POST http://localhost:8080/games/

Make move:  

    curl -v -X POST -d "{ \"key1\":\"value1\" }" -H "Content-Type: application/json" http://localhost:8080/games/e8d26f0a-18a0-414f-a3ac-edbbe2a6bc57/moves

- the game id here would need to be a valid gameId obtained from a 'new game' call
- the json in the body would need to match your chosen format for a move

