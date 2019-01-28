package dataaccess.implementation;

import dataaccess.ScoreDAO;

import java.io.*;
import java.util.*;

public class ScoreDAOFileHandler implements ScoreDAO {

    private static ScoreDAOFileHandler instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ScoreDAOFileHandler() {
    }

    public static ScoreDAOFileHandler getInstance() {
        if (instance == null) {
            instance = new ScoreDAOFileHandler();
        }
        return instance;
    }

    private String scroresFilePath = "resources/gamedata/scores.txt";

    private Scanner readFile() throws FileNotFoundException {
        Scanner scores = new Scanner(new File(scroresFilePath));
        return scores;
    }

    @Override
    public void add(long score) {
        try (FileWriter fw = new FileWriter(scroresFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            if (score > getFirst10()[9])
                out.println(score);

        } catch (IOException e) {
        }
    }

    @Override
    public void remove() {

    }

    @Override
    public void clear() {

    }

    @Override
    public long[] getFirst10() {
        long[] result = new long[10];
        for (int i = 0; i < Math.min(getAll().size(), 10); i++) {
            result[i] = getAll().get(i);
        }
        return result;
    }

    @Override
    public List<Long> getAll() {
        Scanner scores = null;
        try {
            scores = readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Long> result = new ArrayList();
        while (scores.hasNext()) {
            result.add(scores.nextLong());
        }
        Collections.sort(result);
        Collections.reverse(result);
        return result;
    }

    @Override
    public Long getHighest() throws FileNotFoundException {
        return getFirst10()[0];
    }
}
