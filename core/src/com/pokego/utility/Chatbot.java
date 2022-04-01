package com.pokego.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * The Chatbot receives an input, and sends an output according to the input. The reply to each input is specified in the "./assets/data/help.txt" file. It is used in the HelpState.
 */
public class Chatbot {
    public final static String DEFAULT_FILE_PATH = "./data/help.txt";

    private String filePath;
    private ArrayList<String> questionResponseArray;

    /**
     * Constructs the Chatbot object according to the default path given.
     */
    public Chatbot() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Constructs the Chatbot object according to a custom path given.
     * @param filePath The file path containing the question response data.
     */
    public Chatbot(String filePath) {
        this.filePath = filePath;
        questionResponseArray = readFile();
    }

    /**
     * Reads the text file, and stores each line as a String in an ArrayList
     * @return ArrayList of Strings, each String contains a line in the .txt file.
     */
    private ArrayList<String> readFile() {
        // read file, store into questionResponseArray
        String line = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while((line = reader.readLine()) != null){
                list.add(line);
                System.out.println("Added " + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Gets response according the input.
     * @param input Input to query the ArrayList of Strings.
     * @return response according to the .txt file.
     */
    public String getResponse(String input) {
        for (String questionResponseString : questionResponseArray) {
            int equalsIndex = questionResponseString.indexOf("=");
            String question = "";

            // Find question in line
            if (equalsIndex != -1) {
                question = questionResponseString.substring(0, equalsIndex);
            }

            // If question found, get response
            if (question.equals(input)) {
                int endIndex = questionResponseString.length();
                return questionResponseString.substring(equalsIndex + 1, endIndex);
            }
        }

        return null;
    }
}
