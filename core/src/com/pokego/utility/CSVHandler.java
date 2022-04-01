package com.pokego.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pokego.datamodel.GameAnimation;
import com.pokego.game.game_data.Character;
import com.pokego.game.game_data.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Collections;
import java.io.IOException;


/**
 * The CSVHandler is used to read and store data in various .csv files. .csv files are mainly used to store different data in the game such as the Player scoreboard, Moves and Characters. Stores a collection of static functions.
 */
public class CSVHandler {
    private final static String DEFAULT_PLAYER_SCORES = "./data/playerScores.csv";
    private final static String DEFAULT_CHARACTERS = "./data/characters.csv";
    private final static String DEFAULT_MOVES = "./data/moves.csv";

    /**
     * Reads leaderboard .csv file. Then returns formatted String of the leaderboard.
     * @return Returns formatted String of the leaderboard.
     */
    public static String readLeaderboard() {
        // CSV reader for the scoreboard.
        try {
            int position = 0;
            String line = "";
            //reading from file
            StringBuilder everything = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(DEFAULT_PLAYER_SCORES));
            //Formatting the CSV into columns before Outputting.
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                if (position == 0) {
                    everything.append(String.format("%-10s%-50.10s%-25s\n", "", words[0], words[1]));
                } else {
                    everything.append(String.format("%-10s%-50.10s%-25s\n", position, words[0], words[1]));

                }
                position += 1;
            }
            return everything.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "";

        }
    }

    /**
     * Inserts new data to the leaderboard .csv file.
     * @param winner Name of the Player, winner.
     * @param loser Name of the Player, loser.
     */
    public static void insertIntoLeaderboard(String winner, String loser){
        /*
            If name exists, add new name to leaderboard, else + 1 to Name's wins
         */

        try{
            boolean winnerExist = false;
            boolean loserExist = false;
            String line = "";
            //reading from file
            BufferedReader br = new BufferedReader(new FileReader(DEFAULT_PLAYER_SCORES));
            Map<String, String> map = new HashMap<String, String>();
            br.readLine();//read header
            while ((line = br.readLine()) != null) {
                String str[] = line.split(",");
                //Check for winner if player exist add 1 to its count.
                if (winner.equals(str[0])){
                    Integer number = Integer.valueOf(str[1]);
                    String result = String.valueOf(number+1);
                    str[1] = result.toString();
                    map.put(str[0], str[1]);
                    winnerExist = true;
                }
                //Setting a flag to check if looser exist in the csv file.
                if (loser.equals(str[0])){
                    loserExist = true;
                }
                map.put(str[0], str[1]);
            }
            //if winner do not exist add a new entry.
            if (winnerExist == false){
                map.put(winner, "1");
            }
            //if loose do not exist add a new entry
            if(loserExist == false){
                map.put(loser,"0");
            }
            br.close();
            FileWriter writer = new FileWriter(DEFAULT_PLAYER_SCORES);
            writer.write("Name,Wins\n");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey()+","+entry.getValue()+"\n");
            }
            writer.close();
            //System.out.println(map);

        } catch (Exception e){
            System.out.println(e);
        }


    }

    /**
     * Controls the field to be sorted for the sortCsv method
     * @param line
     * @return String of line.
     */
    private static String getField(String line) {
        return line.split(",")[1];// extract value you want to sort on
    }

    /**
     * Sorts .csv file according to wins.
     */
    public static void sortLeaderboard() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_PLAYER_SCORES));
            Map<String, List<String>> map = new TreeMap<String, List<String>>();
            String line = reader.readLine();//read header
            while ((line = reader.readLine()) != null) {
                String key = getField(line);
                List<String> l = map.get(key);
                if (l == null) {
                    l = new LinkedList<String>();
                    map.put(key, l);
                }
                l.add(line);
            }

            //System.out.println(map);
            Map<String, List<String>> newMap = new TreeMap<String, List<String>>(Collections.reverseOrder());
            newMap.putAll(map);
            System.out.println(map);
            System.out.println(newMap);

            reader.close();

            FileWriter writer = new FileWriter(DEFAULT_PLAYER_SCORES);
            writer.write("Name,Wins\n");
            for (List<String> list : newMap.values()) {
                for (String val : list) {
                    writer.write(val);
                    writer.write("\n");
                }
            }
            writer.close();

        } catch (Exception e){
            System.out.println(e);
        }

    }

    /**
     * Gets Move from move .csv file according to the moveID specified.
     * @param moveID MoveID to get Move from.
     * @return Move object of specific moveID.
     */
    public static Move getMoveByID(int moveID) {
        // Read move from CSV, and return according to moveID
        int currRow = 1;
        try {
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader(DEFAULT_MOVES));
            while ((row = csvReader.readLine()) != null) {
                if (currRow > 1) {
                    String[] data = row.split(",");

                    int csvMoveID = Integer.parseInt(data[0]);

                    // If moveID matches csvMoveID
                    if (csvMoveID == moveID) {
                        String csvMoveName = data[1];
                        Type csvMoveType = Type.valueOf(data[2]);
                        MoveClass csvMoveClass = MoveClass.valueOf(data[3]);
                        String csvMoveSpriteLocation = data[4];
                        int csvMoveBaseDamage = Integer.parseInt(data[5]);
                        int csvMoveDamageBlocked = Integer.parseInt(data[6]);
                        int csvMovePP = Integer.parseInt(data[7]);
                        int csvFrameCount = Integer.parseInt(data[8]);
                        float csvFrameDuration = Float.parseFloat(data[9]);

                        TextureRegion textureRegion = new TextureRegion(new Texture(csvMoveSpriteLocation));
                        GameAnimation animation = new GameAnimation(textureRegion, csvFrameCount, csvFrameDuration);

                        switch (csvMoveClass) {
                            case ATTACK:
                                return new AttackMove(csvMoveName, csvMoveType, animation, csvMoveBaseDamage);
                            case DEFENSE:
                                return new DefenseMove(csvMoveName, csvMoveType, animation, csvMoveDamageBlocked);
                        }
                    }
                }
                currRow++;
            }
            return null;
        } catch (IOException e) {
            // If read file fails.
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the total number of Characters available in the game.
     * @return Returns the total number of Characters available in the game.
     */
    public static int getCharacterCount() {
        int currRow = 0;
        try {
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader(DEFAULT_CHARACTERS));
            while ((row = csvReader.readLine()) != null) {
                currRow++;
            }
            return currRow - 1;

        } catch (IOException e) {
            // If read file fails.
            e.printStackTrace();

        }
        return -1;
    }

    /**
     * Gets Character from move .csv file according to the characterID specified.
     * @param characterID CharacterID to get Character from.
     * @return Character object of specific characterID.
     */
    public static Character getCharacterByID(int characterID) {
        // Read character from CSV, and return according to characterID
        int currRow = 1;
        try {
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader(DEFAULT_CHARACTERS));
            while ((row = csvReader.readLine()) != null) {
                if (currRow > 1) {
                    String[] data = row.split(",");

                    int csvCharID = Integer.parseInt(data[0]);

                    // If character matches characterID
                    if (characterID == csvCharID) {
                        String csvCharName = data[1];
                        Type csvCharType = Type.valueOf(data[2]);
                        String csvCharSpriteLocation = data[3];
                        Move csvCharFireMove = getMoveByID(Integer.parseInt(data[4]));
                        Move csvCharWaterMove = getMoveByID(Integer.parseInt(data[5]));
                        Move csvCharGrassMove = getMoveByID(Integer.parseInt(data[6]));
                        Move csvCharDefenseMove = getMoveByID(Integer.parseInt(data[7]));

                        return new Character(csvCharName, csvCharType, csvCharSpriteLocation, csvCharFireMove, csvCharWaterMove, csvCharGrassMove, csvCharDefenseMove);
                    }
                }
                currRow++;
            }
        } catch (IOException e) {
            // If read file fails.
            e.printStackTrace();
        }
        return null;
    }
}
