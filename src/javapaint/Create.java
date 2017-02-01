package javapaint;

import java.awt.*;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Color.black;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;
import java.awt.Graphics;
import javapaint.Tools.*;

public class Create extends JFrame implements Runnable {

    private final FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;

    private Canvas canvas;
    private CreateInput mouse;
    private KeyboardInput keyboard;
    private final Point point = new Point(0, 0);

    private final ArrayList<Point> lines = new ArrayList<>();
    private final ArrayList<Shape> shapes = new ArrayList<>();
    private final ArrayList<Color> color = new ArrayList<>();
    
    FreeDraw fDraw;
    Line lDraw;
    RectangleShape rDraw;

    private boolean drawingLine;

    int current = 1;
    int actionCode = 1;
    JButton freeBrush; //1
    JButton lineBrush; //2
    JButton polyLine; //3
    JButton rectangleBrush; //4
    Point strRec, endRec, str, end;
    JButton RedB, GreenB, BlueB, BlackB;
    JPanel pannelTool;
    Box buttonHolder;
    boolean mouseDown = false;

    private final Color[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};
    private int colorIndex;

    /**
     *Set Up
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Create() {
        frameRate = new FrameRate();
        pannelTool = new JPanel();
        buttonHolder = Box.createVerticalBox();
        
        freeBrush = setButton("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/JavaPaint/src/javapaint/Images/CurvInUse.png");
        lineBrush = setButton("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/JavaPaint/src/javapaint/Images/LineNotInUse.png");
        polyLine = setButton("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/JavaPaint/src/javapaint/Images/PolyLineNotInUse.png");
        rectangleBrush = setButton("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/JavaPaint/src/javapaint/Images/RecNotInUse.png");
        RedB = setColorButton(1);
        GreenB = setColorButton(2);
        BlueB = setColorButton(3);
        BlackB = setColorButton(4);

        buttonHolder.add(freeBrush);
        buttonHolder.add(lineBrush);
        buttonHolder.add(polyLine);
        buttonHolder.add(rectangleBrush);

        
        buttonHolder.add(BlackB);
        buttonHolder.add(RedB);
        buttonHolder.add(GreenB);
        buttonHolder.add(BlueB);
        
        pannelTool.add(buttonHolder);

        this.add(buttonHolder, BorderLayout.WEST);
        disableCursor();
        this.setVisible(true);
    }
    
    /*
     *  params: @int p, is the action th icon switches to
     */
    public void changeIcon(int p) {
        switch (p) {
            case 1://freeForm //colorIndex
                freeBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/CurvInUse.png"));
                lineBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/LineNotInUse.png"));
                polyLine.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/PolyLineNotInUse.png"));
                rectangleBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/RecNotInUse.png"));

                break;
            case 2://Lines
                freeBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/CurvNotInUse.png"));
                lineBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/LineInUse.png"));
                polyLine.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/PolyLineNotInUse.png"));
                rectangleBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/RecNotInUse.png"));

                break;
            case 3: //Poly
                freeBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/CurvNotInUse.png"));
                lineBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/LineNotInUse.png"));
                polyLine.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/PolyLineInUse.png"));
                rectangleBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/RecNotInUse.png"));
                break;
            case 4://rec
                freeBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/CurvNotInUse.png"));
                lineBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/LineNotInUse.png"));
                polyLine.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/PolyLineNotInUse.png"));
                rectangleBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/RecInUse.png"));
        }
    }

    private JButton setButton(String png) {
        JButton button = new JButton();
        Icon buttonIcon = new ImageIcon(png);
        button.setIcon(buttonIcon);
        //Add Listener to JButton
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //logger.info("Action Preformed" + actionCode);
                // current = actionCode;
                Object source = e.getSource();
                if (source == freeBrush) {
                    actionCode = 1;
                    changeIcon(actionCode);
                } else if (source == lineBrush) {
                    actionCode = 2;
                    changeIcon(actionCode);
                } else if (source == polyLine) {
                    actionCode = 3;
                    changeIcon(actionCode);
                } else if (source == rectangleBrush) {
                    actionCode = 4;
                    changeIcon(actionCode);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = false;
                }
            }
        });

        button.setBackground(WHITE);
        return button;
    }

    private JButton setColorButton(int i) {
        JButton button = new JButton();

        //Add Listener to JButton
        button.addActionListener((ActionEvent e) -> {
            //logger.info("Action Preformed" + actionCode);
            //  current = colorIndex;
            Object source = e.getSource();
            if (RedB == source) {
                colorIndex = 1;
            }
            if (GreenB == source) {
                colorIndex = 2;
            }
            if (BlueB == source) {
                colorIndex = 3;
            }
            if (BlackB == source) {
                colorIndex = 4;
            }
        });
       
        switch (i) {
            case 1:
                button.setBackground(RED);
                button.setSize(30, 20);
                break;
            case 2:
                button.setBackground(GREEN);
                button.setSize(30, 20);
                break;
            case 3:
                button.setBackground(BLUE);
                button.setSize(30, 20);
                break;
            case 4:
                button.setBackground(black);
                button.setSize(30, 20);
                break;
            default:
                button.setBackground(black);
        }
        return button;
    }

/******************************************************************************/
    protected void createAndShowGUI() {
        canvas = new Canvas();
        canvas.setSize(1280, 720);
        canvas.setBackground(Color.WHITE);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setTitle("Java Paint");
        setIgnoreRepaint(true);
        pack();
       
        // Add key listeners
        keyboard = new KeyboardInput();
        canvas.addKeyListener(keyboard);

        // Add mouse listeners
        // For full screen : mouse = new RelativeMouseInput( this );
        mouse = new CreateInput(canvas);
        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);

        //Mouse wheel listener TODO: ADD the up to swtich tools and not color?
        canvas.addMouseWheelListener(mouse);
        
        init();
        setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        canvas.requestFocus();

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        running = true;
        frameRate.initialize();
        while (running) {
            gameLoop();
        }
    }

    public void init() {
         fDraw = new FreeDraw(canvas);
         lDraw = new Line(canvas);
         rDraw = new RectangleShape(canvas);
    }

    private void gameLoop() {
        processInput();
        renderFrame();
        sleep(10L);
    }

    void renderFrame() {
        do {
            do {
                Graphics g = null;
                try {
                    g = bs.getDrawGraphics();
                    g.clearRect(0, 0, getWidth(), getHeight());
                    render(g);
                } finally {
                    if (g != null) {
                        g.dispose();
                    }
                }
            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());
    }

    private void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException ex) {
        }
    } 
    
    private void processInput() {
        keyboard.poll();
        mouse.poll();
          Graphics g = bs.getDrawGraphics();
        if (canvas != null) {
            if (point.x + 25 < 0) {
                point.x = canvas.getWidth() - 1;
            } else if (point.x > canvas.getWidth() - 1) {
                point.x = -25;
            }
            if (point.y + 25 < 0) {
                point.y = canvas.getHeight() - 1;
            } else if (canvas.getHeight() - 1 > point.y) {
            } else {
                point.y = -25;
            }
        }
                
        if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
            str = mouse.getPosition();
            if(actionCode == 2 || actionCode == 3){
                lDraw.setSTR(str);
            }else if(actionCode == 4){
                rDraw.setSTR(str);
            }
            drawingLine = true;
        }
        // if the button is down, add line point
        if (mouse.buttonDown(MouseEvent.BUTTON1)) {
            mouseDown = true;
        } else if (drawingLine){
            mouseDown = false; //if mouse id down
            drawingLine = false; //if started to draw
            if(actionCode == 1){
                fDraw.setArrayLines(null);
            }else if(actionCode == 2 || actionCode == 3){
                lDraw.save(g);
            }
            else if (actionCode == 4){
                rDraw.save(g);
            }
                
        }
        
        // if 'C' is down, clear the lines
        if (keyboard.keyDownOnce(KeyEvent.VK_C)) {
            clear();
        }
        //scroll of wheel too //++ unless is at 4 them 1

        Point p = mouse.getPosition();
        if (mouse.isRelative()) {
            point.x += p.x;
            point.y += p.y;
        } else {
            point.x = p.x;
            point.y = p.y;
        }
    }
    
    private void render(Graphics g) {

        Color colors = COLORS[Math.abs(colorIndex % COLORS.length)];
        g.setColor(colors);
        
        //todo fix this line
        frameRate.calculate();
        g.drawLine(point.x + 10, point.y, point.x, point.y);
        g.drawLine(point.x, point.y + 10, point.x, point.y);
        g.drawLine(point.x, point.y, point.x - 10, point.y);
        g.drawLine(point.x, point.y, point.x, point.y - 10);

        //Call Draw
       
            if(drawingLine){
                Draw(g);
            }
            //Old ArrayList
            fDraw.AddLines(g);
            lDraw.AddShapes(g);
            rDraw.AddShapes(g);
   
    }
/***********************************************************/
    private void Draw(Graphics g){
    
        switch(actionCode){
            case 1:
                fDraw.draw(g, mouse);
                break;
            case 2: case 3:
                lDraw.draw(g, actionCode, mouseDown, mouse);
                break;
            case 4:
                rDraw.draw(g, mouseDown, mouse);
                break;
            default:
                System.out.print("Draw Error\n");
        }
    
    }
    
    private void disableCursor() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = tk.createImage("");
        Point points = new Point(0, 0);
        String name = "Pointer";
        Cursor cursor = tk.createCustomCursor(image, points, name);
        setCursor(cursor);

    }

    protected void onWindowClosing() {
        try {
            running = false;
            gameThread.join();
        } catch (InterruptedException e) {
            System.out.println("Error "+e);
        }
        System.exit(0);
    }

    protected void clear(){
    rDraw.clear();
    lDraw.clear();
    fDraw.clear();
    }
}
