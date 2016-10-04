package com.teamtreehouse.model;

import java.util.Set;
import java.util.TreeSet;

import com.teamtreehouse.Prompter;

public class HeightGroup {
    int mMin;
    int mMax;
    Set<Player> mPlayers;

    public HeightGroup(int min, int max) {
        mMin = min;
        mMax = max;
        mPlayers = new TreeSet<>();
    }

    public void add(Player player) {
        if(inRange(player.getHeightInInches())) {
            mPlayers.add(player);
        } else {
            throw new IllegalArgumentException("Player height is out of range.");
        }
    }

    public void remove(Player player) {
        mPlayers.remove(player);
    }

    public Set<Player> get() {
        return mPlayers;
    }

    public Boolean inRange(int value) {
        return value >= mMin && value <= mMax;
    }

    @Override
    public String toString() {
        String output = Prompter.getSubTitle(String.format("Players %s to %s inches tall (%d Total)", mMin, mMax, get().size()));
//        String output = String.format(
//            "%nPlayers %s to %s inches tall (%d Total)%n" +
//            "------------------------------------------------%n",
//            mMin, mMax, get().size());

        for(Player player : get()) {
            output += String.format("%s, %s (%din)%n", player.getLastName(), player.getFirstName(), player.getHeightInInches());
        }

        return output;
    }
}
