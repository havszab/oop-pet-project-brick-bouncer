package dataaccess.implementation;

import dataaccess.KeyDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KeyDAOFileHandler implements KeyDAO {

    private static KeyDAOFileHandler instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private KeyDAOFileHandler() {
    }

    public static KeyDAOFileHandler getInstance() {
        if (instance == null) {
            instance = new KeyDAOFileHandler();
        }
        return instance;
    }

    private String keysFilePath = "resources/gamedata/keys.txt";
    private String ballInUseFilePath = "resources/gamedata/ball_in_use.txt";
    private String boardInUseFilePath = "resources/gamedata/board_in_use.txt";
    private String unlockedBallsFilePath = "resources/gamedata/unlocked_balls.txt";
    private String unlockedBoardsFilePath="resources/gamedata/unlocked_boards.txt";

    private Scanner readFile() throws FileNotFoundException {
        return new Scanner(new File(keysFilePath));
    }

    @Override
    public int getCount() throws FileNotFoundException {
        Scanner keys = readFile();
        if (keys.hasNext())
            return Integer.parseInt(keys.next());
        return 0;
    }

    @Override
    public void init() throws FileNotFoundException {
        try (FileWriter fw = new FileWriter(keysFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(0);
        } catch (IOException e) {
        }
    }


    @Override
    public void increase(int num) throws FileNotFoundException {
        int count = getCount() + num;
        try (FileWriter fw = new FileWriter(keysFilePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(count);
        } catch (IOException e) {
        }
    }

    @Override
    public void decrease(int num) throws FileNotFoundException {
        int count = getCount() - num;
        try (FileWriter fw = new FileWriter(keysFilePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(count);
        } catch (IOException e) {
        }
    }

    @Override
    public List<Integer> getUnlockedBalls() throws FileNotFoundException {
        Scanner unlockedBalls = new Scanner(new File(unlockedBallsFilePath)); // need to be closed?
        List<Integer> result = new ArrayList<>();
        while (unlockedBalls.hasNext())
            result.add(unlockedBalls.nextInt());
        return result;
    }

    @Override
    public void addUnlockedBall(int index) throws FileNotFoundException {
        List<Integer> items = new ArrayList<>(getUnlockedBalls());
        try (FileWriter fw = new FileWriter(unlockedBallsFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            if (!items.contains(index))
                out.println(index);
        } catch (IOException e) {
        }
    }

    @Override
    public int getBallInUse() throws FileNotFoundException {
        Scanner unlockedBalls = new Scanner(new File(ballInUseFilePath)); // need to be closed?
        if (unlockedBalls.hasNext())
            return Integer.parseInt(unlockedBalls.next());
        return 0;
    }

    @Override
    public void setBallInUse(int index) {
        try (FileWriter fw = new FileWriter(ballInUseFilePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(index);
        } catch (IOException e) {
        }
    }

    @Override
    public List<Integer> getUnlockedBoards() throws FileNotFoundException {
        Scanner unlockedBalls = new Scanner(new File(unlockedBoardsFilePath)); // need to be closed?
        List<Integer> result = new ArrayList<>();
        while (unlockedBalls.hasNext())
            result.add(unlockedBalls.nextInt());
        return result;
    }

    @Override
    public void addUnlockedBoard(int index) throws FileNotFoundException {
        List<Integer> items = new ArrayList<>(getUnlockedBoards());
        try (FileWriter fw = new FileWriter(unlockedBoardsFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            if (!items.contains(index))
                out.println(index);
        } catch (IOException e) {
        }
    }

    @Override
    public int getBoardInUse() throws FileNotFoundException {
        Scanner unlockedBalls = new Scanner(new File(boardInUseFilePath)); // need to be closed?
        if (unlockedBalls.hasNext())
            return Integer.parseInt(unlockedBalls.next());
        return 0;
    }

    @Override
    public void setBoardInUse(int index) {
        try (FileWriter fw = new FileWriter(boardInUseFilePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(index);
        } catch (IOException e) {
        }
    }

}
