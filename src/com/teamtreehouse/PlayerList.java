package com.teamtreehouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.teamtreehouse.model.Player;

public class PlayerList extends MemberList<Player> {

    public PlayerList() {
        set(new ArrayList<>());
    }

    public PlayerList(Player[] members) {
        set(Arrays.asList(members));
    }

    public PlayerList(List<Player> members) {
        set(members);
    }

    public String toString() {
        String output = "";
        int count = 1;

        Set<Player> members = get();

        for(Player player : members) {
            output += String.format("%d). %s%n", count++, player.toString(true));
        }

        return output;
    }
}
