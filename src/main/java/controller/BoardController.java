package controller;

import model.Ball;
import model.BoardManager;
import model.Gizmo;
import model.Gizmos.Circle;
import model.Gizmos.Flipper;
import model.Gizmos.Square;
import model.Gizmos.Triangle;
import model.IElement;
import view.GizmoBallView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bairdjb on 11/02/2016.
 */
public class BoardController {
    private BoardManager boardManager;
    private GizmoBallView view;

    public BoardController(){
        boardManager = new BoardManager();
        view = new GizmoBallView(boardManager);
        boardManager.getBoard().addObserver(view);
        test();
    }

    //TODO Remove test method
    private void test(){
        Gizmo test1 = new Square(3, 0, "Test");
        Gizmo test2 = new Triangle(2, 6, "Test");
        Gizmo test3 = new Square(1, 6, "Test");
        Gizmo test4 = new Square(1, 2, "Test");
        Gizmo test5 = new Circle(2, 1, "Test");
        Flipper test6 = new Flipper(5, 3, "Test");
        test6.addKeyPressTrigger(KeyEvent.VK_SPACE);
        test6.setColor(Color.GREEN);
        List<IElement> testShapes = Arrays.asList(new IElement[]{test1, test2, test3, test4, test5, test6});
        boardManager.getBoard().setElements(testShapes);
        Ball ball = new Ball("Ball", 3.5, 7, -5.0, -5.0);

        Ball ball2 = new Ball("Ball", 2.5, 3, -5.0, -5.0);
        boardManager.getBoard().addBall(ball);
        boardManager.getBoard().addBall(ball2);
    }


}
