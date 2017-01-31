package javapaint;

import java.awt.Color;
import java.awt.Point;

public class ColorPoint extends Point {
	private Color color;

	public ColorPoint(Point p, Color c) {
		super(p);
		color = c;
	}

}