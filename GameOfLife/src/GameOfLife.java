
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

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
    private Timer t; // THIS IS USED TO HEAVILY OPTIMIZE LOADING TIME FOR START/STOP
    private LifeGrid g;
    private int timeStep;
    private boolean isRunning;
    private JTextField preset;
    private JButton save;
    private final String saveLabelText = "Save";
    public int gen = 0;
 
    
    public GameOfLife() {
//    	g = new LifeGrid(50, 50); // MIGHT CAUSE ERRORS JUST CHECK
    	isRunning = false;
        display = new LifeCanvas(this);
        display.setBackground(Color.DARK_GRAY);
        controls = new LifeControls(this);
        
        Container c = getContentPane();
        c.add(display, BorderLayout.CENTER);
        c.add(controls, BorderLayout.SOUTH);
        
        // initializing the timer and adding a set delay
        // soon this can be edited via gui and accessibility
        timeStep = 1000; // Adjust the initial delay as needed; DON'T USE SETSPEED BECAUSE OF ERRORS
        // timeStep is init to 1000 because Choice is already set to Slow
        t = new Timer(timeStep, this); // again, inits timer with a delay and an ActionListener
        
        // makes a "Loading..." text in between delays of changing speed (SCRAPPED)
        // loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
     
    }
    
    public void setSpeed(int millisecs) // use this for gui
    {
        timeStep = millisecs;
        t.setDelay(timeStep);
        t.restart();
    	
    }
    
    public void next()
    {
        display.next();
        gen++;
    	controls.setGeneration(gen);
        repaint();
    }
    
    public void stop()
    {
        t.stop();
        isRunning = false;
    }
    
    public void start()
    {
    	gen++;
    	controls.setGeneration(gen);
    	
    	if (!isRunning) {
    		
            // Initialize the grid in a background thread
            SwingUtilities.invokeLater(() -> {
            	
            	g = new LifeGrid(50, 50);
            	
            	display.next();  // actually displays the next grid
            	// this was put in the evolve() code because it 
            	// actually saves the grid in real time, rather than in another file (which is GameOfLife.java in this case)
//            	g.savePreset(); 
            	
                t.start();  // Start the timer
                repaint();
                
                isRunning = true;
            });
        }
    }
    
    public void clear() {
    	gen = 0;
    	controls.setGeneration(gen);
    	display.clear();
    	
    }
    
    public void save() {
    	display.updateSave();
    }
    
    public void load(String text) {
    	display.loadPreset(text);
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
      window.setTitle("Game of Life but whimsical");
      window.setBounds(100, 100, 800, 600);
      window.setDefaultCloseOperation(EXIT_ON_CLOSE);
      window.setResizable(false);
      window.setVisible(true);
      controls.stop();
      
      System.out.println("Hi!");
    }
    
}
