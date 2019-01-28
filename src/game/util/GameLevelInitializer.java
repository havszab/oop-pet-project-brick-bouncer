package game.util;

import entities.ingame.Barrier;
import entities.ingame.powerups.BallUlralizer;
import entities.ingame.powerups.Bomb;
import game.run.Game;

public class GameLevelInitializer {

    private static int DELTA_X = 80;
    private static int DELTA_Y = 40;


    public static void initBarriers(Game game, int lvl) {
        switch (lvl) {
            case 1:
                int rowDistance;
                int columnDistance;
                placeBarrierToCoordinate(6,6,3,game);
                placeBarrierToCoordinate(7,6,3,game);
                placeBombToCoordinate(6,7,game);
                placeBombToCoordinate(7,7,game);
                placeUltToCoordinate(1,3,game);
                break;
            case 2:
                placeBarrierRows(1,1, 3,3, 2, game);
                break;
            case 3:
                placeBarriersInRectangleShape(game, 4, 5, 200, 3);
                break;
            case 4:
                rowDistance = 0;
                for (int row = 0; row < 3; row++) {
                    columnDistance = 0;
                    for (int col = 0; col < 10; col++) {
                        new Barrier(game, 120+columnDistance, 100 + rowDistance, 3);
                        columnDistance += DELTA_X;
                    }
                    rowDistance += DELTA_Y;
                    columnDistance = 30;
                    for (int col = 0; col < 9; col++) {
                        new Barrier(game, 120+columnDistance, 100+ rowDistance, 2);
                        columnDistance += DELTA_X;
                    }
                    rowDistance += DELTA_Y;
                }
                break;
            case 5:
                placeBarriersInRectangleShape(game, 2, 10, 150, 2);
                placeBarriersInRectangleShape(game, 2, 10, 350, 3);
                placeBarriersInRectangleShape(game, 1, 5, 500, 2);
                break;
            case 6:
                placeBarriersInRectangleShape(game, 3, 10, 150, 3);
                placeBarriersInRectangleShape(game, 2, 10, 300, 2);
                placeBarriersInRectangleShape(game, 1, 12, 340, 3);
                placeBarriersInRectangleShape(game, 1, 5, 500, 2);
                break;
            case 7:
                placeBarriersInRectangleShape(game, 1, 12, 150, 3);
                placeBarriersInRectangleShape(game, 1, 11, 190, 2);
                placeBarriersInRectangleShape(game, 1, 10, 230, 1);
                placeBarriersInRectangleShape(game, 1, 9, 270, 3);
                placeBarriersInRectangleShape(game, 1, 8, 310, 2);
                placeBarriersInRectangleShape(game, 1, 7, 350, 1);
                break;
            case 8:
                placeBarriersInRectangleShape(game, 1, 12, 150, 3);
                placeBarriersInRectangleShape(game, 1, 11, 190, 2);
                placeBarriersInRectangleShape(game, 1, 10, 230, 1);
                placeBarriersInRectangleShape(game, 1, 9, 270, 3);
                placeBarriersInRectangleShape(game, 1, 8, 310, 2);
                placeBarriersInRectangleShape(game, 1, 7, 350, 1);
                placeBarriersInRectangleShape(game, 1, 6, 390, 3);
                placeBarriersInRectangleShape(game, 1, 5, 430, 2);
                placeBarriersInRectangleShape(game, 1, 4, 470, 1);
                placeBarriersInRectangleShape(game, 1, 3, 510, 3);
                placeBarriersInRectangleShape(game, 1, 2, 550, 2);
                placeBarriersInRectangleShape(game, 1, 1, 590, 1);
                break;
            case 9:
                placeBarriersInRectangleShape(game, 1, 12, 150, 3);
                placeBarriersInRectangleShape(game, 1, 9, 190, 2);
                placeBarriersInRectangleShape(game, 1, 12, 230, 3);
                placeBarriersInRectangleShape(game, 1, 9, 270, 3);
                placeBarriersInRectangleShape(game, 1, 12, 310, 2);
                placeBarriersInRectangleShape(game, 1, 9, 350, 3);
                placeBarriersInRectangleShape(game, 1, 8, 390, 3);
                break;
            case 10:
                for (int i = 0; i < 12; i++) {
                    placeBombToCoordinate(1+i, 1, game);
                }
                placeBarriersInRectangleShape(game, 2, 12, 150, 3);
                placeBarriersInRectangleShape(game, 2, 9, 230, 2);
                placeBarriersInRectangleShape(game, 2, 12, 310, 3);
                placeBarriersInRectangleShape(game, 2, 9, 390, 2);
                placeBarriersInRectangleShape(game, 2, 12, 470, 3);
                for (int i = 0; i < 12; i++) {
                    placeUltToCoordinate(1+i,12,game);
                }
                break;
            case 11:
                placeBarrierRows(1,1,12,4,3,game);
                placeBombRows(1,5,12,2,game);
                placeBarrierRows(1, 7, 12, 3, 3, game);
                placeBombRows(1,10,12,1,game);
                placeBarrierRows(1, 11, 12, 1, 3, game);
                placeUltRows(1, 12, 12,1,game);
                break;

        }
    }

    private static void placeBarriersInRectangleShape(Game game, int numOfRow, int numOfColumns, int startingY, int lives) {
        int rowDist = 0;
        int colDist;
        int startingX = 500 - (numOfColumns * DELTA_X) / 2;
        for (int row = 0; row < numOfRow; row++) {
            colDist = 0;
            for (int col = 0; col < numOfColumns; col++) {
                new Barrier(game, startingX + colDist, startingY + rowDist, lives);
                colDist += DELTA_X;
            }
            rowDist += DELTA_Y;
        }
    }

    private static void placeBombsInRectangleShape(Game game, int numOfRow, int numOfColumns, int startingY) {
        int rowDist = 0;
        int colDist;
        int startingX = 500 - (numOfColumns * DELTA_X) / 2;
        for (int row = 0; row < numOfRow; row++) {
            colDist = 0;
            for (int col = 0; col < numOfColumns; col++) {
                new Bomb(game, startingX + colDist, startingY + rowDist);
                colDist += DELTA_X;
            }
            rowDist += DELTA_Y;
        }
    }

    // X beginning with 20 to 980, Y beginning from 100 to 480

    private static void placeBarrierToCoordinate(int x, int y, int lives, Game g) {
        new Barrier(g, 20 + ((x-1) * DELTA_X), 100 + ((y-1) * DELTA_Y), lives);
    }

    private static void placeBombToCoordinate (int x, int y, Game g) {
        new Bomb(g, 20 + ((x-1) * DELTA_X), 100 + ((y-1) * DELTA_Y));

    }

    private static void placeUltToCoordinate (int x, int y, Game g) {
        new BallUlralizer(g, 20 + ((x-1) * DELTA_X), 100 + ((y-1) * DELTA_Y));

    }

    private static void placeBarrierRows(int leftTopX, int leftTopY,  int numOfCol, int numOfRow, int lives, Game g) {
        for (int row = 0; row < numOfRow; row++) {
            for (int col = 0; col < numOfCol; col++) {
                placeBarrierToCoordinate(leftTopX + col, leftTopY + row, lives, g);
            }
        }
    }

    private static void placeBombRows(int leftTopX, int leftTopY,  int numOfCol, int numOfRow, Game g) {
        for (int row = 0; row < numOfRow; row++) {
            for (int col = 0; col < numOfCol; col++) {
                placeBombToCoordinate(leftTopX + col, leftTopY + row, g);
            }
        }
    }

    private static void placeUltRows(int leftTopX, int leftTopY,  int numOfCol, int numOfRow, Game g) {
        for (int row = 0; row < numOfRow; row++) {
            for (int col = 0; col < numOfCol; col++) {
                placeUltToCoordinate(leftTopX + col, leftTopY + row, g);
            }
        }
    }
}
