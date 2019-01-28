package dataaccess;

import java.io.FileNotFoundException;
import java.util.List;

public interface ScoreDAO {

    void add(long score);
    void remove();
    void clear();
    List<Long> getAll();
    long[] getFirst10() throws FileNotFoundException;
    Long getHighest() throws FileNotFoundException;
}
