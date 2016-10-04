package com.teamtreehouse.model;

import java.util.*;

import com.teamtreehouse.PlayerList;
import com.teamtreehouse.Prompter;

public class Team implements Comparable<Team> {
    private String mName;
    private String mCoach;
    private PlayerList mPlayers;

    public Team(String name, String coach) {
        mName = name;
        mCoach = coach;
        mPlayers = new PlayerList();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCoach() {
        return mCoach;
    }

    public void setCoach(String coach) {
        mCoach = coach;
    }

    public void addPlayer(Player player) {
        mPlayers.add(player);
    }

    public void removePlayer(Player player) {
        mPlayers.remove(player);
    }

    public int compareTo(Team other) {
        if(equals(other)) {
            return 0;
        }

        return mName.compareTo(other.getName());
    }

    public PlayerList getPlayerList() {
        return mPlayers;
    }

    public Set<Player> getPlayers() {
        return getPlayerList().get();
    }

    public String getPlayerReport() {
        String output = Prompter.getTitle(String.format("%s Player Height Report", getName()));

        for(Map.Entry<String, HeightGroup> entry : groupPlayersByHeight().entrySet()) {
            output += entry.getValue().toString();
        }

        return output;
    }

    public String getBalanceReport() {
        int total = getPlayers().size();
        int experienced = (int) getPlayers()
                                .stream()
                                .filter(Player::isPreviousExperience)
                                .count();
        int inexperienced = total - experienced;

        String output = Prompter.getSubTitle(String.format("%s Balance Report", getName()));

        output += String
                    .format(
                        "Experienced: %d (%.0f%%)%nInexperienced: %d (%.0f%%)%n",
                        experienced,
                        getPercentageOf(experienced, total),
                        inexperienced,
                        getPercentageOf(inexperienced, total)
                    );

        return output;
    }

    public String getRoster() {
        return getPlayerList().toString();
    }

    @Override
    public String toString() {
        return String.format("%s (Coach: %s)", mName, mCoach);
    }

    private Map<String, HeightGroup> groupPlayersByHeight() {
        Map<String, HeightGroup> heightGroups = new HashMap<>();
        heightGroups.put("35-40", new HeightGroup(35, 40));
        heightGroups.put("41-46", new HeightGroup(41, 46));
        heightGroups.put("47-50", new HeightGroup(47, 50));

        for(Player player : getPlayers()) {
            int height = player.getHeightInInches();

            if(height >= 35 && height <= 40) {
                heightGroups.get("35-40").add(player);
                continue;
            }

            if(height >= 41 && height <= 46) {
                heightGroups.get("41-46").add(player);
                continue;
            }

            if(height >= 47 && height <= 50) {
                heightGroups.get("47-50").add(player);
            }
        }

        return heightGroups;
    }

    private float getPercentageOf(int num, int total) {
        return ((float) num / (float) total) * 100;
    }

}
