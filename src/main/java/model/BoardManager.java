package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import javax.sound.sampled.Line;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by baird on 06/02/2016.
 */
public class BoardManager {
    private Board board;

    public BoardManager(){
        board = new Board(0,0,20,20);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void tick() {
        List<Ball> newBalls = new ArrayList<>();
        for (Ball ball : board.getBalls()) {
            newBalls.add(moveBall(ball));
        }
        board.setBalls(newBalls);
    }

    private Ball moveBall(Ball ball) {
        double moveTime = 0.05; //20 FPS
        Collision collision = getTimeTillCollision(ball);

        if (collision.getTime() >= moveTime) { //No Collision
            ball = moveBallForTime(ball, moveTime);
        } else { //Collision
            ball = moveBallForTime(ball, collision.getTime());
            ball.setVelocity(collision.getVelocity());
            System.out.println("New Veloctiy:" + collision.getVelocity());
        }
        System.out.println("Ball Center: " + ball.getCenter().toString());
        return ball;
    }

    private Ball moveBallForTime(Ball ball, double time) {
        double newX, newY;
        double velX, velY;

        velX = ball.getVelocity().x();
        velY = ball.getVelocity().y();

        newX = ball.getCenter().x() + (velX * time);
        newY = ball.getCenter().y() + (velY * time);
        ball.setCenter(new Vect(newX, newY));
        ball.setVelocity(new Vect(velX, velY));

        return new Ball("Ball", newX, newY, velX, velY);
    }

    private Collision getTimeTillCollision(Ball bll) {
        Circle ballC = bll.getCircle();
        Vect ballV = bll.getVelocity();
        Vect newV = new Vect(0, 0);
        IElement collidingElement;
        double shortestTime = Double.MAX_VALUE;
        double time = 0.0;
        for (IElement element : board.getElements()) {

            for (Circle circle : element.getCircles()) {
                time = Geometry.timeUntilCircleCollision(circle, ballC, ballV);
                if (time < shortestTime) {
                    shortestTime = time;
                    collidingElement = element;
                    element.setColor(Color.BLUE);
                    //System.out.println("On course to colide with Circle: " + circle.getCenter() + " in time: " + time);
                    newV = Geometry.reflectCircle(circle.getCenter(), ballC.getCenter(), ballV);
                }
            }
            for (LineSegment line : element.getLines()) {
                time = Geometry.timeUntilWallCollision(line, ballC, ballV);
                if (time < shortestTime) {
                    shortestTime = time;
                    collidingElement = element;
                    element.setColor(Color.GREEN);

                    //System.out.println("On course to colide with Line: " + line.toString() + " in time: " + time);
                    newV = Geometry.reflectWall(line, ballV);
                }

            }
        }
        System.out.println("Collision in " + shortestTime);
        return new Collision(newV, shortestTime);
    }
}