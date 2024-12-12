# Quiz Game Program

This Java-based game is a quiz-style application where players can answer questions, earn points, and interact with an in-game store to buy helpful items. The game includes achievements, a bonus question system, and a story that progresses based on the player's performance.

## Features

- **Quiz Game**: Answer multiple-choice questions and earn points.
- **In-Game Store**: Purchase items such as score multipliers, skip tokens, secret room keys, and hints using earned points.
- **Bonus Questions**: Answer random bonus questions to earn extra points.
- **Achievements**: Unlock achievements based on your actions and performance.
- **Story Progression**: Different storylines based on the player's score and progress in the game.
- **Help Menu**: A comprehensive help menu that explains how to interact with the game.

## Gameplay

1. **Answer Questions**: Answer multiple-choice questions (A, B, C, D) to earn points.
2. **Use Hints**: Use available hints (5 to start, additional ones can be purchased).
3. **Skip Questions**: Use skip tokens to skip difficult questions (purchasable in the store).
4. **Score Multipliers**: Use score multipliers to double your points (can be purchased).
5. **Secret Rooms**: Unlock secret rooms by purchasing keys to access bonus questions.

## Store Items

The store allows you to purchase the following items:

- **Score Multiplier**: Double your points for the next question.
- **Skip Token**: Skip a question without losing points.
- **Secret Room Key**: Unlock a secret room for a chance to answer a bonus question.
- **Hint**: Get a hint for a question you’re stuck on.

## Achievements

You can earn achievements based on your performance, including:

- Answering all questions correctly.
- Using no hints.
- Not purchasing any items.
- Unlocking all bonus rooms.
- Completing each section of the game.

## Usage

To run the game:

1. Clone or download this repository.
2. Compile the Java files using `javac`:
    ```bash
    javac GameProgram.java
    ```
3. Run the game:
    ```bash
    java GameProgram
    ```

## Game Flow

1. The game will start by asking questions from different sections.
2. You can earn points based on correct answers and time taken.
3. You can purchase items from the store using earned points.
4. Continue progressing through sections, unlocking achievements, and completing the game.

## Help Menu

- **A, B, C, D**: Answer regular multiple-choice questions.
- **H**: Use a hint (start with 5, can be bought in the store).
- **?**: Activate a secret room key and access a bonus question.
- **S**: Use a skip token to skip a question.
- **M**: Use a score multiplier to double points for a question.
- **In the Store**:
    - `1`: Buy a score multiplier.
    - `2`: Buy a skip token.
    - `3`: Buy a secret room key.
    - `4`: Buy a hint.
