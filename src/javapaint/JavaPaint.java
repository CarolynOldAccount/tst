/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapaint;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import static java.lang.Thread.sleep;
import javax.swing.SwingUtilities;

/**
 *
 * @author carol_8wybosj
 */
public class JavaPaint {

    /**
     *MAIN
     */
    public static Create app;
   private FrameRate frameRate;
   
   public static void main(String[] args) throws IOException {
       app = new Create();
        if(app!=null){   
      app.addWindowListener( new WindowAdapter() {
        
          public void windowClosing( WindowEvent e ) {
            app.onWindowClosing();
         }
      });
      
      SwingUtilities.invokeLater( new Runnable() {
         public void run() {
             app.createAndShowGUI();      
         }
         
      });
      
        } 
    }
   
    
   }