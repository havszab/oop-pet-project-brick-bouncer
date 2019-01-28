package game.util;

import entities.Entity;
import entities.ingame.Barrier;
import entities.ingame.Board;
import javafx.geometry.Point2D;
import userinterface.WindowUtil;

import java.util.Random;

public class Util {

    public static Point2D directionToVector(double directionInDegrees, double length) {
        double directionInRadians = directionInDegrees / 180 * Math.PI;
        Point2D heading = new Point2D(length * Math.sin(directionInRadians), -length * Math.cos(directionInRadians));
        return heading;
    }

    public static int getRandomIntInRange(int low, int high) {
        Random random = new Random();
        int result = random.nextInt(high - low) + low;
        return result;
    }

    public static boolean isIn0to90degrees(double degree) {
        return degree >= 0 && degree <= 90;
    }

    public static boolean isIn90to180degrees(double degree) {
        return degree >= 90 && degree <= 180;
    }

    public static boolean isIn180to270degrees(double degree) {
        return degree >= 180 && degree <= 270;
    }

    public static boolean isIn270to360degrees(double degree) {
        return degree >= 270 && degree <= 360;
    }

    public static void waitMS(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void checkAndHandleGameWin() {
        int barriersLeft = 0;

        for (Entity ent : Globals.getGameObjects()) {
            if (ent instanceof Barrier)
                barriersLeft++;
        }
        if (barriersLeft - 1 < 1) {
            Globals.removeAllGameObjects();
            WindowUtil.winPopup();
        }
    }

    public static double getBoardX() {
        double x = 0;
        for (Entity e : Globals.getGameObjects()) {
            if (e instanceof Board)
                x = e.getX();
        }
        return x;
    }

}
