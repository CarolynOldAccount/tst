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
import java.awt.Shape;
import java.util.ArrayList;
import javapaint.Create;
import javapaint.CreateInput;

/**
 *
 * @author carol_8wybosj
 */
public class Line extends Tools{

    @Override
    public void clear() {
        shapes.clear();
        color.clear();
    }

    public void save(Graphics g, Create.PointPair s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public class PointPair
    {
        Point left;
        Point right;
        
        public PointPair(Point l, Point r)
        {
            left = l;
            right = r;
        }
    }
    
    private ArrayList<PointPair> shapes = new ArrayList<PointPair>();
    private ArrayList<Color> color = new ArrayList<Color>();
    public Point str, end;    
    private Point point;
    private CreateInput mouse;
    
    public void setSTR(Point str){
        this.str = str;
    }
    public void setEND(Point end){
        this.end = end;
    }
     
  public Line(Canvas canvas){
    mouse = new CreateInput(canvas);
  }     
      
    //TODO: Not used delete
    public void draw(Graphics g, int code, CreateInput mouse){ 
    
        if(code == 1){ // line
             g.drawLine(str.x, str.y, point.x, point.y);
            //shapes.add(new PointPair(str, point));
           // color.add(g.getColor());
        }else{ //poly
             g.drawLine(str.x, str.y, point.x, point.y);
          //  shapes.add(new PointPair(str, point));
           // color.add(g.getColor());
        }
        end = point;
    }
 
     public void save(Graphics g){
         color.add(g.getColor());
         shapes.add(new PointPair(str,end));
        System.out.print("Line save: "+str+","+end+"\n");
    }
     
    public void AddShapes(Graphics g){
    for (int i = 0; i < shapes.size() - 1; i++){
            // Adding a null into the list is used
            // for breaking up the lines when
            // there are two or more lines
            // that are not connected
           if (!(shapes == null)) {
                g.setColor(color.get(i));
                g.drawLine(shapes.get(i).left.x, shapes.get(i).left.y, 
                        shapes.get(i).right.x, shapes.get(i).right.y);
                
            }
        }
    }

}
