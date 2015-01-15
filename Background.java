import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.IOException;
/**
 * Starter code for Image Manipulation Array Assignment.
 * 
 * The class Processor contains all of the code to actually perform
 * transformation. The rest of the classes serve to support that
 * capability. This World allows the user to choose transformations
 * and open files.
 * 
 * Add to it and make it your own!
 * 
 * @author Jordan Cohen
 * @version November 2013
 */
public class Background extends World
{
    // Constants:
    private final String STARTING_FILE = "Flowers.jpg"; // the first picutre that is opened when you open the program

    // Objects and Variables:
    private ImageHolder image;

    //required buttons
    private TextButton greyscaleButton;
    private TextButton negativeButton;

    //image orientation manipulation buttons
    private TextButton hRevButton;
    private TextButton verticalFlipButton;
    private TextButton rotateButton;

    //opening a new picutre
    private TextButton openFile;

    //Swapping colors
    private TextButton current;
    private TextButton changeInto;
    private TextButton change;

    //Shift all rgb values
    private TextButton colorShift;

    //increasing or deceasing red, green, blue, or alpha values of pixels
    private TextButton addOrSub;
    private TextButton changeBlue;
    private TextButton changeGreen;
    private TextButton changeRed;
    private TextButton changeAlpha;

    private TextButton highlight;
    private TextButton colorToKeep;

    //brightening and darkening
    private TextButton brighten;
    private TextButton darken;

    //saving and file types
    private TextButton saveAsPNG;
    private TextButton saveAsJPG;

    private TextButton reset;

    //name of the file
    private String fileName;

    /**
     * Constructor for objects of class Background. Creates all the buttons and loads the image.
     * 
     */
    public Background()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 

        // Initialize buttons and the image
        image = new ImageHolder(STARTING_FILE);
        fileName = STARTING_FILE;
        //loads buttons with the text required
        greyscaleButton = new TextButton(" [ Greyscale ] ");
        negativeButton = new TextButton (" [ Negative ] ");

        hRevButton = new TextButton(" [ Flip Horizontal ] ");
        verticalFlipButton = new TextButton(" [ Flip Vertical ] ");
        rotateButton = new TextButton(" [ Rotate 90 ] ");

        openFile = new TextButton(" [ Open File: " + STARTING_FILE + " ] ");

        current = new TextButton(" [ Red ");
        changeInto = new TextButton(" Red ] ");
        change = new TextButton (" [ Swap Colors ] ");

        colorShift = new TextButton (" [ Shift Colors ] ");

        addOrSub = new TextButton(" [ + ] ");
        changeBlue = new TextButton(" [ Blue ] ");
        changeGreen = new TextButton (" [ Green ] ");
        changeRed = new TextButton (" [ Red ] ");
        changeAlpha = new TextButton (" [ Transparency ] ");

        highlight = new TextButton (" [ Highlight ] ");
        colorToKeep = new TextButton(" [ Red ] ");

        brighten = new TextButton (" [ Brighten ] ");
        darken = new TextButton (" [ Darken ] ");

        saveAsPNG = new TextButton (" [ Save as PNG ] ");
        saveAsJPG = new TextButton (" [ Save as JPG ] ");

        reset = new TextButton (" [ Reset Image ] ");

        // Add objects to the screen
        addObject (image, 400, 250);

        addObject (greyscaleButton, 257, 500);
        addObject (negativeButton, 257, 470);

        addObject (hRevButton, 150, 470);
        addObject (verticalFlipButton, 150, 500);
        addObject (rotateButton, 360, 500);

        addObject (openFile, 700, 580);

        addObject (saveAsPNG, 730, 520);
        addObject (saveAsJPG, 730, 550);

        addObject(reset, 730, 470);

        addObject (highlight, 220, 540);
        addObject (colorToKeep, 220, 570);

        addObject (current, 70, 570);
        addObject (changeInto, 130, 570);
        addObject (change, 100, 540);

        addObject (colorShift, 360, 470);

        addObject (addOrSub, 500, 470);
        addObject (changeBlue, 500, 499);
        addObject (changeGreen, 500, 528);
        addObject (changeRed, 500, 557);
        addObject (changeAlpha, 500, 586);

        addObject (brighten, 600, 470);
        addObject (darken, 600, 500);

        prepare();
    }

    /**
     * Act() method just checks for mouse input... Not going to do anything else here
     */
    public void act ()
    {
        checkMouse();
    }

    /**
     * Check for user clicking on a button
     */
    private void checkMouse ()
    {
        // Avoid excess mouse checks - only check mouse if something is clicked.
        if (Greenfoot.mouseClicked(null))
        {
            if (Greenfoot.mouseClicked(greyscaleButton))
            {
                Processor.greyscale(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(negativeButton)){
                Processor.negative(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(hRevButton)){
                Processor.flipHorizontal(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(rotateButton)){
                image.setImage(Processor.rotate90(image.getBufferedImage()));
            }
            else if (Greenfoot.mouseClicked(openFile))
            {
                openFile ();
            }
            else if (Greenfoot.mouseClicked(verticalFlipButton)){
                Processor.flipVertical(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(saveAsPNG)){
                saveAsPNG();
            }
            else if (Greenfoot.mouseClicked(saveAsJPG)){
                saveAsJPG();
            }
            else if (Greenfoot.mouseClicked(current)){
                Processor.colorToChange(current);
            }
            else if (Greenfoot.mouseClicked(changeInto)){
                Processor.colorToTurnInto(changeInto);
            }
            else if (Greenfoot.mouseClicked(change)){
                Processor.change(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(colorShift)){
                Processor.colorShift(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(addOrSub)){
                Processor.addOrSubtract(addOrSub);
            }
            else if (Greenfoot.mouseClicked(changeBlue)){
                Processor.changeBlue(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(changeGreen)){
                Processor.changeGreen(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(changeRed)){
                Processor.changeRed(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(brighten)){
                Processor.brighten(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(darken)){
                Processor.darken(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(changeAlpha)){
                Processor.changeAlpha(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(reset)){
                image.setImage(fileName);
            }
            else if (Greenfoot.mouseClicked(colorToKeep)){
                Processor.colorToKeep(colorToKeep);
            }
            else if (Greenfoot.mouseClicked(highlight)){
                Processor.highlight(image.getBufferedImage());
            }
        }
    }

    /**
     * Allows the user to open a new image file.
     */
    private void openFile ()
    {
        // Use a JOptionPane to get file name from user
        fileName = JOptionPane.showInputDialog("Please enter the name of the file you wish to open.");

        // If the file opening operation is successful, update the text in the open file button
        if (image.openFile (fileName))
        {
            String display = " [ Open File: " + fileName + " ] ";
            openFile.update (display);
        }
    }

    /**
     * 
     * Allows the user to save the current picture as a .png file.
     */
    public void saveAsPNG(){
        String fileName = JOptionPane.showInputDialog("Input file name (no extension)"); //prompts user to input the file name
        fileName+= ".png";
        // creates and saves the file in the same folder as this greenfoot project
        File f = new File (fileName);
        try{
            ImageIO.write(image.getImage().getAwtImage(), "png", f);
        } catch (IOException e){}
    }

    /**
     * 
     * Allows the user to save the current picture as a.jpg file.
     */
    public void saveAsJPG(){
        String fileName = JOptionPane.showInputDialog("Input file name (no extension)"); //prompts user to input file name
        fileName+= ".jpg";
        //creates a new BufferedImage of a different type that has no alpha layer, since jpgs do not have them
        BufferedImage b = new BufferedImage (image.getImage().getWidth(), image.getImage().getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < b.getWidth(); i++){
            for (int j = 0; j < b.getHeight(); j++){
                int rgb = image.getBufferedImage().getRGB(i, j);
                b.setRGB(i, j, rgb);
            }
        }
        //writes the file and saves it
        File f = new File (fileName);
        try {
            ImageIO.write(b, "jpg", f);
        } catch (IOException e) {}
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
    }
}