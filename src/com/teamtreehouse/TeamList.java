package com.teamtreehouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.teamtreehouse.model.Team;

public class TeamList extends MemberList<Team> {

    public TeamList() {
        set(new ArrayList<>());
    }

    public TeamList(Team[] members) {
        set(Arrays.asList(members));
    }

    public TeamList(List<Team> members) {
        set(members);
    }

    public String toString() {
        String output = "";
        int count = 1;

        Set<Team> members = get();

        for(Team team : members) {
            output += String.format("%d). %s%n", count++, team.toString());
        }

        return output;
    }
}