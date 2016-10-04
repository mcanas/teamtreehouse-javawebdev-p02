import java.io.BufferedReader;
import java.io.IOException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.teamtreehouse.Prompter;
import com.teamtreehouse.PlayerList;
import com.teamtreehouse.TeamList;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

public class LeagueManager {
    private static Prompter mAppPrompter;
    private static Set<String> mMenu;
    private static TeamList mAllTeams;
    private static PlayerList mAllPlayers;
    private static PlayerList mSelectedPlayers;

    public static void main(String[] args) {
        Player[] players = Players.load();
        System.out.printf("There are currently %d registered players.%n", players.length);
        // Your code here!

        mAllPlayers = new PlayerList(players);
        mSelectedPlayers = new PlayerList();
        mAllTeams = new TeamList();

        mMenu = new LinkedHashSet<>();
        mMenu.add("Create a new team");
        mMenu.add("Add a player to a team");
        mMenu.add("Remove a player from a team");
        mMenu.add("Print team roster");
        mMenu.add("Team height report");
        mMenu.add("League balance report");
        mMenu.add("Quit the League Manager");

        mAppPrompter = new Prompter("League Manager Menu", "Choose a menu option", mMenu);

        run();
    }

    private static void run() {
        Prompter.printHeader("Welcome to the League Manager");

        String choice = "";

        do {
            try {
                choice = mAppPrompter.promptMenu();

                switch(choice) {
                    case "1":
                        onCreateNewTeam();
                        break;
                    case "2":
                        onAddPlayerToTeam();
                        break;
                    case "3":
                        onRemovePlayerFromTeam();
                        break;
                    case "4":
                        onPrintTeamRoster();
                        break;
                    case "5":
                        onTeamHeightReport();
                        break;
                    case "6":
                        onLeagueBalanceReport();
                        break;
                    case "7":
                        Prompter.printResponse("Exiting the League Manager.");
                        break;
                    default:
                        System.out.printf("%nSorry, you chose an invalid option. Please select another option.%n%n");
                }
            } catch(IOException ioe) {
                System.out.println("Problem reading input");
                ioe.printStackTrace();
            }
        } while(!choice.equals("7"));
    }

    private static void onCreateNewTeam() throws IOException {
        if(mAllTeams.get().size() < mAllPlayers.get().size()) {
            Team newTeam = promptForNewTeam();
            mAllTeams.add(newTeam);

            Prompter.printResponse(
                String.format("New team %s coached by %s has been created",
                    newTeam.getName(),
                    newTeam.getCoach()
                )
            );
        } else {
            Prompter.printResponse("No players available to create a team");
        }
    }

    private static void onAddPlayerToTeam() throws IOException {
        if(mAllTeams.get().size() > 0 && mSelectedPlayers.get().size() < mAllPlayers.get().size()) {
            Team teamToAddTo = promptForTeam(mAllTeams);
            Player playerToAdd = promptForPlayer(
                getRemainingPlayers(mAllPlayers, mSelectedPlayers));

            teamToAddTo.addPlayer(playerToAdd);
            mSelectedPlayers.add(playerToAdd);

            Prompter.printResponse(String
                .format("%s %s has been added to %s", playerToAdd.getFirstName(),
                    playerToAdd.getLastName(), teamToAddTo.getName()));
        } else if(mAllTeams.get().size() == 0) {
            Prompter.printResponse("No teams have been created");
        } else {
            Prompter.printResponse("No players available to add");
        }
    }

    private static void onRemovePlayerFromTeam() throws IOException {
        if(mAllTeams.get().size() > 0 && mSelectedPlayers.get().size() > 0) {
            Team teamToRemoveFrom = promptForTeam(mAllTeams);

            if(teamToRemoveFrom.getPlayers().size() > 0) {
                Player playerToRemove = promptForPlayer(teamToRemoveFrom.getPlayerList());

                teamToRemoveFrom.removePlayer(playerToRemove);
                mSelectedPlayers.remove(playerToRemove);

                Prompter.printResponse(String
                    .format("%s %s has been removed from %s", playerToRemove.getFirstName(), playerToRemove.getLastName(),
                        teamToRemoveFrom.getName()));
            } else {
                Prompter.printResponse("No players available to remove");
            }
        } else if(mAllTeams.get().size() == 0) {
            Prompter.printResponse("No teams have been created");
        } else {
            Prompter.printResponse("No players available to remove");
        }
    }

    private static void onPrintTeamRoster() throws IOException {
        if(mAllTeams.get().size() > 0) {
            Team teamToPrint = promptForTeam(mAllTeams);
            if (teamToPrint.getPlayers().size() > 0) {
                System.out.println(teamToPrint.getRoster());
            } else {
                Prompter.printResponse("There are no players on this team");
            }
        } else {
            Prompter.printResponse("No teams have been created");
        }
    }

    private static void onTeamHeightReport() throws IOException {
        if(mAllTeams.get().size() > 0) {
            System.out.println(promptForTeam(mAllTeams).getPlayerReport());
        } else {
            Prompter.printResponse("No teams have been created");
        }
    }

    private static void onLeagueBalanceReport() throws IOException {
        if(mAllTeams.get().size() > 0 && mSelectedPlayers.get().size() > 0) {
            System.out.println(getLeagueBalanceReport());
        } else if(mAllTeams.get().size() == 0) {
            Prompter.printResponse("No teams have been created");
        } else {
            Prompter.printResponse("No players have been added to a team");
        }
    }

    private static String getLeagueBalanceReport() {
        String output = "";
        output += Prompter.getTitle("League Balance Report");

        for(Team team : mAllTeams.get()) {
            output += team.getBalanceReport();
        }

        return output;
    }

    private static PlayerList getRemainingPlayers(PlayerList allPlayers, PlayerList selectedPlayers) {
        Set<String> selectedPlayerNames = selectedPlayers
                                            .get()
                                            .stream()
                                            .map(Player::toString)
                                            .collect(Collectors.toSet());

        List<Player> filteredPlayers = allPlayers
                                        .get()
                                        .stream()
                                        .filter(e -> !selectedPlayerNames.contains(e.toString()))
                                        .collect(Collectors.toList());

        return new PlayerList(filteredPlayers);
    }

    private static Team promptForNewTeam() throws IOException {
        Prompter.printTitle("Create a new team");
        String name = Prompter.promptAction("Enter a team name");
        String coach = Prompter.promptAction("Enter a coach");

        return new Team(name.trim(), coach.trim());
    }

    private static Team promptForTeam(TeamList teams) throws IOException {
        Prompter.printTitle("Select a team");
        Prompter.print(teams.toString());
        return teams.get(Integer.parseInt(Prompter.promptAction("Choose a team")) - 1);
    }

    private static Player promptForPlayer(PlayerList players) throws IOException {
        Prompter.printTitle("Select a player");
        Prompter.print(players.toString());
        return players.get(Integer.parseInt(Prompter.promptAction("Choose a player")) - 1);
    }
}