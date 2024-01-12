import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * Write a description of class LifeControls here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LifeControls extends JPanel implements KeyListener, ChangeListener
{
    private JLabel genLabel;
	private final String genLabelText = "Generations: ";
	private final String slow = "Slow";
	private final String fast = "Fast";
	private final String hyper = "Hyper";
	private final String custom = "Custom";
	private final String nextLabelText = "Next";
	private final String startLabelText = "Start";
	private final String stopLabelText = "Stop";
	private final String clearLabelText = "Clear";
	private final String saveLabelText = "Save";
	private JButton go;
	private JButton nextButton;
	private JButton clear;
	private JButton save;
	private JTextField load;
	
	private JSlider speed;
	private final int SPEED_MIN = 5;
	private final int SPEED_MAX = 1000;
	private final int SPEED_INIT = 250;
	
	
	private LifeGrid grid;
	private LifeCanvas display;
	private GameOfLife app;

  public LifeControls(GameOfLife app)
  {
      this.app = app;
      grid = new LifeGrid(50, 50);
      display = new LifeCanvas(app);
      
      // pulldown menu with speeds
		Choice speedChoice = new Choice();
		
		// add custom speed slider
				speed = new JSlider(JSlider.HORIZONTAL, SPEED_MIN, SPEED_MAX, SPEED_INIT);
				speed.addChangeListener(this);
				speed.setMajorTickSpacing(SPEED_INIT);
				speed.setMinorTickSpacing((int)SPEED_INIT-(SPEED_INIT/2));
				speed.setPaintTicks(true);
				speed.setPaintLabels(true);
				speed.setVisible(false);
				// actually adds the GUI
				add(speed);
				
		// add speeds
		speedChoice.addItem(slow);
		speedChoice.addItem(fast);
		speedChoice.addItem(hyper);
		speedChoice.addItem(custom);
		
		
		
		// when item is selected
		speedChoice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String arg = (String) e.getItem();
				if (slow.equals(arg)) { // slow
					getGameOfLife().setSpeed(1000);
					speed.setVisible(false); // removes slider option if "Custom is not highlighted
					stop(); // stops app upon change & sets button to "Start"
					app.stop();
				}
				else if (fast.equals(arg)) { // fast
					getGameOfLife().setSpeed(100);
					speed.setVisible(false); // removes slider option if "Custom is not highlighted
					stop();
					app.stop();
				}
				else if (hyper.equals(arg)) { // hyperspeed
					getGameOfLife().setSpeed(10);
					speed.setVisible(false); // removes slider option if "Custom is not highlighted
					stop();
					app.stop();
				} else if (custom.equals(arg)) {
//					boolean isEnabled = selectedOption.equals("");
//					speed.setVisible(!speed.isVisible()); // custom value
					
					speed.setVisible(true);
					stop();
					app.stop();
				}
			}
		});
	
		// number of generations
		genLabel = new JLabel(genLabelText+"0             ");
	
		// start and stop button
		go = new JButton(stopLabelText);
			
		// when start/stop button is clicked
		go.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					startStopButtonClicked();
				}
			}
		);
	
		// next generation button
		nextButton = new JButton(nextLabelText);
			
		// when next button is clicked
		nextButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					app.stop();
					stop();
					getGameOfLife().next(); // stops 
					
				}
			}
		);
		
		// clear grid button
		clear = new JButton(clearLabelText);
		
		// when clear button is clicked
		clear.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						getGameOfLife().clear();
					}
				}
			);
		
		
		// save grid button
     	save = new JButton(saveLabelText);
     				
     	// when save button is clicked
     	save.addActionListener(
     			new ActionListener() {
     				public void actionPerformed(ActionEvent e) {
     					// System.out.println("WOOHOO!!!");
     					app.save();
     				}
     			}
     		);
				
		// load preset input text field
		load = new JTextField(20);
		
		load.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String text = load.getText();
						app.load(text);
						
					}
				}
			);

		
	
		// create panel with controls
		// adds buttons too
		this.add(nextButton);
		this.add(go);
		this.add(clear);
		this.add(speedChoice);
		this.add(genLabel);
		this.add(save); 
		this.add(load);
		
		
		
		// Save Button :DDDDDD
		
		
		
		this.validate();
	
      
  }
  
  public String getLoadText() {
	  return load.getText();
  }
   
  public void stateChanged(ChangeEvent e) {
	  JSlider source = (JSlider)e.getSource();
	  
	  if (!source.getValueIsAdjusting()) {
		  int speed = (int)source.getValue();
		  
//		  app.setSpeed(speed);
		  getGameOfLife().setSpeed(speed);
		  
		  // this stops the app from running on each change but it's not necessary
//		  stop();
//		 app.stop();
		  
	  }
  }
  

  /**
   *  Implement KeyListener interface:
   */
  public void keyPressed (KeyEvent e)
  {
    int code = e.getKeyCode();

    switch(code)
    {
      case KeyEvent.VK_ENTER:
        
        break;

      case KeyEvent.VK_SPACE:
        
        break;

      case KeyEvent.VK_UP:
      case KeyEvent.VK_KP_UP:
        
        break;

      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_KP_DOWN:
        
        break;

      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_KP_LEFT:
        
        break;

      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_KP_RIGHT:
        
        break;

      case KeyEvent.VK_S:
        break;

      case KeyEvent.VK_F:
        
        break;
    }
  }

  public void keyReleased (KeyEvent e)
  {
  }

  public void keyTyped (KeyEvent e)
  {
  }

	/**
	 * Set the number of generations in the control bar.
	 * @param generations number of generations
	 */
	public void setGeneration( int generations ) {
		genLabel.setText(genLabelText + generations);
	}
	
	/**
	 * Change the label of the start/stop-button to "Stop".
	 */
	public void start() {
		go.setText(stopLabelText);
	}

	/**
	 * Change the label of the start/stop-button to "Start".
	 */
	public void stop() {
		go.setText(startLabelText);
//		stop();
	}

	/**
	 * Called when the start/stop-button is clicked.
	 */
	public void startStopButtonClicked() {
		if ( app.isRunning() ) {
			app.stop();
			stop();
		} else {
		    start();
			app.start();
		}
	}

	/**
	 * Return GameOfLife applet object.
	 * @return GameOfLife applet object
	 */
	public GameOfLife getGameOfLife() {
		return app;
	}


  /**
   *  Control panel's drawing method
   */
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

   }
}
