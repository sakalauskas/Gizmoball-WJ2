package model.gizmos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Gizmo;
import model.IElement;
import model.Triggerable;
import physics.LineSegment;
import physics.Vect;

/**
 * Gizmoball - Square
 * Created by Group WJ2 on 06/02/2016.
 * Authors: J Baird, C Bean, N Stannage, U Akhtar, L Sakalauskas
 */
public class Square extends Gizmo {
	private List<Vect> coordinates;
	private List<String> connections = new ArrayList<>();
	private List<String> keyConnects = new ArrayList<>();
	private String saveInfo;
	private final String name;
	private int rotation;

	/**
	 * Contruct a new Square.
	 * 
	 * @param origin
	 * @param name
	 */
	public Square(Vect origin, String name) {
		super(origin, name);
		this.name = name;
		rotation = 0;
		coordinates = calculateCoordinates();
		super.setCircles(calculateCircles());
		super.setLines(calculateLines());
		super.setColor(new Color(0xc0392b));
		saveInfo = "Square" + " " + name + " " + (int) origin.getXCoord() + " " + (int) origin.getyCoord();
	}

	@Override
	public void trigger() {

	}

	/**
	 * Construct a new Square.
	 * 
	 * @param x
	 * @param y
	 * @param name
	 */
	public Square(int x, int y, String name) {
		this(new Vect(x, y), name);
	}

	/**
	 * Calculate this Element's coordinates.
	 * 
	 * @return this Element's coordinates.
	 */
	private List<Vect> calculateCoordinates() {
		Vect topLeft = origin;
		Vect topRight = new Vect(bound.x(), origin.y());
		Vect bottomRight = bound;
		Vect bottomLeft = new Vect(origin.x(), bound.y());
		return Arrays.asList(topLeft, topRight, bottomRight, bottomLeft);
	}

	/**
	 * Calculate this Element's circles.
	 * 
	 * @return this Element's circles.
	 */
	private List<physics.Circle> calculateCircles() {
		List<physics.Circle> calcCircles = new ArrayList<>();
		for (Vect coord : coordinates) {
			physics.Circle circle = new physics.Circle(coord, 0);
			calcCircles.add(circle);
		}
		return calcCircles;
	}

	/**
	 * Calculate this Element's lines.
	 * 
	 * @return this Element's lines.
	 */
	private List<LineSegment> calculateLines() {
		List<LineSegment> calcLines = new ArrayList<>();
		for (int i = 0; i < coordinates.size(); i++) {
			Vect a = coordinates.get(i);
			Vect b = coordinates.get((i + 1) % coordinates.size());
			LineSegment line = new LineSegment(a, b);
			calcLines.add(line);
		}
		return calcLines;
	}

	@Override
	public void rotate() {
		Vect centerPoint = getCenterPoint();
		rotation = (rotation + 1) % 4;
		//setSaveInfo();
		for (int i = 0; i < coordinates.size(); i++) {
			coordinates.set(i, rotationMatrix(coordinates.get(i), centerPoint));
		}
		super.setCircles(calculateCircles());
		super.setLines(calculateLines());
	}

	/**
	 * @return the square's center point
	 */
	public Vect getCenterPoint() {
		double width = bound.x() - origin.x();
		double height = bound.y() - origin.y();
		return origin.plus(new Vect(width / 2, height / 2));
	}

	@Override
	public Vect calculateBound() {
		Vect origin = super.getOrigin();
		Vect bound = new Vect(1, 1);
		return origin.plus(bound);
	}

	@Override
	public List<Vect> getCoordinates() {
		return coordinates;
	}

	@Override
	public String getSaveInfo() {
		return saveInfo;
	}

	@Override
	public int getRotation() {
		return rotation;
	}

	@Override
	public void move(Vect distance) {
		super.origin = super.origin.plus(distance);
		super.bound = super.bound.plus(distance);
		coordinates = calculateCoordinates();
		super.setCircles(calculateCircles());
		super.setLines(calculateLines());
		saveInfo = "Square" + " " + name + " " + (int) origin.getXCoord() + " " + (int) origin.getyCoord();
	}

	@Override
	public boolean equals(Object other) {
		if (other.getClass() != Square.class) {
			return false;
		}
		//We know that its a square
		Square otherSquare = (Square) other;

		if (!origin.equals(otherSquare.getOrigin())) {
			return false;
		}
		if (!bound.equals(otherSquare.getBound())) {
			return false;
		}
		if (rotation != otherSquare.getRotation()) {
			return false;
		}
		return coordinates.equals(otherSquare.getCoordinates());
	}

	@Override
	public void gizmoConnect(IElement secondElement){
		this.addTriggerable((Triggerable) secondElement);
		connections.add("Connect " +this.getName()+ " "+ secondElement.getName());
	}

	@Override
	public List<String> getConnections() {
		return connections;
	}

	@Override
	public void addKeyConnect(int keycode){
		this.addKeyPressTrigger(keycode);
		keyConnects.add("KeyConnect Key "+ keycode+ " change "+ this.getName());
	}

	@Override
	public List<String> returnKeyConnects(){
		return keyConnects;
	}

	@Override
	public void clearConnections() {
		connections.clear();
		this.clearTriggerable();
	}

	@Override
	public void clearKeyConnections() {
		keyConnects.clear();
		this.clearKeyPressTrigger();
	}

	@Override
	public void removeConnection(IElement element) {
		for (String connect : connections) {
			if (connect.contains(element.getName())) {
				connections.remove(connect);
			}
		}
	}

}