/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapaint.Tools;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import javapaint.CreateInput;

/**
 *
 * @author carol_8wybosj
 */
public class RectangleShape extends Tools{
    
    private final ArrayList<Rectangle> shapesBuffer = new ArrayList<>();
    private final ArrayList<Rectangle> shapes = new ArrayList<>();
    private final ArrayList<Color> color = new ArrayList<>();
    public Point str = new Point(0, 0), end = new Point(0, 0);    
    private final Point point = new Point(0, 0);
    private final CreateInput mouse;
    Rectangle rectangle;
    
    public void setEND(Point end){
        this.end = end;
    }
 
    public RectangleShape(Canvas canvas){
        mouse = new CreateInput(canvas);
    }      
    
    public void setSTR(Point str){
        this.str = str;
    }
    
    //Todo: delete not in use
    public void draw(Graphics g, boolean relase, CreateInput mouse) {
       
        int xValue = Math.min(str.x, point.x);
        int yValue = Math.min(str.y, point.y);
        int width = Math.abs(str.x - point.x);
        int height = Math.abs(str.y - point.y);
                
        
        rectangle = new Rectangle(xValue, yValue, width, height);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    
    }
    
    public void save(Graphics g){
        int xValue = Math.min(str.x, end.x);
        int yValue = Math.min(str.y, end.y);
        int width = Math.abs(str.x - end.x);
        int height = Math.abs(str.y - end.y);
        rectangle = new Rectangle(xValue, yValue, width, height);
       
        g.setColor(g.getColor());
        shapes.add(rectangle);
        System.out.println("Saved"+ rectangle.x);
        //Add action Listener for the relase save
        
    }
    
    //not used
     public void save(Graphics g,Rectangle rectangleInput){
        g.setColor(g.getColor());
        shapes.add(rectangleInput);
        //Add action Listener for the relase save   
    }
    
    public void AddShapes(Graphics g){
      
        for (int i = 0; i < shapes.size() - 1; i++) {
        System.out.print(shapes.size()+", boc \n");
              Rectangle r = shapes.get(i);
            // Adding a null into the list is used
            // for breaking up the lines when
            // there are two or more lines
            // that are not connected
                if (!(shapes == null)&& color.get(i)!=null) {
                    g.setColor(color.get(i));   
                    g.drawRect(r.x, r.y, r.width, r.height);
                }
            }
    }

    @Override
    public void clear() {
        shapes.clear();
        color.clear();
    }
}
