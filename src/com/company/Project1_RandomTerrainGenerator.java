package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Project1_RandomTerrainGenerator {
    //references to some variables we may want to access in a global context
    static int WIDTH = 500; //width of the image
    static int HEIGHT = 500; //height of the image
    static BufferedImage Display; //the image we are displaying
    static JFrame window; //the frame containing our window


    public static void main(String[] args) {
        //run the GUI on the special event dispatch thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Create the window and set options
                //The window
                window = new JFrame("RandomWalker");
                window.setPreferredSize(new Dimension(WIDTH+100,HEIGHT+50));
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
                window.pack();


                //Display panel/image
                JPanel DisplayPanel = new JPanel();
                Display = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
                DisplayPanel.add(new JLabel(new ImageIcon(Display)));
                window.add(DisplayPanel,BorderLayout.CENTER);

                //Config panel
                JPanel Configuration = new JPanel();
                Configuration.setBackground(new Color(230,230,230));
                Configuration.setPreferredSize(new Dimension(100,500));
                Configuration.setLayout(new FlowLayout());

                //Step count input
                JLabel StepCountLabel = new JLabel("Step Count:");
                Configuration.add(StepCountLabel);

                JTextField StepCount = new JTextField("500");
                StepCount.setPreferredSize(new Dimension(100,25));
                Configuration.add(StepCount);

                //Walker type input
                JLabel WalkerType = new JLabel("Walker Type:");
                Configuration.add(WalkerType);

                ButtonGroup WalkerTypes = new ButtonGroup();//group of buttons
                JRadioButton Standard = new JRadioButton("Standard");//creates a radio button. in a ButtonGroup, only one can be selected at a time
                Standard.setActionCommand("standard");//can be grabbed to see which button is active
                Standard.setSelected(true);//set this one as selected by default
                JRadioButton Picky = new JRadioButton("Picky");
                Picky.setActionCommand("picky");
                WalkerTypes.add(Standard); //add to panel
                WalkerTypes.add(Picky);
                Configuration.add(Standard); //set as part of group
                Configuration.add(Picky);

                //Walker type input
                JLabel Geometry = new JLabel("World Geometry:");
                Configuration.add(Geometry);
                ButtonGroup Geometries = new ButtonGroup();
                JRadioButton Bounded = new JRadioButton("Bounded");
                Bounded.setActionCommand("bounded");
                Bounded.setSelected(true);
                JRadioButton Toroidal = new JRadioButton("Toroidal");
                Toroidal.setActionCommand("toroidal");
                Geometries.add(Bounded);
                Geometries.add(Toroidal);
                Configuration.add(Bounded);
                Configuration.add(Toroidal);

                //Path Rendering input
                JLabel Render = new JLabel("Path Render:");
                Configuration.add(Render);
                ButtonGroup Renderers = new ButtonGroup();
                JRadioButton Black = new JRadioButton("Black");
                Black.setActionCommand("black");

                Black.setSelected(true);
                Renderers.add(Black);
                Configuration.add(Black);

                //Run Button
                JButton Run = new JButton("Walk");
                Run.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        UpdateDisplay(Display);
                        int count = Integer.parseInt(StepCount.getText()); //get from a TextField, and read as an int
                        String walk_type = WalkerTypes.getSelection().getActionCommand();//gets the action command of which radio button is selected
                        String geom_type = Geometries.getSelection().getActionCommand();
                        String render_type = Renderers.getSelection().getActionCommand();

                        //===Walk, Update Display, repaint===
                        //1. Generate a Buffered image using the data from above (return one from a method)?
                        //2. UpdateDisplay([[insert the image you made]]);
                        //generate a buffer image
                        int randX = 0;
                        int randY = 0;
                        Random rand = new Random();
                        int x = WIDTH/2-1;
                        int y = HEIGHT/2-1;

                        if(walk_type == "standard"){
                            for(int i = 0; i<count; i++){

                                randX = rand.nextInt( 2-(-1))-1;
                                randY = rand.nextInt( 2-(-1))-1;
                                x = x + randX;
                                y = y + randY;

                                if(geom_type == "toroidal"){
                                    //Toroidal
                                    if(x >= WIDTH ){
                                        x = 0;
                                    }
                                    if(x < 0 ){
                                        x = WIDTH-1;
                                    }
                                    if(y >= HEIGHT ){
                                        y = 0;
                                    }
                                    if(y<0 ){
                                        y = HEIGHT-1;
                                    }
                                }

                                if(geom_type == "bounded"){
                                    if(x >= WIDTH || x < 0 ){
                                        x -= randX;
                                    }
                                    if(y >= HEIGHT || y<0){
                                        y -= randY;
                                    }

                                }

                                //System.out.println(x + " " + y);
                                Display.setRGB(x, y, Color.BLACK.getRGB());
                            }
                        }

                        if(walk_type == "picky"){

                            Random rand2 = new Random();
                            int randSteps = 0;
                            for(int i = 0; i<count; i++){
                                //picks random direction
                                randX = rand.nextInt( 2-(-1))-1;
                                randY = rand.nextInt( 2-(-1))-1;
                                x = x + randX;
                                y = y + randY;

                                //pick random # of steps
                                randSteps = rand2.nextInt(11-1) + 1;
                                for(int j = 0; j<randSteps; j++){
                                    x = x + randX;
                                    y = y + randY;
                                    if(geom_type == "toroidal"){
                                        //Toroidal
                                        if(x >= WIDTH ){
                                            x = 0;
                                        }
                                        if(x < 0 ){
                                            x = WIDTH-1;
                                        }
                                        if(y >= HEIGHT ){
                                            y = 0;
                                        }
                                        if(y<0 ){
                                            y = HEIGHT-1;
                                        }
                                    }

                                    if(geom_type == "bounded"){
                                        if(x >= WIDTH || x < 0 ){
                                            x -= randX;
                                        }
                                        if(y >= HEIGHT || y<0){
                                            y -= randY;
                                        }

                                    }
                                    Display.setRGB(x, y, Color.BLACK.getRGB());
                                }
                            }
                        }

                        window.repaint();
                    }
                });
                Configuration.add(Run);
                window.add(Configuration,BorderLayout.EAST);

            }
        });
    }

    //A method to update the display image to match one generated by you
    static void UpdateDisplay(BufferedImage img){
        //Below 4 lines draws the input image on the display image
        Graphics2D g = (Graphics2D) Display.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.drawImage(img,0,0,null);

        //forces the window to redraw its components (i.e., the image)
        window.repaint();
    }

}
































