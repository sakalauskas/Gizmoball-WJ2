package model.gizmos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Gizmo;
import model.IElement;
import physics.LineSegment;
import physics.Vect;

/**
 * Gizmoball - Wall
 * Created by Group WJ2 on 06/02/2016.
 * Authors: J Baird, C Bean, N Stannage, U Akhtar, L Sakalauskas
 */
public class Wall extends Gizmo {

	private final Vect bound;
	private final java.util.List<Vect> coordinates;

	/**
	 * Construct a new Wall.
	 * 
	 * @param origin
	 * @param bnd
	 * @param name
	 */
	public Wall(Vect origin, Vect bnd, String name) {
		super(origin, name);
		this.bound = bnd;
		super.setBound(bnd);
		coordinates = calculateCoordinates();
		super.setCircles(calculateCircles());
		super.setLines(calculateLines());
		super.setColor(Color.BLACK);
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

	/**
	 * Move this Wall (does nothing)
	 */
	@Override
	public void move(Vect distance) {

	}

	/**
	 * rotate this Wall (does nothing)
	 */
	@Override
	public void rotate() {
		// TODO Auto-generated method stub

	}

	@Override
	public Vect calculateBound() {
		return bound;
	}

	@Override
	public String getSaveInfo() {
		return "Wall";
	}

	@Override
	public int getRotation() {
		return 0;
	}

	@Override
	public List<Vect> getCoordinates() {
		return coordinates;
	}

	@Override
	public boolean equals(Object other) {
		if (other.getClass() != Wall.class) {
			return false;
		}
		//We know that its a wall
		Wall otherWall = (Wall) other;

		if (!origin.equals(otherWall.getOrigin())) {
			return false;
		}
		if (!bound.equals(otherWall.getBound())) {
			return false;
		}
		return coordinates.equals(otherWall.getCoordinates());
	}

	@Override
	public void gizmoConnect(IElement secondElement){

	}

	@Override
	public List<String> getConnections(){
		return new ArrayList<>();
	}

	@Override
	public void addKeyConnect(int keycode){

	}

	@Override
	public List<String> returnKeyConnects(){
		return new ArrayList<>();
	}

	@Override
	public void clearConnections() {
	}

	@Override
	public void clearKeyConnections() {
	}

	@Override
	public void removeConnection(IElement element) {

	}
}
