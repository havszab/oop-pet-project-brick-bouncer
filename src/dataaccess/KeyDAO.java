package dataaccess;

import java.io.FileNotFoundException;
import java.util.List;

public interface KeyDAO {

    int getCount () throws FileNotFoundException;
    void increase(int num) throws FileNotFoundException;
    void decrease(int num) throws FileNotFoundException;
    void init() throws FileNotFoundException;


    List<Integer> getUnlockedBalls() throws FileNotFoundException;
    void addUnlockedBall(int index) throws FileNotFoundException;
    int getBallInUse() throws FileNotFoundException;
    void setBallInUse(int index);


    List<Integer> getUnlockedBoards() throws FileNotFoundException;
    void addUnlockedBoard(int index) throws FileNotFoundException;
    int getBoardInUse() throws FileNotFoundException;
    void setBoardInUse(int index);
}
