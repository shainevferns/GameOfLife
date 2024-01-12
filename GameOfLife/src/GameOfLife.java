
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Write a description of class GameOfLife here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOfLife extends JFrame implements ActionListener

{
    private LifeCanvas display;
    private static LifeControls controls;
    private Timer t;
    private LifeGrid g;
    private int timeStep;
    private boolean isRunning;
    
    public GameOfLife() {
    	isRunning = false;
        display = new LifeCanvas(this);
        display.setBackground(Color.DARK_GRAY);
        controls = new LifeControls(this);
        
        Container c = getContentPane();
        c.add(display, BorderLayout.CENTER);
        c.add(controls, BorderLayout.SOUTH);
        
        
       timeStep = 3000;
       t = new Timer(timeStep, this);
    }
    
    public void setSpeed(int millisecs)
    {
        timeStep = millisecs;
        t.setDelay(timeStep);
        t.restart();
    }
    
    public void next()
    {
        display.next();
        repaint();
    }
    
    public void stop()
    {
        t.stop();
        isRunning = false;
    }
    
    public void start()
    {
        t.start();
        isRunning = true;
    }
    
    public void clear() {
    	display.clear();
    }
    
    /**
	 * Is the program running?
	 * @return true: program is running
	 */
	public boolean isRunning() {
		return isRunning;
	}
     
   
    /* Timer Event */
    public void actionPerformed(ActionEvent e)
    {
         next();
    }

    public static void main(String[] args)
    {
      GameOfLife window = new GameOfLife();
      window.setTitle("Game of Life 1.0");
      window.setBounds(100, 100, 600, 400);
      window.setDefaultCloseOperation(EXIT_ON_CLOSE);
      window.setResizable(false);
      window.setVisible(true);
      controls.stop();
      
      System.out.println("Hi!");
    }
    
}
