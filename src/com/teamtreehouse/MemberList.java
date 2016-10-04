package com.teamtreehouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MemberList<T> {
    private Set<T> mMembers;

    public MemberList() {
        set(new ArrayList<>());
    }

    public MemberList(T[] members) {
        set(Arrays.asList(members));
    }

    public MemberList(List<T> members) {
        set(members);
    }

    public void set(T[] members) {
        set(Arrays.asList(members));
    }

    public void set(List<T> members) {
        mMembers = new TreeSet<>(members);
    }

    public Set<T> get() {
        return mMembers;
    }

    public T get(int index) {
        return (new ArrayList<>(get())).get(index);
    }

    public void add(T member) {
        mMembers.add(member);
    }

    public void remove(T member) {
        mMembers.remove(member);
    }
}