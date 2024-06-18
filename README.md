# Daschund card game
## Overview
The Card Game is a strategic simulation where players manage a kingdom by making decisions through a deck of thematic cards. Each card presents a scenario affecting the kingdom's core attributes: food, military, economy, and religion. The unique twist of the game is its dachshund theme, where all cards are inspired by dachshunds, adding a playful element to the serious task of kingdom management.

## Gameplay
Players interact with the game by either accepting or rejecting cards that appear on the screen. Each decision will either positively or negatively impact the kingdom's attributes. The goal is to maintain balance in the kingdom, ensuring none of the attributes fall to zero or below, which would result in the end of the game.

## Key Features
### Random Card Impact: Introduces variability in gameplay, making each playthrough unique. The impact of each card decision is randomized within a range, adding to the challenge.
### Database Integration: Utilizes an H2 database to manage game data, including card information and player high scores.
### Graphical User Interface (GUI): Provides an intuitive and visually appealing interface for players to interact with the game.

## Technical Details
- Attribute Class: An enumeration class containing the kingdom’s attributes.
- Card Class: Represents individual cards with properties such as id, title, description, and image. It includes methods to determine the impact of card decisions.
- CardLoadUtil Class: Responsible for loading card data from a CSV file into the game.
- DatabaseEngine Class: Manages the H2 database, including saving and retrieving card data and player scores.
- Engine Class: The core of the game, handling the game logic, updating kingdom attributes, and managing game sessions.
- ImageUtil Class: Handles image processing, including loading and scaling images.
- Kingdom Class: Creates and manages the state of the kingdom.
- SQLCommands Class: Contains SQL queries for database interactions.

## User Interface
Start Screen: Allows players to start a new game or view the leaderboard with the best scores.
Game Screen: Displays cards for the player to accept or reject, showing the current state of the kingdom and the number of days in reign.
End Screen: Enables players to enter their name for the high scores, review their decisions, and restart the game or return to the start screen.
