
//This imports the required library of commands to access the common
//utilities and the input and output commands.
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Initialize game state variables.

        // Variable to hold the user's accumulated score in the game.
        int totalScore = 0;

        // Variable to keep track of the number of lives the user has left.
        int lives = 5;

        // Variable to track the number of hints the user has remaining.
        int hints = 5;

        // Variable to count the number of hints used.
        int hintsUsed = 0;

        // Variable to hold the current score multiplier.
        int scoreMultiplier = 1;

        // Variable to hold the number of score multipliers the user has in their
        // inventory.
        int scoreMultipliersBought = 0;

        // Variable to hold the number of skip tokens the user has available.
        int skipTokens = 0;

        // Variable to count the number of skip tokens used.
        int skipTokensUsed = 0;

        // Variable to hold the number of secret room keys available to use.
        int secretRoomKeys = 0;

        // Variable to count the number of bonus questions seen.
        int bonusQuestionsUsed = 0;

        // Flag variable for mid-game shop visit halfway through the game.
        boolean midGameShopVisited = false;

        // Record of the longest answer streak the user has.
        int greatestAnswerStreak = 0;

        // Variable to hold the current answer streak the user has.
        int currentStreak = 0;

        // Variable to store the start time for timed questions.
        long startTime;

        // 15 seconds in milliseconds --> used to check if the user has taken too long
        // to answer for the timed questions.
        long timeLimit = 15000;

        // This variable is used to check if the question will be timed.
        boolean isTimed = false;

        // Flag variable to track if a hint was used.
        boolean usedHint = false;

        // Flag variable to track if an item was bought.
        boolean boughtItem = false;

        // Flag variable to track if all questions were answered correctly.
        boolean answeredAllCorrectly = true;

        // Variable to hold the count of score multipliers used.
        int scoreMultipliersUsed = 0;

        // Flag variable for the completion of section 1.
        boolean section1Completed = false;

        // Flag variable for the completion of section 2.
        boolean section2Completed = false;

        // Flag variable for the completion of section 3.
        boolean section3Completed = false;

        // Array to store the possible achievements the user can earn.
        String[] achievements = new String[7];

        // Counter for achievements that will be incremented depending on the number of
        // achievements the user has unlocked.
        int achievementCount = 0;

        // Define story messages for each section.

        // Array to hold story messages for the beginner section.
        String[] beginnerStory = {
                "You cautiously proceed down the dimly lit hallway, the shadows growing more ominous with each step.",
                "The air grows colder as you make progress, and you notice strange symbols on the walls.",
                "Faint whispers echo around you, guiding you through the puzzles.",
                "The shadows begin to retreat slightly as you solve more puzzles.",
                "You feel a sense of accomplishment as you decode another cryptic message.",
                "Glowing symbols light your way, indicating you are on the right path.",
                "The hallway seems less intimidating now, as you become more confident in your abilities.",
                "You discover hidden passages with ancient scripts as you advance further.",
                "The hallway opens up, revealing a grand doorway ahead.",
                "You step through the doorway, having mastered the fundamental concepts, ready for the next challenge."
        };

        //Array to hold story messages for the intermediate section.
        String[] intermediateStory = {
                "You enter the enchanted library, greeted by the soft glow of ancient artifacts and enchanted books.",
                "Magical scrolls flutter around, responding to your correct answers.",
                "You feel the presence of ancient wisdom guiding you.",
                "The library reveals hidden compartments with each question you answer.",
                "The bookshelves shift, uncovering more complex challenges.",
                "You find a powerful artifact that boosts your understanding.",
                "The library's magic becomes more pronounced, aiding your progress.",
                "Ancient books open on their own, revealing secrets as you solve puzzles.",
                "You feel a surge of energy as you unlock a particularly difficult question.",
                "The library's magic leads you to a hidden passage, ready for the ultimate test."
        };

        // Array to hold story messages for the expert section.
        String[] expertStory = {
                "The Forbidden Chamber is dark and ominous, with ancient runes glowing faintly in the darkness.",
                "With each correct answer, the chamber illuminates slightly, revealing more of its secrets.",
                "You sense a powerful energy coursing through the room.",
                "The runes on the walls glow brighter as you solve more complex problems.",
                "Ancient artifacts start to hum with energy, reacting to your progress.",
                "You feel a deep connection to the ancient knowledge stored here.",
                "The chamber's power becomes almost overwhelming, but you press on.",
                "Mystical forces guide your hand, helping you solve the toughest puzzles.",
                "The chamber is now brightly lit, and you can feel the power of the ancient artifacts guiding you.",
                "You have mastered the Forbidden Chamber, unlocking its deepest secrets and achieving ultimate enlightenment."
        };

        // Main game loop that runs until the user decides to quit.
        do {

            // Display the main menu and rules of the game.
            displayRules();

            // Grab the username from the user.
            String username = getString("Enter your username:", true, 1, 100);

            // Declaring the 2D array that will hold the set of questions to use
            String[][] strQuestions = new String[20][8];

            // This variable keeps track of what the current questions number we are at.
            int iQCount = 0;

            // Variable to check for end game status.
            boolean end = false;

            // This variable keeps track of what the current section is.
            int iSection = 0;

            // Array to hold all the bonus questions, their answers, and explanations.
            String[][] bonusQuestions = {
                    {
                            // This is the question.
                            "What is the output of the following code? Use periods for spaces and \\n for newlines.\n\nint[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};\nfor (int i = 0; i < arr.length; i++) {\n  for (int j = arr[i].length - 1; j >= 0; j--) {\n    System.out.print(arr[i][j] + \" \");\n  }\n  System.out.println();\n}\n",
                            // This is the answer.
                            "3.2.1.\\n6.5.4.\\n9.8.7.\\n",
                            // This is the explanation.
                            "Explanation: The nested loop prints each row of the 2D array in reverse order."
                    },
                    // This order applies for all the other bonus questions below.
                    {
                            "What is the output of the following code? Use periods for spaces and \\n for newlines.\n\nString str = \"abcdef\";\nfor (int i = 0; i < str.length(); i += 2) {\n  System.out.print(str.charAt(i));\n}\n",
                            "ace",
                            "Explanation: The loop increments by 2, printing every second character of the string."
                    },
                    {
                            "What is the output of the following code? Use periods for spaces and \\n for newlines.\n\nint x = 3;\nint y = 5;\nint z = x+1+y+1;\nSystem.out.println(x + \", \" + y + \", \" + z);\n",
                            "3,.5,.10",
                            "Explanation: In this code, x is initialized to 3 and y to 5. Then, z is assigned the value of x (3) plus 1 plus y (5) plus 1, which equals 10. Finally, the values of x, y, and z are printed, separated by commas and spaces, resulting in the output."
                    },
                    {
                            "What is the output of the following code? Use periods for spaces and \\n for newlines.\n\nString[] words = {\"this\", \"is\", \"a\", \"test\"};\nfor (int i = words.length - 1; i >= 0; i--) {\n  System.out.print(words[i] + \" \");\n}\n",
                            "test.a.is.this.",
                            "Explanation: The loop prints the words array in reverse order."
                    },
                    {
                            "What is the output of the following code? Use periods for spaces and \\n for newlines.\n\nint count = 0;\nfor (int i = 0; i < 5; i++) {\n  for (int j = i; j < 5; j++) {\n    count++;\n  }\n}\nSystem.out.println(count);\n",
                            "15",
                            "Explanation: The nested loop counts the number of iterations, resulting in 15."
                    },
                    {
                            "What is the output of the following code? Use periods for spaces and \\n for newlines.\n\nString s = \"racecar\";\nboolean isPalindrome = true;\nfor (int i = 0; i < s.length() / 2; i++) {\n  if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {\n    isPalindrome = false;\n  }\n}\nSystem.out.println(isPalindrome);\n",
                            "true",
                            "Explanation: The loop checks if the string is a palindrome."
                    },
                    {
                            "What is the output of the following code? Use periods for spaces and \\n for newlines.\n\nint[] nums = {1, 2, 3, 4, 5};\nint sum = 0;\nfor (int num : nums) {\n  if (num % 2 != 0) {\n    sum += num;\n  }\n}\nSystem.out.println(sum);\n",
                            "9",
                            "Explanation: The loop sums only the odd numbers in the array."
                    },
                    {
                            "What is the output of the following code? Use periods for spaces and \\n for newlines.\n\nString s = \"hello world\";\ns = s.toUpperCase().substring(0, 5);\nSystem.out.println(s);\n",
                            "HELLO",
                            "Explanation: The string is converted to uppercase and then the first 5 characters are taken."
                    },
                    {
                            "What is the output of the following code? Use periods for spaces and \\n for newlines.\n\nint n = 5;\nfor (int i = 1; i <= n; i++) {\n  for (int j = 1; j <= i; j++) {\n    System.out.print(j + \" \");\n  }\n  System.out.println();\n}\n",
                            "1.\\n1.2.\\n1.2.3.\\n1.2.3.4.\\n1.2.3.4.5.\\n",
                            "Explanation: The nested loops print a right-angled triangle of numbers."
                    },
                    {
                            "What is the output of the following code? Use periods for spaces and \\n for newlines.\n\nint x = 10;\nint y = 0;\nif (y != 0) {\n  System.out.println(x / y);\n} else {\n  System.out.println(\"Cannot divide by zero\");\n}\n",
                            "Cannot.divide.by.zero",
                            "Explanation: The code checks if y is zero and prints a message to avoid division by zero."
                    }
            };

            do {
                // Clears the screen.
                System.out.print("\033[H\033[2J\f");
                System.out.flush();

                // Loop for the current topic.
                do {
                    // Initial setup calls "initialize" to ensure the string array is empty.
                    // Stores nothing ("") in all spots in the 2D array.
                    initialize(strQuestions, "");

                    // Used in the random number generation and loop end detection.
                    int iNumQs = strQuestions.length;

                    // Clears the screen.
                    System.out.print("\033[H\033[2J\f");
                    System.out.flush();

                    // Increase the section number by one.
                    iSection++;

                    // This switch structure determines the correct set of questions to load based
                    // on the section number.
                    switch (iSection) {
                        case 1:
                            // Call method for section/topic 1 (Beginning Section)
                            fillArrayTopic1(strQuestions);

                            // Introduction to the Cryptic Hallway section
                            System.out.println(
                                    "\nBeginner Section: The Cryptic Hallway --> Fundamental Programming Concepts");
                            break;
                        case 2:
                            // Call method for section/topic 2 (Intermediate Section)
                            fillArrayTopic2(strQuestions);

                            // Introduction to the Enchanted Library section
                            System.out.println(
                                    "\nIntermediate Section: The Enchanted Library --> Deepen Your Understanding & Test Your Application Skills");
                            break;
                        case 3:
                            // Call method for section/topic 3 (Final Section))
                            fillArrayTopic3(strQuestions);

                            // Introduction to the Forbidden Chamber section
                            System.out.println(
                                    "\nExpert Section: The Forbidden Chamber --> Demonstrate Your Knowledge Through Complex & Abstract Problems");
                            break;
                    }

                    // Loop to do the random questions.
                    do {
                        // Add one to the question count.
                        iQCount++;

                        // Check if it's time to visit the mid-game shop (only in section 2 after 5
                        // questions).
                        if (iSection == 2 && iQCount > 5 && !midGameShopVisited) {
                            // Receive the integer array returned from the midgame shop function, with all
                            // the items the user bought.
                            int[] shopResult = visitMidGameShop(totalScore, hints, skipTokens, secretRoomKeys,
                                    scoreMultipliersBought, boughtItem);

                            // Update the total, hints, skip tokens, secret room keys, score multipliers,
                            // and bought items accordingly.
                            totalScore = shopResult[0];
                            hints = shopResult[1];
                            skipTokens = shopResult[2];
                            secretRoomKeys = shopResult[3];
                            scoreMultipliersBought = shopResult[4];

                            // As the result of the shop method was an integer array, boughtItem needs to be
                            // converted to a boolean by evaluating this expression. If the method returned
                            // 1 in its place, boughtItem holds true, else it holds false.
                            boughtItem = shopResult[5] == 1;

                            // Set the flag variable to true.
                            midGameShopVisited = true;
                        }

                        // Select a random question.
                        int iRandQ = (int) (Math.random() * iNumQs);

                        // If the random question chosen above has already been used (the question
                        // portion is empty)
                        // then grab the next question in line (wraps around after it gets to the last
                        // question).
                        while (iQCount <= iNumQs && strQuestions[iRandQ][0] == "") {
                            iRandQ = (iRandQ + 1) % iNumQs;
                        }

                        // Show the randomly selected question:
                        System.out.println("\nQuestion #" + iQCount + " -- " + strQuestions[iRandQ][0]);

                        // Clear that question so it is not selected again.
                        strQuestions[iRandQ][0] = "";

                        // This shows the four options
                        for (int i = 1; i < strQuestions[0].length - 3; i++) {
                            // Displays the options – this line will show uppercase “A”, “B”, “C” and “D”.
                            System.out.println("\n" + (char) (i + 'A' - 1) + ") " + strQuestions[iRandQ][i]);
                        }

                        // Variable to hold the user's answer.
                        String input;

                        // Boolean variable to check if the user's answer is valid.
                        boolean check = false;

                        // Mark the starting time when the question is displayed, in milliseconds. Used
                        // for the last 5 timed questions.
                        startTime = getTime();

                        // Check if the question is a timed question, to set the flag variable equal to
                        // true, for access to double points in the score method.
                        if (iSection == 3 && iQCount > 5) {
                            // Set the flag variable to true, so the scoring method can return double points if the question is answered under 15 seconds.
                            isTimed = true;

                            // Display a message to the user indicating that the question is under a time limit.
                            System.out.println("\nThis question is timed. Answer within 15 seconds for double points!");
                        } else {
                            // Otherwise, this means that the question is not timed.
                            isTimed = false;
                        }

                        // Loop to get a valid answer from the user.
                        do {
                            // Get an input from the user to perform an action.
                            input = getString("\n\nPlease enter your answer here (A, B, C, D, or # for help)", false, 1,
                                    1);
                            switch (input.toUpperCase().charAt(0)) {
                                case 'H':
                                    // Check if the user has a hint left.
                                    if (hints > 0) {
                                        // If they do, remove one hint from their inventory.
                                        hints--;

                                        // Add 1 to the number hints they have used up until that point in the game
                                        hintsUsed++;

                                        // Set the flag variable of using a hint to true.
                                        usedHint = true;

                                        // Display how many hints the user has left.
                                        System.out.print("Hints Remaining: " + hints + "\n");

                                        // Display the hint.
                                        System.out.println(strQuestions[iRandQ][7]);
                                    } else {
                                        // Message to display if user doesn't have a hint left.
                                        System.out.println("No hints remaining.");
                                    }
                                    break;
                                case 'S':
                                    // Checks if the user has a skip token left.
                                    if (skipTokens > 0) {
                                        // If they do, remove one skip token from their inventory.
                                        skipTokens--;

                                        // Add 1 to the number of skip tokens the user has used up until that point in
                                        // the game.
                                        skipTokensUsed++;

                                        // Display the number of skip tokens the user has left.
                                        System.out.print("Skip Tokens Remaining: " + skipTokens + "\n");

                                        // Display a message telling the user their action was successful.
                                        System.out.println(
                                                "You used a skip token and have earned full points for this question. Moving to the next question.");

                                        // The user receives full points for the question, so the score method is
                                        // called.
                                        totalScore += doScores(iSection, 0, scoreMultiplier, timeLimit, isTimed);

                                        // Setting the flag to true allows the user to break out of the loop without
                                        // giving any input for the question.
                                        check = true;
                                    } else {
                                        // Message to display if they don't have any skip tokens left.
                                        System.out.println("No skip tokens remaining.");
                                    }
                                    break;
                                case '?':
                                    // Checks if the user has a secret room key available.
                                    if (secretRoomKeys > 0) {
                                        // If they do, remove one secret room key from their inventory.
                                        secretRoomKeys--;

                                        // Display to the user how many keys they have left.
                                        System.out.print("Secret Room Keys Remaining: " + secretRoomKeys + "\n\n");

                                        // Call the method to ask the user a bonus question.
                                        askBonusQuestion(bonusQuestions, totalScore);

                                        // Add 1 to the number of secret room keys the user has used up until that point
                                        // in the game.
                                        bonusQuestionsUsed++;
                                    } else {
                                        // Message to display if they don't have any secret room keys left.
                                        System.out.println("No secret room keys remaining.");
                                    }
                                    break;
                                case 'M':
                                    // Check if the user has a score multiplier available.
                                    if (scoreMultipliersBought > 0) {
                                        // If they do, set the current score multiplier to 2, for double points.
                                        scoreMultiplier = 2;

                                        // Add 1 to the number of score multipliers the user has used up until that
                                        // point.
                                        scoreMultipliersUsed++;

                                        // Remove 1 from the number of score multipliers the user has.
                                        scoreMultipliersBought--;

                                        // Display how many score multipliers the user has left.
                                        System.out
                                                .print("Score Multipliers Remaining: " + scoreMultipliersBought + "\n");

                                        // Display a message indicating the action was successful.
                                        System.out.println("Score multiplier activated!");
                                    } else {
                                        // Message to display if they don't have any score multipliers left.
                                        System.out.println("No score multipliers remaining.");
                                    }
                                    break;
                                case '#':
                                    // Call the method to display the help menu.
                                    displayHelpMenu();
                                    break;
                                case 'A':
                                case 'B':
                                case 'C':
                                case 'D':
                                    // Setting the flag variable to true allows the user to break out of the input
                                    // loop, as now their answer can be evaluated.
                                    check = true;
                                    break;
                                default:
                                    // If the user enters an invalid input, display an error message.
                                    System.out.println("Invalid input. Try again.");
                            }
                        } while (!check);

                        // Check if the user used a skip token.
                        if (input.toUpperCase().equals("S")) {
                            // If they did, wait for them to clear the screen when they're ready to move onto the next question.
                            pauseToContinue("\nOnce you're ready to continue, simply press Enter to proceed.", true);
                        } else {
                            // Store the time after the user answered the question as the end time.
                            long endTime = getTime();

                            // Take the difference between the end and start time, to find out how long the
                            // user took to answer the question.
                            long timeTaken = endTime - startTime;

                            // Chek if the user's answer is correct.
                            if (input.toUpperCase().charAt(0) == strQuestions[iRandQ][5].charAt(0)) {
                                // Clears the screen.
                                System.out.print("\033[H\033[2J\f");
                                System.out.flush();

                                // Message to display if the user's answer is correct.
                                System.out.println("\nNice work, you got that question right!");

                                // Calculate the points gained for the correct answer through the score method.
                                int pointsGained = doScores(iSection, timeTaken, scoreMultiplier, timeLimit, isTimed);

                                // Reset score multiplier if used.
                                scoreMultiplier = 1;

                                // Checks if the question was timed, and was answered within the time limit.
                                if (isTimed && timeTaken <= timeLimit) {

                                    // If so, display a message to the user, indicating they scored double points.
                                    System.out.println("You answered within the time limit and earned double points!");

                                } else if (isTimed && timeTaken >= timeLimit) {

                                    // Otherwise, display a message to the user, indicating they did not earn any
                                    // score for the question.
                                    System.out.println(
                                            "However, you didn't answer within the time limit and don't earn any points for this question!");

                                    // Brings the total points gained for the question back to 0.
                                    pointsGained = 0;
                                }

                                // Add the points gained to the total running score of the user.
                                totalScore += pointsGained;

                                // Message to display to the user, indicating how many points they have earned.
                                if (pointsGained != 1) {
                                    // If the points gained is not 1, display plural points.
                                    System.out.println("\nYou have gained " + pointsGained + " points.");
                                } else {
                                    // Otherwise, display singular points.
                                    System.out.println("\nYou have gained " + pointsGained + " point.");
                                }

                                // Increment the current streak.
                                currentStreak++;

                                // Update the greatest answer streak if the current streak is greater.
                                if (currentStreak > greatestAnswerStreak) {
                                    greatestAnswerStreak = currentStreak;
                                }
                            } else {
                                // In the case of an incorrect answer.

                                // Clears the screen.
                                System.out.print("\033[H\033[2J\f");
                                System.out.flush();

                                // Checks if the user has lives remaining.
                                if (lives > 0) {
                                    // Decrement the number of lives the user has left.
                                    lives--;

                                    // Display a message to the user, indicating they have lost a life.
                                    System.out.println(
                                            "\n\nYikes!!! You got that wrong and lost a life! You only have " + lives
                                                    + " remaining! Remember to use your hints and skip tokens wisely!");
                                    if (lives <= 0) {
                                        // If the user has no lives remaining after this action, display a message to
                                        // the user, indicating they have los the game.
                                        System.out.println(
                                                "\n\nWomp womp! You have lost all your lives and lost the game! Better luck next time!");

                                        // Setting this to 10 breaks out of the random question loop, so the user can
                                        // move onto seeing their final summary and achievements.
                                        iQCount = 10;
                                    }
                                }

                                // Resets their current answer streak back to 0.
                                currentStreak = 0;

                                // Sets the flag variable to false, as they have not answered all questions
                                // correctly anymore, so this achievement will not show up in their final sum
                                answeredAllCorrectly = false;
                            }

                            // Display the story message based on the current section and score.
                            displayStory(iSection, totalScore, beginnerStory, intermediateStory, expertStory);

                            // Ask the user if they would like an explanation to the question they just
                            // answered.
                            if (!end && getString(
                                    "\nIf you're uncertain, would you like to delve deeper into the question with an additional explanation? (Type 'y' for 'yes' to proceed with further clarification; other inputs will advance to the next question.)",
                                    true, -1, -1).equalsIgnoreCase("y")) {
                                // Run this code if the user wants an explanation.

                                // Clears the screen.
                                System.out.print("\033[H\033[2J\f");
                                System.out.flush();

                                // Display the explanation message for the question.
                                System.out.println(strQuestions[iRandQ][6]);

                                // Wait for the user to read over the explanation, before pressing Enter to
                                // continue.
                                pauseToContinue("\nOnce you've grasped the explanation, simply press Enter to proceed.",
                                        true);

                                // Clears the screen.
                                System.out.print("\033[H\033[2J\f");
                                System.out.flush();
                            } else {
                                // Clears the screen.
                                System.out.print("\033[H\033[2J\f");
                                System.out.flush();
                            }
                        }

                    } while (iQCount != 10 && !end);

                    // Clears the screen.
                    System.out.print("\033[H\033[2J\f");
                    System.out.flush();

                    // If the user has made it to section 2 with lives remaining, the flag variable
                    // for completing section 1 should be set to true.
                    if (iSection == 2 && lives > 0) {
                        section1Completed = true;
                    }

                    // If the user has made it to section 3 with lives remaining, the flag variable
                    // for completing section 2 should be set to true.
                    if (iSection == 3 && lives > 0) {
                        section2Completed = true;
                    }

                    // If the user has made it through all the questions in section 3 with lives
                    // remaining, the flag variable for completing section 3 should be set to true,
                    // and the flag variable for completing the game should be set to true.
                    if (iSection == 3 && iQCount == 10 && lives > 0) {
                        section3Completed = true;
                        end = true;
                    }

                    // If the user has no lives remaining, the flag variable for completing the game
                    // should be set to true.
                    if (lives <= 0) {
                        end = true;
                    }

                    // Reset the question count for the next section if they have lives to continue.
                    iQCount = 0;

                } while (iSection != 3 && !end
                        && getString(
                                "You have successfully navigated this room. Ready for your next adventure? (Press 'y' to continue)",
                                false,
                                -1, -1).equalsIgnoreCase("y"));
            } while (iQCount != 10 && !end);

            // Ask the user if they would like to see the endgame summary.
            if (getString("Curious about your performance? Access the endgame summary by pressing 'y'.", true, 1, 1)
                    .equalsIgnoreCase("y")) {
                // Clears the screen.
                System.out.print("\033[H\033[2J\f");
                System.out.flush();

                // Displays the endgame summary, through the final summary method.
                displayFinalSummary(username, lives, hintsUsed, skipTokensUsed, scoreMultipliersUsed,
                        bonusQuestionsUsed, greatestAnswerStreak, achievementCount, achievements, answeredAllCorrectly,
                        usedHint, boughtItem, section1Completed, section2Completed, section3Completed);
            }
        } while (getString("\nFeel like besting your performance? Enter 'y' to play again and strive for improvement.",
                true, -1, -1).equalsIgnoreCase("y"));

        // If the user decides to quit the game, display a message thanking them fo
        System.out.println("\nThanks for playing!");
    }

    // Method to display the game rules and introduction.
    private static void displayRules() {
        // Clears the screen.
        System.out.print("\033[H\033[2J\f");
        System.out.flush();

        // Title for the main program.
        System.out.println("ESCAPE ROOM COMPUTER SCIENCE QUIZ -- UNRAVEL THE MYSTERIES OF PROGRAMMING!\n\n");

        // Display game rules.
        System.out.println("  RULES:  \n");
        System.out.println(
                "1. You will journey through three sections, each with distinct difficulty levels and environments:\n");
        System.out.println("   Beginner Section --> The Cryptic Hallway: 1 point per correct answer");
        System.out.println("   Intermediate Section --> The Enchanted Library: 2 points per correct answer");
        System.out.println("   Expert Section --> The Forbidden Chamber: 3 points per correct answer\n");
        System.out.println(
                "2. You can unlock bonus questions worth 5 points each, by using secret room keys purchased at the midgame shop (maximum 5 keys).\n");
        System.out.println(
                "3. You have 5 lives. Losing all lives ends the game. Progress through the story depends on your answers.\n");
        System.out.println(
                "4. You start with 5 hints and can buy more at the shop. Achievements are earned by completing sections, buying no items, answering all questions correctly, unlocking all bonus rooms, or using no hints.\n");
        System.out
                .println("5. Option to receive immediate feedback after each question with explanations for correct answers.\n");
        System.out.println(
                "6. After the first 15 questions, visit the midgame shop to spend earned points on various items, including score multipliers, more hints, skip tokens, and secret room keys.\n");
        System.out.println(
                "7. Last 5 questions are time-limited --> 15 seconds time limit. Answer correctly within the time limit for double points. Failure to answer within the time limit awards no points.\n");
        System.out.println("8. Help Menu: Activate with '#' to display available commands throughout the game:\n");
        System.out.println("   A, B, C, D - Answer regular multiple choice questions\n");
        System.out.println("   H for hints (start with 5, can buy more from shop)\n");
        System.out.println("   ? for activating secret room key and access to bonus question\n");
        System.out.println("   S for using skip token\n");
        System.out.println("   M for using score multiplier (double points)\n");
        System.out.println("In Shop:");
        System.out.println("   1 for score multiplier powerup");
        System.out.println("   2 for skip token");
        System.out.println("   3 for secret room key");
        System.out.println("   4 for hint\n");

        // Wait for the user to read the rules, before pressing Enter to continue.
        pauseToContinue("Are you ready to embark on this programming adventure? Press Enter to begin.", true);
    }

    // Method to visit the mid-game shop.
    private static int[] visitMidGameShop(int totalScore, int hints, int skipTokens, int secretRoomKeys,
            int scoreMultipliersBought, boolean boughtItem) {
        // Display shop welcome message and available points
        System.out.println("Welcome to the Mid-Game Shop! Spend your points on useful items:");
        System.out.println("1. Score Multiplier (x2) - 2 points");
        System.out.println("2. Skip Token - 3 points");
        System.out.println("3. Secret Room Key - 2 points (Max 5)");
        System.out.println("4. Hint - 1 point");
        System.out.println("You have " + totalScore + " points.");

        // Flag variable to control shopping loop.
        boolean continueShopping = true;

        // Loop to handle shopping.
        while (continueShopping) {
            // Get the user's choice for the item to purchase.
            int choice = getInt("Enter the number of the item you want to purchase (0 to exit):", 0, 4);

            // Switch statement to handle each item purchase.
            switch (choice) {
                // Case for exiting the shop, as continue shopping is set to false.
                case 0:
                    continueShopping = false;
                    break;

                // Case for purchasing a score multiplier.
                case 1:
                    // Checks if the user has enough points to buy the score multiplier.
                    if (totalScore >= 2) {
                        // Add 1 to the number of score multipliers the user has.
                        scoreMultipliersBought++;

                        // Subtract the cost of the score multiplier from the total points.
                        totalScore -= 2;

                        // Set the flag variable to true to indicate that an item was purchased, meaning
                        // this achievement will not be shown in the final summary.
                        boughtItem = true;

                        // Display a message indicating the purchase was successful, and the updated
                        // total points.
                        System.out.println("Score multiplier purchased! Points left: " + totalScore);
                    } else {
                        // Message to display if the user does not have enough points to buy the score
                        // multiplier.
                        System.out.println("Not enough points.");
                    }
                    break;

                // Case for purchasing a skip token.
                case 2:
                    // Checks if the user has enough points to buy the skip token.
                    if (totalScore >= 3) {
                        // Add 1 to the number of skip tokens the user has.
                        skipTokens++;

                        // Subtract the cost of the skip token from the total points.
                        totalScore -= 3;

                        // Set the flag variable to true to indicate that an item was purchased, meaning
                        // this achievement will not be shown in the final summary.
                        boughtItem = true;

                        // Display a message indicating the purchase was successful, and the updated
                        // total points.
                        System.out.println("Skip token purchased! Points left: " + totalScore);
                    } else {
                        // Display a message indicating that the user does not have enough points to buy
                        // the skip token.
                        System.out.println("Not enough points.");
                    }
                    break;

                // Case for purchasing a secret room key.
                case 3:
                    // Checks if the user has enough points to buy the secret room key.
                    if (totalScore >= 2 && secretRoomKeys < 5) {
                        // Add 1 to the number of secret room keys the user has.
                        secretRoomKeys++;

                        // Subtract the cost of the secret room key from the total points.
                        totalScore -= 2;

                        // Set the flag variable to true to indicate that an item was purchased, meaning
                        // this achievement will not be shown in the final summary.
                        boughtItem = true;

                        // Display a message indicating the purchase was successful, and the updated
                        // total points.
                        System.out.println("Secret room key purchased! Points left: " + totalScore);
                    } else {
                        // Display a message indicating that the user does not have enough points to buy
                        // the secret room key.
                        System.out.println("Not enough points or maximum keys reached.");
                    }
                    break;

                // Case for purchasing a hint.
                case 4:
                    // Checks if the user has enough points to buy a hint.
                    if (totalScore >= 1) {
                        // Add 1 to the number of hints the user has.
                        hints++;

                        // Subtract the cost of the hint from the total points.
                        totalScore -= 1;

                        // Set the flag variable to true to indicate that an item was purchased, meaning
                        // this achievement will not be shown in the final summary.
                        boughtItem = true;

                        // Display a message indicating the purchase was successful, and the updated
                        // total points.
                        System.out.println("Hint purchased! Points left: " + totalScore);
                    } else {
                        // Display a message indicating that the user does not have enough points to buy
                        // a hint.
                        System.out.println("Not enough points.");
                    }
                    break;
                default:
                    // Display an error message for invalid inputs.
                    System.out.println("Invalid choice.");
            }
        }

        // Clears the screen.
        System.out.print("\033[H\033[2J\f");
        System.out.flush();

        // Return the updated values after shopping as an integer array, with boughtItem
        // being converted into an integer for the time being, until it is converted
        // back into a boolean in the game loop.
        return new int[] { totalScore, hints, skipTokens, secretRoomKeys, scoreMultipliersBought, boughtItem ? 1 : 0 };
    }

    // Method to ask a bonus question, similar to how random questions are asked in
    // the main game loop.
    private static int askBonusQuestion(String[][] bonusQuestions, int totalScore) {
        // Select a random bonus question.
        int iRandBonusQ = (int) (Math.random() * bonusQuestions.length);

        // Ensure the selected question has not been used.
        while (bonusQuestions[iRandBonusQ][0] == "") {
            iRandBonusQ = (iRandBonusQ + 1) % bonusQuestions.length;
        }

        // Display the bonus question.
        System.out.println("Bonus Question: " + bonusQuestions[iRandBonusQ][0]);

        // Get the user's answer.
        String input = getString("Enter your answer:", false, 1, 100);

        // Check if the answer is correct.
        if (input.equals(bonusQuestions[iRandBonusQ][1])) {
            // Display a message indicating the user's answer is correct.
            System.out.println("Correct! " + bonusQuestions[iRandBonusQ][2]);

            // Increase the running total points by 5, as each bonus question is worth 5
            // points.
            totalScore += 5;
        } else {
            // Display a message indicating the user's answer is incorrect.
            System.out.println("Incorrect! " + bonusQuestions[iRandBonusQ][2]);
        }
        // Mark the question as used.
        bonusQuestions[iRandBonusQ][0] = "";

        // Return the updated total score.
        return totalScore;
    }

    // Method to calculate the score.
    private static int doScores(int section, long timeTaken, int scoreMultiplier, long timeLimit, boolean isTimed) {
        // Initialize the score.
        int score = 0;

        // Add points based on the section.
        if (section == 1) {
            // 1 point for section 1.
            score++;
        } else if (section == 2) {
            // 2 points for section 2.
            score += 2;
        } else {
            // 3 points for section 3.
            score += 3;
        }

        // Double the score if the question is timed and answered within the time limit.
        if (isTimed && timeTaken <= timeLimit) {
            score *= 2;
        }

        // Apply the current score multiplier (1 by default, 2 if a score multiplier
        // item was bought and used).
        score *= scoreMultiplier;

        // Return the total amount of points gained.
        return score;
    }

    // Method to display the final summary.
    private static void displayFinalSummary(String username, int lives, int hintsUsed, int skipTokensUsed,
            int scoreMultipliersUsed, int bonusQuestionsUsed, int greatestAnswerStreak, int achievementCount,
            String[] achievements, boolean answeredAllCorrectly, boolean usedHint, boolean boughtItem,
            boolean section1Completed, boolean section2Completed, boolean section3Completed) {

        // Check for various achievements using the flag variables that have been
        // checked throughout the entire game thus far.
        if (answeredAllCorrectly) {
            // Add the achievement to the list of achievements if the user has answered all
            // questions correctly.
            achievements[achievementCount++] = "Answered All Questions Correctly";
        }
        if (!usedHint) {
            // Add the achievement to the list of achievements if the user has not used a
            // hint.
            achievements[achievementCount++] = "Used No Hints";
        }
        if (!boughtItem) {
            // Add the achievement to the list of achievements if the user has not bought
            // any items.
            achievements[achievementCount++] = "Bought No Items";
        }
        if (bonusQuestionsUsed == 5) {
            // Add the achievement to the list of achievements if the user has unlocked all
            // bonus rooms.
            achievements[achievementCount++] = "Unlocked All Bonus Rooms";
        }
        if (section1Completed) {
            // Add the achievement to the list of achievements if the user has completed
            // section 1.
            achievements[achievementCount++] = "Completed Section 1 --> The Cryptic Hallway";
        }
        if (section2Completed) {
            // Add the achievement to the list of achievements if the user has completed
            // section 2.
            achievements[achievementCount++] = "Completed Section 2 --> The Enchanted Library";
        }
        if (section3Completed) {
            // Add the achievement to the list of achievements if the user has completed
            // section 3.
            achievements[achievementCount++] = "Completed Section 3 --> The Forbidden Chamber";
        }

        // Display the final summary.
        System.out.println("\nUsername: " + username);
        System.out.println("Lives remaining: " + lives);
        System.out.println("Hints used: " + hintsUsed);
        System.out.println("Skip tokens used: " + skipTokensUsed);
        System.out.println("Score multipliers used: " + scoreMultipliersUsed);
        System.out.println("Secret rooms unlocked: " + bonusQuestionsUsed + "/5");
        System.out.println("Greatest answer streak: " + greatestAnswerStreak);
        System.out.println(
                "Achievements unlocked: " + achievementCount + "/7 (it is possible to get 6 in one playthrough)");

        // Display the list of achievements, by looping through the achievements array,
        // up until the number of achievements the user has earned (achievementCount).
        for (int i = 0; i < achievementCount; i++) {
            System.out.println("- " + achievements[i]);
        }
    }

    // Method to display the help meny.
    private static void displayHelpMenu() {
        // Display the help menu.
        System.out.println("\nHelp Menu:");
        System.out.println("A, B, C, D - Answer regular multiple choice questions");
        System.out.println("H for hints (start with 5, can buy more from shop)");
        System.out.println("? for activating secret room key and access to bonus question");
        System.out.println("S for use skip token");
        System.out.println("M for use score multiplier (double points)");
        System.out.println("In Shop:");
        System.out.println("1 for score multiplier powerup");
        System.out.println("2 for skip token");
        System.out.println("3 for secret room key");
        System.out.println("4 for hint");
    }

    // Method to display story message based on the section and score.
    private static void displayStory(int section, int score, String[] beginnerStory, String[] intermediateStory,
            String[] expertStory) {
        // Initialize the story message.
        String message = "";

        // Switch statement to select the appropriate story message based on section and score.
        switch (section) {
            case 1:
                if (score == 1) {
                    message = beginnerStory[0];
                } else if (score == 2) {
                    message = beginnerStory[1];
                } else if (score == 3) {
                    message = beginnerStory[2];
                } else if (score == 4) {
                    message = beginnerStory[3];
                } else if (score == 5) {
                    message = beginnerStory[4];
                } else if (score == 6) {
                    message = beginnerStory[5];
                } else if (score == 7) {
                    message = beginnerStory[6];
                } else if (score == 8) {
                    message = beginnerStory[7];
                } else if (score == 9) {
                    message = beginnerStory[8];
                } else if (score == 10) {
                    message = beginnerStory[9];
                }
                break;
            case 2:
                // Basic adjustment for shop spending at the halfway mark in section 2.
                int adjustedScore = score - 10;

                if (2 >= adjustedScore) {
                    message = intermediateStory[0];
                } else if (4 >= adjustedScore) {
                    message = intermediateStory[1];
                } else if (6 >= adjustedScore) {
                    message = intermediateStory[2];
                } else if (8 >= adjustedScore) {
                    message = intermediateStory[3];
                } else if (10 >= adjustedScore) {
                    message = intermediateStory[4];
                } else if (12 >= score) {
                    message = intermediateStory[5];
                } else if (14 >= score) {
                    message = intermediateStory[6];
                } else if (16 >= score) {
                    message = intermediateStory[7];
                } else if (18 >= score) {
                    message = intermediateStory[8];
                } else if (score > 18) {
                    message = intermediateStory[9];
                }
                break;
            case 3:
                // Score ranges compensate for shop spending and using shop items to earn even
                // more points.
                if (25 >= score) {
                    message = expertStory[0];
                } else if (30 >= score) {
                    message = expertStory[1];
                } else if (35 >= score) {
                    message = expertStory[2];
                } else if (40 >= score) {
                    message = expertStory[3];
                } else if (45 >= score) {
                    message = expertStory[4];
                } else if (50 >= score) {
                    message = expertStory[5];
                } else if (55 >= score) {
                    message = expertStory[6];
                } else if (60 >= score) {
                    message = expertStory[7];
                } else if (65 >= score) {
                    message = expertStory[8];
                } else if (score > 65) {
                    message = expertStory[9];
                }
                break;
        }
        // Display the story message.
        System.out.println("\n" + message);
    }

    // This routine is here to initialize the 2 dimensional array to contain
    // whatever initial starting string is sent as the second parameter.
    private static void initialize(String grid[][], String strStart) {
        // Loop through the number of arrays (rows) there are.
        for (int i = 0; i < grid.length; i++) {
            // Loop through the number of elements there are in each array (columns).
            for (int j = 0; j < grid[0].length; j++) {
                // Put the start character in the current spot in the 2D array.
                grid[i][j] = strStart;
            }
        }
    }

    // Loads the array sent in with the first topic content.
    private static void fillArrayTopic1(String strQs[][]) {
        // This is the question.
        strQs[0][0] = "Which format specifier should be used to print an integer in Java?";

        // This is Option A).
        strQs[0][1] = "%s";

        // This is Option B).
        strQs[0][2] = "%f";

        // This is Option C).
        strQs[0][3] = "%c";

        // This is Option D).
        strQs[0][4] = "%d";

        // This is the answer to the question.
        strQs[0][5] = "D";

        // This is the explanation to the question.
        strQs[0][6] = "Answer: D) -- %d is used to format integers in Java. A) %s is used for strings. C) %c is used for characters. B) %f is used for floating-point numbers.";

        // This is the hint for the question.
        strQs[0][7] = "Hint: Think about how you would format whole numbers in Java.";

        // This order applies for all the questions below, including the other 2 fillArrayTopic methods for the intermediate and advanced sections.

        strQs[1][0] = "What is the result of 5 % 2 in Java?";
        strQs[1][1] = "0";
        strQs[1][2] = "1";
        strQs[1][3] = "2";
        strQs[1][4] = "3";
        strQs[1][5] = "B";
        strQs[1][6] = "Answer: B) -- The modulus operator % returns the remainder of the division. 5 divided by 2 is 2 with a remainder of 1. A) 0 is incorrect because 5 is not evenly divisible by 2. C) 2 is the quotient, not the remainder. D) 3 is not related to this operation.";
        strQs[1][7] = "Hint: Modulus returns the remainder.";

        strQs[2][0] = "What does x += 2 do to the variable x?";
        strQs[2][1] = "Adds 2 to x";
        strQs[2][2] = "Subtracts 2 from x";
        strQs[2][3] = "Multiplies x by 2";
        strQs[2][4] = "Divides x by 2";
        strQs[2][5] = "A";
        strQs[2][6] = "Answer: A) -- The += operator adds the value on the right to the variable on the left. B) Subtracts 2 from x is incorrect, it describes x -= 2. C) Multiplies x by 2 is incorrect, it describes x *= 2. D) Divides x by 2 is incorrect, it describes x /= 2.";
        strQs[2][7] = "Hint: += is an addition assignment operator.";

        strQs[3][0] = "Which of these is a primitive data type in Java?";
        strQs[3][1] = "String";
        strQs[3][2] = "Class";
        strQs[3][3] = "boolean";
        strQs[3][4] = "Array";
        strQs[3][5] = "C";
        strQs[3][6] = "Answer: C) -- boolean is a primitive data type. A) String is a class, not a primitive data type. B) Classes are a special user-defined datatype, making them non-primitive. D) Array is not a primitive data type, it's a data structure that stores a collection of elements of the same type.";
        strQs[3][7] = "Hint: This specific primitive type has one of two possible values.";

        strQs[4][0] = "What is the output of System.out.println(5 + 3); in Java?";
        strQs[4][1] = "53";
        strQs[4][2] = "8";
        strQs[4][3] = "5 + 3";
        strQs[4][4] = "35";
        strQs[4][5] = "B";
        strQs[4][6] = "Answer: B) -- 5 + 3 is an arithmetic addition, resulting in 8. A) 53 is the result of string concatenation, not arithmetic addition (\"\" + 5 + 3). C) 5 + 3 is incorrect because it would only print that if enclosed in double quotes. D) 35 is incorrect, it would require \"\" + 3 + 5.";
        strQs[4][7] = "Hint: It's arithmetic addition, not string concatenation.";

        strQs[5][0] = "What is the purpose of the // symbol in Java?";
        strQs[5][1] = "Start a multi-line comment";
        strQs[5][2] = "End a single-line comment";
        strQs[5][3] = "End a multi-line comment";
        strQs[5][4] = "Start a single-line comment";
        strQs[5][5] = "D";
        strQs[5][6] = "Answer: D) -- // is used to start a single-line comment in Java. A) is incorrect because /* is used to start a multi-line comment. C) is incorrect because */ is used to end a multi-line comment. B) is not applicable.";
        strQs[5][7] = "Hint: Single-line comments start with //.";

        strQs[6][0] = "What is the result of the expression 10 / 4 in Java?";
        strQs[6][1] = "2.5";
        strQs[6][2] = "2";
        strQs[6][3] = "3";
        strQs[6][4] = "2.0";
        strQs[6][5] = "B";
        strQs[6][6] = "Answer: B) -- Dividing two integers results in integer division, truncating the decimal. A) 2.5 would be the result if at least one operand was a floating-point number. C) 3 is incorrect as it is the wrong quotient. D) 2.0 would require at least one operand to be a floating-point number.";
        strQs[6][7] = "Hint: Integer division truncates the decimal.";

        strQs[7][0] = "Which of the following is a valid way to increment the variable x by 1 in Java?";
        strQs[7][1] = "x = x + 1;";
        strQs[7][2] = "x++;";
        strQs[7][3] = "x += 1;";
        strQs[7][4] = "All of the above";
        strQs[7][5] = "D";
        strQs[7][6] = "Answer: D) -- All options A), B), and C) are valid ways to increment a variable by 1 in Java.";
        strQs[7][7] = "Hint: Increment can be done in multiple ways.";

        strQs[8][0] = "What does System.out.printf(\"%d\", 100); output?";
        strQs[8][1] = "100";
        strQs[8][2] = "%d";
        strQs[8][3] = "\"100\"";
        strQs[8][4] = "d100";
        strQs[8][5] = "A";
        strQs[8][6] = "Answer: A) -- %d is the format specifier for integers, printing 100. B) %d would print as such if it was enclosed in quotes. C) \"100\" is incorrect because it would print only if enclosed in quotes. D) d100 is incorrect as %d correctly formats integers.";
        strQs[8][7] = "Hint: %d is for integers.";

        strQs[9][0] = "What will the following code print? System.out.println('A' + 1);";
        strQs[9][1] = "A1";
        strQs[9][2] = "B";
        strQs[9][3] = "66";
        strQs[9][4] = "1A";
        strQs[9][5] = "C";
        strQs[9][6] = "Answer: C) --  is correct because 'A' is 65 in ASCII, and adding 1 results in 66. A) A1 is incorrect; it would need string concatenation. B) B would be the result if 'A' was incremented, then explicitly casted back to a character variable. D) 1A is incorrect as it would need string concatenation.";
        strQs[9][7] = "Hint: ASCII value of 'A' is 65.";

        strQs[10][0] = "Which of these is a floating-point data type in Java?";
        strQs[10][1] = "boolean";
        strQs[10][2] = "int";
        strQs[10][3] = "float";
        strQs[10][4] = "char";
        strQs[10][5] = "C";
        strQs[10][6] = "Answer: C) -- float is a floating-point data type. A) boolean represents true or false. B) int is an integer data type. D) char represents characters.";
        strQs[10][7] = "Hint: Floating-point types are for numbers with decimals.";

        strQs[11][0] = "What does x -= 3 do to the variable x?";
        strQs[11][1] = "Adds 3 to x";
        strQs[11][2] = "Subtracts 3 from x";
        strQs[11][3] = "Multiplies x by 3";
        strQs[11][4] = "Divides x by 3";
        strQs[11][5] = "B";
        strQs[11][6] = "Answer: B) -- The -= operator subtracts the right-hand value from the variable. A) Adding 3 is incorrect; it describes x += 3. C) Multiplying x by 3 is incorrect; it describes x *= 3. D) Dividing x by 3 is incorrect; it describes x /= 3.";
        strQs[11][7] = "Hint: -= is a subtraction assignment operator.";

        strQs[12][0] = "Which of these keywords can used to declare a variable in Java?";
        strQs[12][1] = "var";
        strQs[12][2] = "create";
        strQs[12][3] = "define";
        strQs[12][4] = "int";
        strQs[12][5] = "D";
        strQs[12][6] = "Answer: D) -- int is used to declare integer variables. A) var is used in some languages, but not traditionally in Java. C) define is not a keyword in Java. B) create is not a keyword in Java.";
        strQs[12][7] = "Hint: Think about how you would declare an integer variable in Java.";

        strQs[13][0] = "What will the following code print? System.out.println(4 + \"4\");";
        strQs[13][1] = "44";
        strQs[13][2] = "8";
        strQs[13][3] = "4 + 4";
        strQs[13][4] = "Error";
        strQs[13][5] = "A";
        strQs[13][6] = "Answer: A) -- 4 + \"4\" results in string concatenation, not arithmetic addition. B) 8 is incorrect as it would result from arithmetic addition, not concatenation. C) 4 + 4 is incorrect as it would only print if enclosed in quotes. D) Error is incorrect as the code will compile and run correctly.";
        strQs[13][7] = "Hint: Concatenation with a string results in a string.";

        strQs[14][0] = "What escape sequence is used to insert a new line in a string in Java?";
        strQs[14][1] = "\\t";
        strQs[14][2] = "\\b";
        strQs[14][3] = "\\n";
        strQs[14][4] = "\\r";
        strQs[14][5] = "C";
        strQs[14][6] = "Answer: C) -- \\n inserts a new line in a string. A) \\t inserts a tab. B) \\b inserts a backspace. D) \\r inserts a carriage return.";
        strQs[14][7] = "Hint: New line escape sequence.";

        strQs[15][0] = "Which format specifier should be used to print a floating-point number in Java?";
        strQs[15][1] = "%s";
        strQs[15][2] = "%d";
        strQs[15][3] = "%c";
        strQs[15][4] = "%f";
        strQs[15][5] = "D";
        strQs[15][6] = "Answer: D) -- %f is used to format floating-point numbers. A) %s is used for strings. B) %d is used for integers. C) %c is used for characters.";
        strQs[15][7] = "Hint: Think about formatting numbers with decimals.";

        strQs[16][0] = "Which of the following is a valid multi-line comment in Java?";
        strQs[16][1] = "// This is a multi-line comment";
        strQs[16][2] = "* This is a multi-line comment *";
        strQs[16][3] = "/* This is a multi-line comment */";
        strQs[16][4] = "/ This is a multi-line comment /";
        strQs[16][5] = "C";
        strQs[16][6] = "Answer: C) -- /* ... */ is used to start and end a multi-line comment. A) is a single-line comment. B) and D) are not valid comment syntax in Java.";
        strQs[16][7] = "Hint: Multi-line comments start with /* and end with */.";

        strQs[17][0] = "What will the following code print? System.out.println(2 * 3 + \"5\");";
        strQs[17][1] = "65";
        strQs[17][2] = "35";
        strQs[17][3] = "6";
        strQs[17][4] = "11";
        strQs[17][5] = "A";
        strQs[17][6] = "Answer: A) -- 2 * 3 results in 6, and \"6\" + \"5\" results in 65. B) 35 is incorrect as the 2 out front is ignored. C) 6 is the result of 2 * 3, but it misses the concatenation. D) 11 is incorrect as \"5\" is enclosed in double quotes, which would result in string concatenation, not numeric addition.";
        strQs[17][7] = "Hint: Multiplication followed by string concatenation.";

        strQs[18][0] = "What does the System.out.print() method do?";
        strQs[18][1] = "Prints text to the console without a newline, by default.";
        strQs[18][2] = "Prints text to the console with a newline, by default.";
        strQs[18][3] = "Prints text to a file.";
        strQs[18][4] = "Returns a string value.";
        strQs[18][5] = "A";
        strQs[18][6] = "Answer: A) -- System.out.print() outputs text to the console without appending a newline character, by default. B) is incorrect because it describes System.out.println(). C) is incorrect because it does not print to a file. D) is incorrect because it does not return a value.";
        strQs[18][7] = "Hint: Prints text without a newline.";

        strQs[19][0] = "What is the correct way to declare a variable in Java?";
        strQs[19][1] = "int x;";
        strQs[19][2] = "int x";
        strQs[19][3] = "x = 10;";
        strQs[19][4] = "declare x;";
        strQs[19][5] = "A";
        strQs[19][6] = "Answer: A) -- It declares an integer variable x. B) is incorrect due to the missing semicolon. C) is incorrect as it assigns a value but doesn't declare the variable type. D) is incorrect because declare is not a keyword in Java.";
        strQs[19][7] = "Hint: Correct variable declaration syntax: type, at least one space, name, semicolon.";
    }

    // Loads the array sent in with the second topic content.
    private static void fillArrayTopic2(String strQs[][]) {
        strQs[0][0] = "Which code snippet shows using the Scanner class to read an integer input from the user?";
        strQs[0][1] = "Scanner input = new Scanner(System.in); int num = input.nextLine();";
        strQs[0][2] = "Scanner input = new Scanner(System.in); int num = input.nextInt();";
        strQs[0][3] = "Scanner input = new Scanner(System.in); String num = input.next();";
        strQs[0][4] = "Scanner input = new Scanner(System.in); double num = input.nextDouble();";
        strQs[0][5] = "B";
        strQs[0][6] = "Answer: B) -- nextInt() is used to read integer input. A) nextLine() is used for string input. C) next() is used for string input. D) nextDouble() is used for double input.";
        strQs[0][7] = "Hint: Look for a method that specifically reads integers.";

        strQs[1][0] = "Given String str = \"Magic\";, what is the output of str.substring(1, 3);?";
        strQs[1][1] = "Ma";
        strQs[1][2] = "ag";
        strQs[1][3] = "Mag";
        strQs[1][4] = "gi";
        strQs[1][5] = "B";
        strQs[1][6] = "Answer: B) -- substring(1, 3) extracts characters from index 1 to 2 (inclusive to exclusive). A) Would be correct for substring(0, 2). C) Would be correct for substring(0, 3). D) Would be correct for substring(2, 4).";
        strQs[1][7] = "Hint: Pay attention to the indices; they start from 0 and the substring method is from inclusive to exclusive.";

        strQs[2][0] = "What will the following code print? System.out.println(Math.ceil(4.2));";
        strQs[2][1] = "4";
        strQs[2][2] = "4.0";
        strQs[2][3] = "5.0";
        strQs[2][4] = "4.2";
        strQs[2][5] = "C";
        strQs[2][6] = "Answer: C) -- ceil() returns a floating-point value representing the nearest whole number that is greater than or equal to the value passed to it, resulting in 5.0. A) and B) represent the floor value. D) is the original number.";
        strQs[2][7] = "Hint: Consider how ceil() rounds numbers.";

        strQs[3][0] = "Which method is used to get the length of a string in Java?";
        strQs[3][1] = "length()";
        strQs[3][2] = "size()";
        strQs[3][3] = "count()";
        strQs[3][4] = "getLength()";
        strQs[3][5] = "A";
        strQs[3][6] = "Answer: A) -- length() is the method to get the string length. B), C), and D) are not methods for string length in Java.";
        strQs[3][7] = "Hint: Think about what you call to determine the length of a string. It’s what it sounds like.";

        strQs[4][0] = "What is the result of Math.max(10, 20);?";
        strQs[4][1] = "10";
        strQs[4][2] = "20";
        strQs[4][3] = "15";
        strQs[4][4] = "0";
        strQs[4][5] = "B";
        strQs[4][6] = "Answer: B) -- Math.max() returns the larger of the two numbers. A), C), and D) are incorrect.";
        strQs[4][7] = "Hint: Consider which of the two numbers is larger.";

        strQs[5][0] = "What will the following code print? System.out.println(Math.floor(7.8));";
        strQs[5][1] = "7.0";
        strQs[5][2] = "8";
        strQs[5][3] = "7";
        strQs[5][4] = "8.0";
        strQs[5][5] = "A";
        strQs[5][6] = "Answer: A) -- floor() returns a floating-point value representing the nearest whole number that is less than or equal to the value passed to it, resulting in 7.0. B) and D) represent ceiling values. C) is an integer, not a floating-point value.";
        strQs[5][7] = "Hint: Think about how floor() rounds numbers down.";

        strQs[6][0] = "Which method is used to convert a string to uppercase in Java?";
        strQs[6][1] = "toUpper()";
        strQs[6][2] = "toUpperCase()";
        strQs[6][3] = "convertUpper()";
        strQs[6][4] = "uppercase()";
        strQs[6][5] = "B";
        strQs[6][6] = "Answer: B) -- toUpperCase() converts a string to uppercase. A), C), and D) are not valid methods for this operation.";
        strQs[6][7] = "Hint: Look for the correct method used in Java, that specifically converts characters to uppercase.";

        strQs[7][0] = "What is the result of Math.min(15, 30);?";
        strQs[7][1] = "15";
        strQs[7][2] = "30";
        strQs[7][3] = "45";
        strQs[7][4] = "0";
        strQs[7][5] = "A";
        strQs[7][6] = "Answer: A) -- Math.min() returns the smaller of the two numbers. B), C), and D) are incorrect.";
        strQs[7][7] = "Hint: Consider which of the two numbers is smaller.";

        strQs[8][0] = "Which control structure is used to repeat a block of code a specific number of times?";
        strQs[8][1] = "if-else";
        strQs[8][2] = "while loop";
        strQs[8][3] = "switch";
        strQs[8][4] = "for loop";
        strQs[8][5] = "D";
        strQs[8][6] = "Answer: D) -- a for loop is used for counted repetition. A) if-else is a conditional structure. B) is a conditional loop, but not ideal for counted repetition. C) is a conditional structure not used for repetition.";
        strQs[8][7] = "Hint: Think about which loop specifically controls the number of iterations with a variable.";

        strQs[9][0] = "What is the output of System.out.println(\"hello\".charAt(1));?";
        strQs[9][1] = "h";
        strQs[9][2] = "e";
        strQs[9][3] = "l";
        strQs[9][4] = "o";
        strQs[9][5] = "B";
        strQs[9][6] = "Answer: B) -- charAt(1) returns the character at index 1. A), C), and D) are characters at other indices (0, 2, and 4 respectively).";
        strQs[9][7] = "Hint: Remember that indices start from 0 and go up from there.";

        strQs[10][0] = "Which method is used to read a whole line of text from the user in Java?";
        strQs[10][1] = "next()";
        strQs[10][2] = "nextInt()";
        strQs[10][3] = "nextLine()";
        strQs[10][4] = "nextDouble()";
        strQs[10][5] = "C";
        strQs[10][6] = "Answer: C) -- nextLine() reads a whole line of text. A) reads the next token. B) reads an integer. D) reads a double.";
        strQs[10][7] = "Hint: Look for a method that specifically reads entire lines of input, rather than just the next token.";

        strQs[11][0] = "Which of these code snippets shows a correct for loop declaration in Java?";
        strQs[11][1] = "for (int i = 0; i < 10; i++)";
        strQs[11][2] = "for int i = 0; i < 10; i++";
        strQs[11][3] = "for (int i = 0, i < 10, i++)";
        strQs[11][4] = "for (i = 0; i < 10; i++)";
        strQs[11][5] = "A";
        strQs[11][6] = "Answer: A) -- it demonstrates the proper syntax for initializing, condition, and iteration in a for loop. B) and C) contain syntax errors, and D) misses the type declaration for the loop variable.";
        strQs[11][7] = "Hint: Pay close attention to the syntax of a for loop in Java. Look for proper use of brackets and semicolons.";

        strQs[12][0] = "Given String str = \"hello\";, what is the result of str.toUpperCase();?";
        strQs[12][1] = "HELLO";
        strQs[12][2] = "hello";
        strQs[12][3] = "Hello";
        strQs[12][4] = "hELLO";
        strQs[12][5] = "A";
        strQs[12][6] = "Answer: A) -- toUpperCase() converts all characters to uppercase. B), C), and D) do not represent the complete uppercase conversion.";
        strQs[12][7] = "Hint: Think about how the method changes the case of each character.";

        strQs[13][0] = "Which code snippet shows a correct if-else structure in Java?";
        strQs[13][1] = "if (x > 0) { } else { }";
        strQs[13][2] = "if (x > 0) else { }";
        strQs[13][3] = "if x > 0 { } else { }";
        strQs[13][4] = "if (x > 0) { } elseif { }";
        strQs[13][5] = "A";
        strQs[13][6] = "Answer: A) -- it is the proper syntax for if-else. B) lacks the proper structure. C) lacks parentheses. D) uses an incorrect keyword.";
        strQs[13][7] = "Hint: Consider the correct syntax for an if-else statement in Java. Look carefully for proper structure and use of parentheses.";

        strQs[14][0] = "What will the following code print? System.out.println(\"java\".substring(1));";
        strQs[14][1] = "java";
        strQs[14][2] = "ava";
        strQs[14][3] = "j";
        strQs[14][4] = "va";
        strQs[14][5] = "B";
        strQs[14][6] = "Answer: B) -- substring(1) returns the string from index 1 to the end. A) includes the entire string. C) is the first character. D) would need substring(2).";
        strQs[14][7] = "Hint: Pay attention to the position in the string from which the substring starts (remember indices start from 0), and where it will end.";

        strQs[15][0] = "Which method is used to compare two strings for equality, case-sensitive, in Java?";
        strQs[15][1] = "equalsIgnoreCase()";
        strQs[15][2] = "compare()";
        strQs[15][3] = "equals()";
        strQs[15][4] = "isEqual()";
        strQs[15][5] = "C";
        strQs[15][6] = "Answer: C) -- equals() compares two strings for equality, and it’s case sensitive. A) ignores case in comparison. B) is not a valid method for strings. D) is not a method in Java.";
        strQs[15][7] = "Hint: Look for the method that directly compares two strings for equality and doesn’t ignore the case.";

        strQs[16][0] = "What is the purpose of the default case in a switch statement?";
        strQs[16][1] = "It specifies a case that is always executed regardless of the switch expression.";
        strQs[16][2] = "It defines a case that is executed if the switch expression evaluates to null.";
        strQs[16][3] = "It specifies the initial value of the switch expression.";
        strQs[16][4] = "It defines a case that is executed when none of the other cases match the switch expression.";
        strQs[16][5] = "D";
        strQs[16][6] = "Answer: D) -- the default case is executed when none of the other cases match the switch expression. Options A), B), and C) describe incorrect functionalities not associated with the default case.";
        strQs[16][7] = "Hint: Think about what happens when none of the other cases in a switch statement are matched.";

        strQs[17][0] = "What will the following code print? System.out.println(\"Java\".toLowerCase());";
        strQs[17][1] = "JaVa";
        strQs[17][2] = "Java";
        strQs[17][3] = "JAVA";
        strQs[17][4] = "java";
        strQs[17][5] = "D";
        strQs[17][6] = "Answer: D) -- toLowerCase() converts all characters to lowercase. A), B), and C) do not represent the complete lowercase conversion.";
        strQs[17][7] = "Hint: Consider what the method does to the case of each character in the string.";

        strQs[18][0] = "Which of the following statements is true about the do-while loop in Java?";
        strQs[18][1] = "It executes the code block twice before checking the condition.";
        strQs[18][2] = "It checks the condition before executing the code block.";
        strQs[18][3] = "It is similar to the while loop structure but ensures the code block is executed at least once.";
        strQs[18][4] = "It is used for infinite loops only.";
        strQs[18][5] = "C";
        strQs[18][6] = "Answer: C) -- the do-while loop in Java is similar to the while loop but guarantees that the code block is executed at least once, regardless of whether the condition is initially true or false. Options A), B), and D) describe functionalities not associated with the do-while loop.";
        strQs[18][7] = "Hint: Think about the fundamental difference between a do-while loop and a regular while loop, and how their structure changes the way they function.";

        strQs[19][0] = "What is the output of System.out.println(Math.round(4.6));?";
        strQs[19][1] = "4";
        strQs[19][2] = "4.0";
        strQs[19][3] = "4.6";
        strQs[19][4] = "5.0";
        strQs[19][5] = "D";
        strQs[19][6] = "Answer: D) -- Math.round returns a floating-point value representing the nearest whole number to the value passed to it, resulting in 5.0. Options A), B), and C) are incorrect as they do not represent the correct rounded value.";
        strQs[19][7] = "Hint: Think about how you would round 4.6 to the nearest whole number.";
    }

    // Loads the array sent in with the third topic content.
    private static void fillArrayTopic3(String strQs[][]) {
        strQs[0][0] = "What will be the output of the following code? String s = \"Hello\"; System.out.println(s.toUpperCase().indexOf('e', 2));";
        strQs[0][1] = "1";
        strQs[0][2] = "2";
        strQs[0][3] = "3";
        strQs[0][4] = "-1";
        strQs[0][5] = "D";
        strQs[0][6] = "Answer: D) -- toUpperCase() converts the string to uppercase (\"HELLO\"), and then indexOf('e', 2) searches for the character 'e' starting from index 2. Since there is no 'e' after index 2 in \"HELLO\", the method returns -1. The other options are incorrect because they do not reflect the correct behavior of the code.";
        strQs[0][7] = "Hint: The indexOf method returns the position of a character in a string, and can be passed an integer value representing the index to start the search from. Remember, indices start at 0.";

        strQs[1][0] = "Which of these code snippets successfully creates an array of integers with 10 rows and 5 columns?";
        strQs[1][1] = "int[][] array = new int[5][10];";
        strQs[1][2] = "int[][] array = new int[10][5];";
        strQs[1][3] = "int[][] array = new int[9][4];";
        strQs[1][4] = "int[][] array = new int[10][10];";
        strQs[1][5] = "B";
        strQs[1][6] = "Answer: B) -- it creates a two-dimensional array with 10 rows and 5 columns. Option A) creates a 5x10 array, C) creates a 9x4 array, and D) creates a 10x10 array, which is not what the question asked for.";
        strQs[1][7] = "Hint: When declaring a 2D array, the first dimension represents rows, and the second dimension represents columns.";

        strQs[2][0] = "What is the output of the following code? int[] numbers = {1, 2, 3, 4, 5}; System.out.println(numbers[numbers.length - 1]);";
        strQs[2][1] = "1";
        strQs[2][2] = "4";
        strQs[2][3] = "5";
        strQs[2][4] = "Error";
        strQs[2][5] = "C";
        strQs[2][6] = "Answer: C) -- numbers.length returns the length of the array, which is 5. numbers[numbers.length - 1] accesses the last element of the array, which is 5. The other options do not correctly reflect the output.";
        strQs[2][7] = "Hint: Remember, array indices start at 0. Think about how to access the last element of an array.";

        strQs[3][0] = "Which of the following statements about methods in Java is true?";
        strQs[3][1] = "Methods can only have one return statement.";
        strQs[3][2] = "Methods can have multiple return statements.";
        strQs[3][3] = "Methods cannot accept parameters.";
        strQs[3][4] = "Methods can only be called from other methods in the same class.";
        strQs[3][5] = "B";
        strQs[3][6] = "Answer: B) -- methods can have multiple return statements, as long as they are within conditional blocks - methods can only return 1 value, however. A) is incorrect because it is possible for methods to have 2 or more return statements in conditional blocks. C) is incorrect because methods can accept parameters. D) is incorrect because methods can be called from other classes as well.";
        strQs[3][7] = "Hint: Consider the flexibility of methods in Java and their ability to handle various scenarios (utilizing conditionals).";

        strQs[4][0] = "What does the following method do? public static int sum(int[] arr) { int total = 0; for (int i = 0; i < arr.length; i++) { total += arr[i]; } return total; }";
        strQs[4][1] = "Calculates the average of all elements in the array.";
        strQs[4][2] = "Finds the largest element in the array.";
        strQs[4][3] = "Computes the sum of all elements in the array.";
        strQs[4][4] = "Reverses the order of elements in the array.";
        strQs[4][5] = "C";
        strQs[4][6] = "Answer: C) -- the method iterates through each element of the array and adds it to the total variable, then returns the total sum. The other options describe different functionalities.";
        strQs[4][7] = "Hint: This method iterates through the array and accumulates a value into the total variable.";

        strQs[5][0] = "When should a method be declared as private in Java?";
        strQs[5][1] = "When the method needs to be accessible from any class.";
        strQs[5][2] = "When the method should only be accessible within the same class.";
        strQs[5][3] = "When the method needs to be overridden by subclasses.";
        strQs[5][4] = "When the method is called from a different package.";
        strQs[5][5] = "B";
        strQs[5][6] = "Answer: B) -- declaring a method as private restricts its access to only within the same class. Options A), C), and D) are incorrect as they do not accurately describe the purpose of declaring a method as private.";
        strQs[5][7] = "Hint: Private methods are more restricted in accessibility.";

        strQs[6][0] = "Which of the following statements about multidimensional arrays in Java is true?";
        strQs[6][1] = "Rows in a multidimensional array can have different lengths.";
        strQs[6][2] = "Multidimensional arrays can have different data types in each dimension.";
        strQs[6][3] = "Multidimensional arrays cannot be passed as arguments to methods.";
        strQs[6][4] = "Multidimensional arrays are not supported by the Java programming language.";
        strQs[6][5] = "A";
        strQs[6][6] = "Answer: A) -- in Java, multidimensional arrays are arrays of arrays, and the inner arrays can be of different lengths. The other options are incorrect because they do not accurately describe the behavior of multidimensional arrays (the data type throughout the entire structure remains constant, they can be passed as arguments to methods, and Java does indeed support multidimensional arrays).";
        strQs[6][7] = "Hint: Consider how multidimensional arrays are structured and accessed in Java.";

        strQs[7][0] = "In which scenario would you prefer to use a two-dimensional array instead of a one-dimensional array?";
        strQs[7][1] = "When storing a list of student names.";
        strQs[7][2] = "When implementing a tic-tac-toe board.";
        strQs[7][3] = "When keeping track of daily temperatures for a month.";
        strQs[7][4] = "When storing a sequence of integers.";
        strQs[7][5] = "B";
        strQs[7][6] = "Answer: B) -- a tic-tac-toe board is inherently a grid with rows and columns, making it a perfect fit for a two-dimensional array. The other options can be adequately handled with one-dimensional arrays.";
        strQs[7][7] = "Hint: Consider the structure of the data and how it maps to a grid-like representation.";

        strQs[8][0] = "What is the purpose of method parameters in Java?";
        strQs[8][1] = "To specify the return type of the method.";
        strQs[8][2] = "To define the accessibility of the method.";
        strQs[8][3] = "To pass values into the method for computation.";
        strQs[8][4] = "To specify the name of the method.";
        strQs[8][5] = "C";
        strQs[8][6] = "Answer: C) -- method parameters allow values to be passed into the method for use in computations or operations. Options A), B), and D) are incorrect as they do not accurately describe the purpose of method parameters.";
        strQs[8][7] = "Hint: Method parameters provide input to the method for processing.";

        strQs[9][0] = "What is the result of the following code? String str = \"Programming\"; System.out.println(str.substring(3, 7).toUpperCase());";
        strQs[9][1] = "GRAM";
        strQs[9][2] = "RAMM";
        strQs[9][3] = "RAMI";
        strQs[9][4] = "GRAMMING";
        strQs[9][5] = "A";
        strQs[9][6] = "Answer: A) -- str.substring(3, 7) returns \"gram\" (from index 3 inclusive to index 7 exclusive), and then toUpperCase() converts it to \"GRAM\". The other options do not correctly represent the result of the code.";
        strQs[9][7] = "Hint: Pay attention to the indices used in substring and the effect of toUpperCase.";

        strQs[10][0] = "What is the output of the following code? int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}; System.out.println(arr[1][1]);";
        strQs[10][1] = "1";
        strQs[10][2] = "5";
        strQs[10][3] = "6";
        strQs[10][4] = "8";
        strQs[10][5] = "B";
        strQs[10][6] = "Answer: B) -- arr[1][1] accesses the element at the second row and second column of the array, which is 5. The other options do not correctly represent the element at that position (arr[0][1], arr[1][2], and arr[2][1], in order).";
        strQs[10][7] = "Hint: Remember, array indices start at 0. Consider the row and column indices used in the array access.";

        strQs[11][0] = "What is the output of the following code? String[] names = {\"Alice\", \"Bob\", \"Charlie\"}; System.out.println(names[1].substring(0, 2));";
        strQs[11][1] = "Bo";
        strQs[11][2] = "Bob";
        strQs[11][3] = "Al";
        strQs[11][4] = "Cha";
        strQs[11][5] = "A";
        strQs[11][6] = "Answer: A) -- names[1] accesses the string \"Bob\" (0-based indexing in Java), and then substring(0, 2) extracts characters from index 0 up to index 2 (exclusive), resulting in \"Bo\". The other options do not correctly represent the output.";
        strQs[11][7] = "Hint: Pay attention to the indices used in substring (inclusive to exclusive) and the accessed array element (0-based indexing for arrays).";

        strQs[12][0] = "Given the following method declaration, how many values are returned? public static void outputSum{int num1, int num2}";
        strQs[12][1] = "2";
        strQs[12][2] = "3";
        strQs[12][3] = "1";
        strQs[12][4] = "0";
        strQs[12][5] = "D";
        strQs[12][6] = "Answer: D) -- Methods can only return one type, and in this case, the method is a procedure with a return type of void, meaning nothing is returned from this method. Option C) would be correct had the return type been anything other than void, and both options A) and B) are not possible, as methods can only return one value of a specified type (primitive or non-primitive).";
        strQs[12][7] = "Hint: Consider the return type specified in the method declaration.";

        strQs[13][0] = "If numbers[][] is a two-dimensional array, which of the following would give the number of elements in row n?";
        strQs[13][1] = "numbers[].size(n)";
        strQs[13][2] = "numbers.length(n)";
        strQs[13][3] = "numbers[].length(n)";
        strQs[13][4] = "numbers[n].length";
        strQs[13][5] = "D";
        strQs[13][6] = "Answer: D) numbers[n].length. This syntax accurately gives the number of elements in row n of the two-dimensional array numbers[][]. Options A, B, and C are incorrect because they either use invalid syntax or methods that do not exist for arrays in Java. Specifically, there is no size() method for arrays, and the correct method to get the length of a row in a two-dimensional array is numbers[rowIndex].length. Therefore, option D is the correct choice for determining the number of elements in row n of the array.";
        strQs[13][7] = "Hint: In Java, to get the number of elements in a specific row of a two-dimensional array, you need to use the length property of the row. Think about how you access elements in a two-dimensional array and how you would determine the length of a row.";

        strQs[14][0] = "Given the following method, what is the result of the call printStuff(‘a’, 3)?\npublic static void printStuff(String stuff, int i) {\nwhile (i > 0) {\nSystem.out.print(stuff);\ni--;\n}\n}";
        strQs[14][1] = "aaa";
        strQs[14][2] = "aaaa";
        strQs[14][3] = "Error";
        strQs[14][4] = "aa";
        strQs[14][5] = "C";
        strQs[14][6] = "Answer: C) -- The method printStuff expects the first parameter to be of type String. However, the method call printStuff('a', 3) passes a char type ('a') instead of a String. This causes a compilation error due to incompatible types. Java cannot implicitly convert a char to a String in this context. The correct way to call this method would be printStuff(\"a\", 3). Therefore, the error occurs due to the type mismatch.";
        strQs[14][7] = "Hint: Pay attention to the data types of the method parameters and arguments.";

        strQs[15][0] = "Which of the following methods is not a valid way to initialize a two-dimensional array in Java?";
        strQs[15][1] = "int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};";
        strQs[15][2] = "int[][] matrix = {{1, 2, 3}, new int[]{4, 5, 6}, {7, 8, 9}};";
        strQs[15][3] = "int[][] matrix = new int[3][3];";
        strQs[15][4] = "matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};";
        strQs[15][5] = "D";
        strQs[15][6] = "Answer: D) is incorrect because it attempts to initialize the array without using a new array declaration, and a type. The valid initializations are as follows: A) is correct because it uses the new int[][] syntax to initialize the array. B) is correct because it uses a combination of array literals and the new int[] syntax for initialization. C) is correct because it initializes an empty 3x3 array using the new int[3][3] syntax.";
        strQs[15][7] = "Hint: Pay attention to the syntax used for array initialization in Java. One of the options is missing a declaration in determining the type of elements the array can hold.";

        strQs[16][0] = "What is the scope of a local variable declared within a method in Java?";
        strQs[16][1] = "It is accessible only within the method in which it is declared.";
        strQs[16][2] = "It is accessible from any method within the same class.";
        strQs[16][3] = "It is accessible from any method within the same package.";
        strQs[16][4] = "It is accessible globally throughout the entire program.";
        strQs[16][5] = "A";
        strQs[16][6] = "Answer: A) -- a local variable declared within a method in Java is only accessible within that method. Option B) is incorrect because local variables are not accessible from other methods within the same class. Option C) is incorrect because local variables are not accessible from other methods within the same package. Option D) is incorrect because local variables are not accessible globally throughout the entire program; their scope is limited to the method in which they are declared.";
        strQs[16][7] = "Hint: Consider the visibility and accessibility of local variables. What do you think \"local\" means in this context to declaring a variable within a method.";

        strQs[17][0] = "What are the indices of the very last element in this two-dimensional array, if x=3, y=5, and z=10?\nx++;\nz--;\ny++;\nint[][] matrix = new int[z-y+x-5][z*x-5*y];";
        strQs[17][1] = "[2][6]";
        strQs[17][2] = "[1][5]";
        strQs[17][3] = "[3][5]";
        strQs[17][4] = "[1][6]";
        strQs[17][5] = "B";
        strQs[17][6] = "Answer: B) -- let’s break down the expressions for the dimensions of the array step by step. First Dimension: z-y+x-5 → Initially, z is 10. After decrementing z (z--), it becomes 9. After incrementing y (y++), it becomes 6. After incrementing x (x++), it becomes 4. Therefore, the expression evaluates to 9 - 6 + 4 - 5 = 2. Second Dimension: z*x-5*y → With the updated values, z is 9, x is 4, and y is 6. The expression evaluates to 9*4 - 5*6 = 36 - 30 = 6. So, the dimensions of the array are 2 rows by 6 columns. Since array indices start from 0, the last element's indices would be [1][5].";
        strQs[17][7] = "Hint: After the operations on x, y, and z, evaluate the expressions for array dimensions. Remember, dimensions determine the size of the array, while indices specify the position of elements within it. Pay attention to how the operations affect both dimensions and indices.";

        strQs[18][0] = "Given the following code, what are the contents of itemList and num after a call of modify(itemList, num)?\nint[] itemList = {9, 8, 7, 6};\nint num = 5;\n\n\npublic static void modify(int[] arr, int number)\n{\n   for (int i=0; i < arr.length; i++)\n   {\n      arr[i] = i;\n   }\n   number = arr[arr.length-1];\n}";
        strQs[18][1] = "itemArray = {0, 1, 2, 3} and val = 3;";
        strQs[18][2] = "itemArray = {0, 1, 2, 3} and val = 5;";
        strQs[18][3] = "itemArray = {9, 8, 7, 6} and val = 3;";
        strQs[18][4] = "itemArray = {9, 8, 7, 6} and val = 5;";
        strQs[18][5] = "B";
        strQs[18][6] = "Answer: B) -- In the modify method, the for loop iterates over each element in the array arr, setting each element to the index i. After this loop completes, the array arr will contain values corresponding to their indices, i.e., {0, 1, 2, 3}. However, the number parameter is only assigned the value of the last element of the array after the loop. Since the last element of the array arr (or itemList in this case) is 3, number is assigned the value 3. Therefore, after the call of modify(itemList, num), itemList will be {0, 1, 2, 3} and num will be 5, as the assignment within the modify method does not affect the original value of num. Hence, the correct answer is B.";
        strQs[18][7] = "Hint: Notice how the modify method updates the elements of the array but only assigns a new value to its local variable 'number'. This local change doesn't affect the variable 'num' outside the method. Think about how method parameters work in Java and their scope.";

        strQs[19][0] = "What is the output of the following code? String s = \"abcdef\"; System.out.println(s.replace(\"a\", \"XX\").replace(\"d\", \"Y\").substring(1, 5).concat(\"Z\"));";
        strQs[19][1] = "YbcZ";
        strQs[19][2] = "XXbcYZ";
        strQs[19][3] = "XbcYZ";
        strQs[19][4] = "YbcYZ";
        strQs[19][5] = "C";
        strQs[19][6] = "Answer: C) -- The code starts by replacing \"a\" with \"XX\", resulting in \"XXbcdef\". Then, it replaces \"d\" with \"Y\", giving \"XXbcYef\". Next, it takes a substring from index 1 (inclusive) to index 5 (exclusive), resulting in \"XbcY\". Finally, it concatenates \"Z\" to the substring, giving \"XbcYZ\". Therefore, the correct output is \"XbcYZ\". The other options reflect incorrect operations.";
        strQs[19][7] = "Hint: Pay attention to the sequence of operations: first, the replacements, then the substring extraction, and finally, the concatenation. Consider the indices used in substring and how they affect the resulting string.";
    }

    /****************************************************************************************************************
     * BELOW THIS LINE IS 'HELPING' CODE
     * Each routine below is commented -- so read for full understanding, here is a
     * short description of each
     * routine included below:
     * pauseToContinue - used to pause the program and then the user presses enter
     * to continue.
     * getString - used to get String input from the user.
     * getInt - used to get a valid integer input from the user.
     * getDbl - used to get a valid double (decimal values) input from the user.
     * checkNum - used to verify a String only has numeric values in it.
     * checkIntNum - used to check if a String value is a valid number within an
     * integer range.
     * checkDblNum - used to check if a String value is a valid number within a
     * double range.
     * getTime - used to retrieve the current system time in milliseconds, and can
     * be used to mark the start and end times for an action, to take the difference
     * and get the time elapsed in seconds.
     ****************************************************************************************************************/
    /**
     * This is a 'pause' routine to allow the user to just hit "enter" to continue
     * with your
     * programs can be used in multiple ways/locations. You can send in a message to
     * the user
     * and whether you want to clear the screen 'flush' before continuing.
     */
    public static void pauseToContinue(String message, boolean clearBeforeContinue) {
        // This is just to allow the user to review anything on screen
        // before clearing the screen and returning them to the main menu.
        getString(message, true, -1, -1);
        if (clearBeforeContinue) {
            // These next two lines will clear the terminal window in BlueJ and will
            // also clear the execution area on Repl.it or GDB online. (Fully explained
            // above.)
            System.out.print("\033[H\033[2J\f");
            System.out.flush();
        }
    }

    /**
     * This is used to get a valid String input from the user, used whenever I need
     * user input.
     * You send in the sMessage containing what you are asking the user for, next is
     * a boolean value as to whether
     * an empty String is okay input or not (emptyOK -- true means an empty String
     * is fine, false means need something).
     * You can also send in a minimum or maximum length needed for the String -- if
     * -1 is sent for either then
     * the length of the String is not checked. In the end the return value is a
     * String based on the criteria sent.
     */
    public static String getString(String sMessage, boolean emptyOK, int intMinChar, int intMaxChar) {
        // Setup the scanner for user input via the keyboard.
        Scanner keyInput = new Scanner(System.in);

        // Temp String to hold user's input until it is valid
        String strTemp = "";
        // Boolean variable to know whether we can end the user input loop.
        boolean blnLeaveLoop;
        do {
            // Getting the user's input to be stored in the strTemp variable.
            System.out.println(sMessage);
            strTemp = keyInput.nextLine();
            // Make the assumption that the input is good -- switch to false if there is an
            // issue.
            blnLeaveLoop = true;
            // Checking if empty String (and whether we need to check this).
            if (strTemp.length() == 0 && !emptyOK) {
                System.out.println("You need to enter something!");
                blnLeaveLoop = false;
            }
            // Checking if they care about how short the String is (minimum number of
            // characters).
            if (intMinChar != -1 && strTemp.length() < intMinChar) {
                System.out.println("Your input needs to have at least " + intMinChar + " characters.");
                blnLeaveLoop = false;
            }
            // Checking if they care about how long the String is (maximum number of
            // characters).
            if (intMaxChar != -1 && strTemp.length() > intMaxChar) {
                System.out.println("Your input needs to have less than or equal to " + intMaxChar + " characters.");
                blnLeaveLoop = false;
            }
        } while (!blnLeaveLoop);

        // ************************************************************************************************************
        // NOTE -- if you get an error in an online environment -- comment out the code
        // line below.
        // ************************************************************************************************************
        // keyInput.close();
        // Out of the input loop now -- so return the input -- it meets the
        // requirements.
        return strTemp;
    }

    /**
     * This is used to get input from the user in the form of an integer. A message
     * is sent to this routine
     * asking the user for integer input. A lowNum value should be sent as the
     * lowest number allowed as well as
     * a highNum value is sent to represent the largest value allowed as input. In
     * the end a valid integer is
     * returned based on the parameters sent in and outlined.
     */
    public static int getInt(String sMessage, int lowNum, int highNum) {
        // Temp String to hold the user's input until it is valid.
        String strTemp = "";
        // Boolean to keep user in loop until input is valid
        boolean blnValidInput = true;
        // Loop to ensure the user enters an integer and in the correct range.
        do {
            // Getting input -- by calling my getString routine, I send in the message to
            // ask the user as well as false
            // for not allowing an empty String. The next two parameters use the length of
            // the range values sent
            // to determine the length of the String input.
            strTemp = getString(sMessage, false, 1,
                    Math.max(Integer.toString(lowNum).length(), Integer.toString(highNum).length()));
            // Using the checkInt routine to verify a number.
            if (checkNum(strTemp, 0)) {
                // Checking the number is in the right range.
                if (checkIntNum(strTemp, lowNum, highNum)) {
                    blnValidInput = true;
                } else {
                    // It's not so let the user know.
                    System.out.println("Please enter a value between " + lowNum + " and " + highNum + ".");
                    blnValidInput = false;
                }
            } else {
                // If it got here there's other issues -- like not being a valid integer.
                System.out.println(
                        "Your input: " + strTemp + " is not a valid integer, please read carefully and try again.");
                blnValidInput = false;
            }
        } while (!blnValidInput);
        // Done the input loop -- send back the valid integer input.
        return Integer.parseInt(strTemp);
    }

    /**
     * This is used to get input from the user in the form of a double. A message is
     * sent to this routine
     * asking the user for decimal numeric input. A lowNum value should be sent as
     * the lowest number
     * allowed as well as a highNum value is sent to represent the largest value
     * allowed as input. In the
     * end a valid double value is returned based on the parameters sent in and
     * outlined.
     */
    public static double getDbl(String sMessage, double lowNum, double highNum) {
        // Temp String to hold the user's input until it is valid.
        String strTemp = "";
        // Boolean to keep user in loop until input is valid
        boolean blnValidInput = true;
        // Loop to ensure the user enters an integer and in the correct range.
        do {
            // Getting input -- by calling my getString routine, I send in the message to
            // ask the user as well as false
            // for not allowing an empty String. The next two parameters use the length of
            // the range values sent
            // to determine the length of the String input.
            strTemp = getString(sMessage, false, 1,
                    3 * Math.max(Double.toString(lowNum).length(), Double.toString(highNum).length()));
            // Using the checkInt routine to verify a number.
            if (checkNum(strTemp, 1)) {
                // Checking the number is in the right range.
                if (checkDblNum(strTemp, lowNum, highNum)) {
                    blnValidInput = true;
                } else {
                    // It's not so let the user know.
                    System.out.println("Please enter a value between " + lowNum + " and " + highNum + ".");
                    blnValidInput = false;
                }
            } else {
                // If it got here there's other issues -- like not being a valid integer.
                System.out.println(
                        "Your input: " + strTemp + " is not a valid integer, please read carefully and try again.");
                blnValidInput = false;
            }
        } while (!blnValidInput);
        // Done the input loop -- send back the valid integer input.
        return Double.parseDouble(strTemp);
    }

    /**
     * This is used to error check a String value (sNum sent to the function) to
     * verify that it is an integer
     * style number (no decimals). It will receive a String value to check and will
     * return true or false
     * based on its validity in terms of being an integer.
     */
    public static boolean checkNum(String sNum, int numDecimals) {
        // Initialize the valid input to false -- assume bad data first and then change
        // if all is good.
        boolean validInput = false;
        // Counters and variables to check for integer validity.
        // posNegCount keeps track of how many + or - characters are in the String.
        // posNegPos keeps track of the last position of a + or - found -- should only
        // be 0.
        // decimalCount keeps track of the number of '.' entered -- should stay at 0 for
        // integers
        // nonNumCount keeps track of how many non numeric characters there were --
        // should be 0 for numbers.
        int posNegCount = 0, posNegPos = -1, decimalCount = 0, nonNumCount = 0;

        // Loop to look at all the characters in the String input and update the counter
        // variables as appropriate.
        for (int i = 0; i < sNum.length(); i++) {
            // Based on what the current character is do....
            switch (sNum.charAt(i)) {
                // Current character is a decimal -- so add one to that counter.
                case '.':
                    decimalCount++;
                    break;
                // Current character is a + or - sign -- so add one to that counter and update
                // the position
                // where it was found.
                case '-':
                case '+':
                    posNegCount++;
                    posNegPos = i;
                    break;
                // Current character is a numeric value so do nothing it's okay nothing to do
                // here.
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    break;
                // Anything else caught here is a non-numeric character (at least for this
                // program's purposes).
                default:
                    nonNumCount++;
            }
        }
        // Now check the results to see if it was valid -- need 1 or 0 +/- sign
        // characters and they can only
        // occur in position 0 of the input.
        if (posNegCount <= 1 && posNegPos <= 0) {
            // We want to ensure there were no non-numeric characters
            if (nonNumCount <= 0) {
                // We want there to be 0 decimal points for integers, maximum 1 for doubles.
                if (decimalCount <= numDecimals) {
                    // Can't allow just 1 + (or -) sign or 1 decimal point, causing the program to
                    // crash.
                    if (!(posNegCount == 1 && sNum.length() == 1) && !(decimalCount == 1 && sNum.length() == 1)) {
                        // If we make it here, all is good. It's an integer style number.
                        validInput = true;
                    }
                }
            }
        }
        // Send back the validity of the String in terms of it being an integer or not.
        return validInput;
    }

    /**
     * This is used to verify that information sent to this function is a valid
     * integer within a specified
     * range. Sent to this routine are: the String version of the number to check
     * (sNum), the lowest value
     * allowed for the number (lowNum), the high value allowed for the number
     * (highNum). Returns true or false
     * as to the validity of the 'sNum' sent to the function.
     */
    public static boolean checkIntNum(String sNum, int lowNum, int highNum) {
        // Temp long to hold the String's value for size checking.
        long lngTemp = 0;
        // Boolean to hold whether the input is valid.
        boolean blnValidInput = true;
        // Using the checkInt routine to verify a number.
        if (checkNum(sNum, 0)) {
            // Storing the number -- as a long temporarily to allow for overflow on the
            // integer data type.
            // Hopefully the programmer using this code set appropriate limit values and by
            // the end
            // can successfully return an integer value.
            lngTemp = Long.parseLong(sNum);
            // Checking the number is in the right range.
            if (lngTemp < lowNum || lngTemp > highNum) {
                blnValidInput = false;
            }
            // All good -- return true
            else {
                blnValidInput = true;
            }
        }
        // Was not a valid number (integer)
        else {
            blnValidInput = false;
        }
        // return the status of the input.
        return blnValidInput;
    }

    /**
     * This is used to verify that information sent to this function is a valid
     * double within a specified
     * range. Sent to this routine are: the String version of the number to check
     * (sNum), the lowest value
     * allowed for the number (lowNum), the high value allowed for the number
     * (highNum). Returns true or false
     * as to the validity of the 'sNum' sent to the function.
     */
    public static boolean checkDblNum(String sNum, double lowNum, double highNum) {
        // Temp long to hold the String's value for size checking.
        double dblTemp = 0;
        // Boolean to hold whether the input is valid.
        boolean blnValidInput = true;
        // Using the checkInt routine to verify a number.
        if (checkNum(sNum, 1)) {
            // Storing the number -- as a long temporarily to allow for overflow on the
            // integer data type.
            // Hopefully the programmer using this code set appropriate limit values and by
            // the end
            // can successfully return an integer value.
            dblTemp = Double.parseDouble(sNum);
            // Checking the number is in the right range.
            if (dblTemp < lowNum || dblTemp > highNum) {
                blnValidInput = false;
            }
            // All good -- return true
            else {
                blnValidInput = true;
            }
        }
        // Was not a valid number (integer)
        else {
            blnValidInput = false;
        }
        // return the status of the input.
        return blnValidInput;
    }

    // This method retrieves the current system time in milliseconds.
    // It can be called to mark the start and end times for any action you want to
    // measure.
    public static long getTime() {
        return System.currentTimeMillis();
    }
}