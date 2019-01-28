package entities.ingame;

import entities.Animatable;
import entities.Entity;
import entities.ingame.border.Border;
import entities.ingame.powerups.BallUlralizer;
import entities.ingame.powerups.Bomb;
import game.util.Globals;
import game.util.Util;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import userinterface.TextDisplayUtil;
import userinterface.WindowUtil;



public class Ball extends Entity implements Animatable {
    private double speed = 6;
    public static final double BALL_SPEED_GROWTH_PER_MIN = 0;

    private static final int SCORE_BONUS = 3;
    private static int scoreBonusSum;

    private static int lives;
    private static int score;

    private boolean isUltra = false;


    public Ball(Pane pane, int xc, int yc, int lives) {
        super(pane);
        this.pane = pane;
        setX(xc);
        setY(yc);
        setImage(Globals.ball);
        setRotate(1);
        pane.getChildren().add(this);

        this.setLives(lives);
    }

    @Override
    public void step() {
        double dir = getRotate();
        // set rotation and position
        setRotate(dir); // ?? irrelevant ??
        Point2D heading = Util.directionToVector(dir, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());

        if (Globals.shiftDown)
            this.speed = 7;

        if (getX() < 0 || getX() > 1000 || getY() < 0 || getY() > 1000) {
            System.out.println("X: " + getX());
            System.out.println("Y: " + getY());
            System.out.println(getRotate());
            resetPosAndDir();
        }

        speed += BALL_SPEED_GROWTH_PER_MIN / (Globals.SECS_PER_MIN * Globals.STEP_CALLED_PER_SEC);

        for (Entity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof Border || entity instanceof Board || entity instanceof Barrier) {
                    bounce(dir, entity);
                } else if (entity instanceof Bomb) {
                    ((Bomb) entity).explode();
                } else if (entity instanceof BallUlralizer) {
                    setUltra(true);
                    ultraBallMode();
                    entity.destroy();
                }
            }
        }
    }

    private void bounce(double dir, Entity entity) {
        double newDir = dir + 180;
        if (entity instanceof Border){
            endUltraMode();
            Border border = (Border) entity;
            newDir = getBorderBounceDirection(dir, newDir, border);
        } else if (entity instanceof Board) {
            endUltraMode();
            Globals.ballBounce.play();
            resetScoreBonusSum();
            double horizontalDifference =entity.getX() - this.getX();
            newDir = calculateDirectionOnBoardBounce(horizontalDifference);
        } else if (entity instanceof Barrier) {
            Barrier barrier = (Barrier) entity;
            if (isUltra){
                Globals.ultSound.play();
                barrier.handleUltraDamage();
                increaseScore();
                newDir = dir;
            } else {
                Globals.ballBounce.play();
                increaseScore();
                newDir = getBarrierBounceDirection(dir, newDir)+ Util.getRandomIntInRange(-5, 5);
                barrier.handleIncomingDamage();
            }

        }
        setRotate(newDir);
    }

    private double getBarrierBounceDirection(double dir, double newDir) {
        return dir + (180 + Util.getRandomIntInRange(-10, 10));
    }

    private double getBoardBounceDirection(double dir, double newDir) {
        if (Util.isIn90to180degrees(dir)) {
            newDir = 180 - dir;
        } else if (Util.isIn180to270degrees(dir)){
            newDir = 540 - dir;
        } else
            newDir = Util.getRandomIntInRange(290, 360 + 80);
        return newDir;
    }

    private double modifyRotationBetween0and360Degrees (double dir) {
        while (!(dir <= 360) || !(dir >=0)) {
            dir = dir > 360 ? dir - 360 : dir;
            dir = dir < 0 ? dir + 360 : dir;
        }
        return dir;
    }

    private double calculateDirectionOnBoardBounce (double horizontalDifference) {
        double newDir = 0;
        if (horizontalDifference >= -34 && horizontalDifference < 34) {
            newDir = ((horizontalDifference + 102) * (1.25) + 280) * -1; //1.25 used instead of 80/68
            newDir = modifyRotationBetween0and360Degrees(newDir);
        } else if (horizontalDifference >= -102 && horizontalDifference <= -34) {
            newDir = (horizontalDifference + 34) * (1.25);
            newDir = modifyRotationBetween0and360Degrees(newDir) * -1;
        }
        return newDir;
    }
/*
to avoid the border bugs, incoming angles outside the specified range should be handled
by returning rotation in the wanted range only (ex. in case of right border: from 180 to 360)
 */
    private double getBorderBounceDirection(double dir, double newDir, Border border) {
        dir = modifyRotationBetween0and360Degrees(dir);
        if(border.isTopBorder()) {
            Globals.ballBounce.play();
            if (Util.isIn0to90degrees(dir)) {
                newDir = 180 - dir;
            }
            else if(Util.isIn270to360degrees(dir)){
                newDir = 180 + (360-dir);
            } else
                System.out.println("X: " + getX() + "Y: " + getY() + "Rot: " + dir + "NewR: " + newDir);
        } else if (border.isBottomBorder()) {
                handleBottomBorderTouch();
        } else if (border.isRightBorder()) {
            Globals.ballBounce.play();
            if (Util.isIn0to90degrees(dir))
                newDir = 360 - dir;
            else //(Util.isIn90to180degrees(dir))
                newDir = 360 - dir;
        } else if (border.isLeftBorder()) {
            Globals.ballBounce.play();
            newDir = 360-dir;
        }
        return newDir + Util.getRandomIntInRange(-2, 2);
    }

    private void ultraBallMode () {
        Globals.ultraBrickDestroy.play();
        this.setImage(Globals.ultraBall);
        this.speed = 9;
    }

    private void endUltraMode() {
        setUltra(false);
        this.setImage(Globals.ball);
        this.speed = 7;
    }

    public void setUltra(boolean ultra) {
        isUltra = ultra;
    }

    public boolean getUltra () {
        return isUltra;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void incrementLives() {
        this.lives += 1;
    }

    private void decrementLives() {
        this.lives -= 1;
    }

    public static int getLives() {
        return lives;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Ball.score = score;
    }

    public static void resetScore() {
        Ball.setScore(0);
    }

    public static void resetLives() {
        lives = 3;
    }

    private static void resetScoreBonusSum() {
        scoreBonusSum = 0;
    }

    public static int getScoreBonusSum() {
        return scoreBonusSum;
    }

    private void handleBottomBorderTouch() {
        this.decrementLives();
        TextDisplayUtil.displayLives.setText("Lives left: " + getLives());
        resetPosAndDir();
        this.speed = 0;
        if (isGameOver()) {
            Globals.setCurrentScore(Ball.getScore());
            Globals.gameLoop.stop();
            destroy();
            WindowUtil.losePopup();
        }
    }

    private boolean isGameOver () {
        return this.getLives() < 1;
    }

    private void resetPosAndDir() {
        this.setX(500);
        this.setY(600);
        this.setRotate(Util.getRandomIntInRange(0,45));
    }

    private void increaseScore() {
        scoreBonusSum += SCORE_BONUS;
        score += scoreBonusSum + 10;
        TextDisplayUtil.displayScore.setText("Score: " + getScore());
    }

}
