package com.teamtreehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Set;

public class Prompter {
    private static BufferedReader mReader;
    private String mTitle;
    private String mActionMessage;
    private Set<String> mOptions;

    public Prompter(String title, String actionMesssage, Set<String> options) {
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mTitle = title;
        mActionMessage = actionMesssage;
        mOptions = options;
    }

    public static String promptAction(String actionMessage) throws IOException {
        printActionMessage(actionMessage);

        return mReader.readLine();
    }

    public String promptMenu() throws IOException {
        printMenu();

        return mReader.readLine();
    }

    private void printMenu() throws IOException {
        printTitle(mTitle);
        printOptions(mOptions);
        printActionMessage(mActionMessage);
    }

    public static void print(String output) throws IOException {
        System.out.println(output);
    }

    public static void printHeader(String header) {
        String output = "";
        output += String.format("%1$-50s%n"," ").replace(" ", "*");
        output += String.format("* %1$-47s*%n", header);
        output += String.format("%1$-50s%n"," ").replace(" ", "*");

        System.out.print(output);
    }

    public static void printResponse(String message) {
        String output = "";
        output += String.format("%n%1$-50s%n"," ").replace(" ", "-");
        output += String.format("| %1$-47s|%n", message);
        output += String.format("%1$-50s%n"," ").replace(" ", "-");

        System.out.print(output);
    }

    public static void printTitle(String title) {
        String output = "";
        output += String.format("%n%s%n", title);
        output += String.format("%1$50s%n"," ").replace(" ", "=");

        System.out.print(output);
    }

    public static String getTitle(String title) {
        return formatTitle(title, 50, "=");
    }

    public static String getSubTitle(String subtitle) {
        return formatTitle(subtitle, 50, "-");
    }

    public static String formatTitle(String text, int padLength, String padSymbol) {
        String output = "";
        output += String.format("%n%s%n", text);
        output += String.format("%1$" + padLength + "s%n"," ").replace(" ", padSymbol);

        return output;
    }

    public static void printOptions(Set<String> options) {
        String output = "";

        int count = 0;
        for(String option : options) {
            output += String.format("%d). %s%n", ++count, option);
        }

        System.out.printf("%s%n",output);
    }

    public static void printActionMessage(String actionMessage) {
        String output = "";
        output += String.format("%s: ", actionMessage);

        System.out.print(output);
    }
}