/**
 * Starter code for Processor - the class that processes images.
 * <p>
 * This class manipulated Java BufferedImages, which are effectively 2d arrays
 * of pixels. Each pixel is a single integer packed with 4 values inside it.
 * <p>
 * I have included two useful methods for dealing with bit-shift operators so
 * you don't have to. These methods are unpackPixel() and packagePixel() and do
 * exactly what they say - extract red, green, blue and alpha values out of an
 * int, and put the same four integers back into a special packed integer. 
 * 
 * @author Jordan Cohen 
 * @version November 2013
 */

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import greenfoot.*;

public class Processor  
{
    private static int currentColor = 0; //keeps track of the first color for swap
    private static int colorChangeTo = 0; // keeps track of the 2nd color for swap
    private static int colorToKeep = 0; //keeps track of the color to keep when highlighting
    private static boolean changeRed = true; //when true, red is the first color
    private static boolean changeGreen = false; //when true, green is the first color
    private static boolean changeBlue = false; //when true, blue is the first color

    private static boolean changeToRed = true; //when true, red is the 2nd color
    private static boolean changeToGreen = false; //when true, green is the 2nd color
    private static boolean changeToBlue = false; //when true, blue is the 2nd color

    private static boolean keepRed = true; //when true, red is the color that is kept
    private static boolean keepGreen = false; //when true, green is the color that is kept
    private static boolean keepBlue = false; //when true, blue is the color that is kept

    private static boolean adding = true; //when true, clicking on the color buttons will add, when false it will subtract
    /**
     * Example colour altering method by Mr. Cohen. This method will
     * increase the blue value while reducing the red and green values.
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void blueify (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (blue <= 255)
                    blue += 2;
                if (red <= 255)
                    red += 2;
                if (green <= 255)
                    green += 2;

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }

    /**
     * Takes the image and turns it into black and white.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void greyscale (BufferedImage bi){
        //get the size of the image to use it in the nested loops below
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        //will only loop for as many pixels that there is in each direction i is the "y axis", j is the "x axis"
        for (int i = 0; i < ySize; i++){
            for (int j = 0; j < xSize; j++){
                //gets an rgba value depicted as a single integer
                int rgb = bi.getRGB(j, i);

                //takes the rgba value and breaks it up into the separate parts
                int[] rgbValues = unpackPixel(rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                //average colors to greyscale
                int average = (red+green+blue)/3;

                red = average;
                green = average;
                blue = average;
                //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                int newColor = packagePixel(red, green, blue, alpha);
                bi.setRGB(j, i, newColor);
            }
        }
    }

    /**
     * Takes the image and inverts the colors.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void negative (BufferedImage bi){
        //get the size of the image to use it in the nested loops below
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        //will only loop for as many pixels that there is in each direction i is the "y axis", j is the "x axis"
        for (int i = 0; i < ySize; i++){
            for (int j = 0; j < xSize; j++){
                //gets an rgba value depicted as a single integer
                int rgb = bi.getRGB(j, i);

                //takes the rgba value and breaks it up into the separate parts
                int[] rgbValues = unpackPixel(rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                //invert the colors
                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;

                //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                int newColor = packagePixel(red, green, blue, alpha);
                bi.setRGB(j, i, newColor);
            }
        }
    }

    /**
     * Takes the image and flips it horizontally.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void flipHorizontal (BufferedImage bi)
    {
        //get the size of the image to use it in the nested loops below
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        //takes the image and flips it horizontally
        for (int i = 0; i < ySize; i++){
            for (int j = 0; j < xSize; j++){
                //returns the rgba value of a pixel
                int rgb = bi.getRGB(j, i);
                newBi.setRGB(xSize - j - 1, i, rgb);
            }
        }

        //takes the pixels of the temp Image and sets them as the pixels of the real image
        for (int i = 0; i < ySize; i++){
            for (int j = 0; j < xSize; j++){
                int rgbNew = newBi.getRGB(j, i);
                bi.setRGB(j,i, rgbNew);
            }
        }

        /**
         * 
         * Note: Due to a limitation in Greenfoot, you can get the backing BufferedImage from
         *       a GreenfootImage, but you cannot set or create a GreenfootImage based on a 
         *       BufferedImage. So, you have to use a temporary BufferedImage (as declared for
         *       you, above) and then copy it, pixel by pixel, back to the original image.
         *       Changes to bi in this method will affect the GreenfootImage automatically.
         */ 
    }

    /**
     * Takes the image and flips it vertically.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void flipVertical (BufferedImage bi)
    {
        //get the size of the image to use it in the nested loops below
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        //takes the pixels and flips them vertically
        for (int i = 0; i < ySize; i++){
            for (int j = 0; j < xSize; j++){
                //returns the rgba value of a pixel
                int rgb = bi.getRGB(j, i);
                newBi.setRGB(j, ySize - i - 1, rgb);
            }
        }
        //takes the pixels of the temp Image and sets them as the pixels of the real image
        for (int i = 0; i < ySize; i++){
            for (int j = 0; j < xSize; j++){
                int rgbNew = newBi.getRGB(j, i);
                bi.setRGB(j, i, rgbNew);
            }
        }

        /**
         * 
         * Note: Due to a limitation in Greenfoot, you can get the backing BufferedImage from
         *       a GreenfootImage, but you cannot set or create a GreenfootImage based on a 
         *       BufferedImage. So, you have to use a temporary BufferedImage (as declared for
         *       you, above) and then copy it, pixel by pixel, back to the original image.
         *       Changes to bi in this method will affect the GreenfootImage automatically.
         */ 
    }

    /**
     * Takes the image and rotates it 90 degrees counter-clockwise.
     * 
     * @param bi The BufferedImage to change.
     * 
     * @return GreenfootImage The new image, rotated.
     */
    public static GreenfootImage rotate90 (BufferedImage bi){
        //get the size of the image to use it in the nested loops below
        int xSizeOriginal = bi.getWidth();
        int ySizeOriginal = bi.getHeight();

        //sets the max values of the x and y coordinates of the new picture to be the y and x coorindates of the old one
        int xSizeNew = ySizeOriginal;
        int ySizeNew = xSizeOriginal;

        //creates a new BufferedImage using the new dimensions
        BufferedImage newBi = new BufferedImage(xSizeNew, ySizeNew, 3);

        //takes the pixels and rotates them into their new spots
        for (int i = 0; i < xSizeNew; i++){
            for (int j = 0; j < ySizeNew; j++){
                int rgb = bi.getRGB(j, i);
                newBi.setRGB(xSizeNew - i - 1, j, rgb);
            }
        }
        //takes the new image and turns it into a GreenfootImage so it can be set as the image of ImageHolder
        GreenfootImage image = createGreenfootImageFromBI(newBi);
        return image;
    }

    /**
     * Changes which colors will be swapped upon clicking the Swap Colors button.
     * 
     * @param current The first color you want to swap.
     */
    public static void colorToChange(TextButton current){
        //adds one to increase the remainder of the division by 1 each time, creating a sort of 3-state boolean
        currentColor++;
        if (currentColor%3 == 0){
            changeRed = true;
            changeGreen = false;
            changeBlue = false;
            current.update(" [ Red ");
        } else if (currentColor%3 == 1){
            changeRed = false;
            changeGreen = true;
            changeBlue = false;
            current.update(" [ Green ");
        } else if (currentColor%3 == 2){
            changeRed = false;
            changeGreen = false;
            changeBlue = true;
            current.update(" [ Blue ");
        }
    }

    /**
     * Changes which colors will be swapped upon clicking the Swap Colors button.
     * 
     * @param changeInto The first color you want to swap.
     */
    public static void colorToTurnInto(TextButton changeInto){
        //adds one to increase the remainder of the division by 1 each time, creating a sort of 3-state boolean
        colorChangeTo++;
        if (colorChangeTo%3 == 0){
            changeToRed = true;
            changeToGreen = false;
            changeToBlue = false;
            changeInto.update("   Red ] ");
        } else if (colorChangeTo%3 == 1){
            changeToRed = false;
            changeToGreen = true;
            changeToBlue = false;
            changeInto.update(" Green ] ");
        } else if (colorChangeTo%3 == 2){
            changeToRed = false;
            changeToGreen = false;
            changeToBlue = true;
            changeInto.update("  Blue ] ");
        }
    }

    /**
     * Takes the image and swaps the two colors specified in the buttons next to it.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void change(BufferedImage bi){
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                //temp integer to store original value, since it will be changed before we can set it
                int temp = 0;
                if (changeRed == true && changeToGreen == true){ //swaps red and green
                    temp = red;
                    red = green;
                    green = temp;
                } else if (changeRed == true && changeToBlue == true){ //swaps red and blue
                    temp = red;
                    red = blue;
                    blue = temp;
                } else if (changeGreen == true && changeToRed == true){ //swaps green and red
                    temp = green;
                    green = red;
                    red = temp;
                } else if (changeGreen == true && changeToBlue == true){ //swaps green and blue
                    temp = green;
                    green = blue;
                    blue = temp;
                } else if (changeBlue == true && changeToRed == true){ //swaps blue and red
                    temp = blue;
                    blue = red;
                    red = temp;
                } else if (changeBlue == true && changeToGreen == true){ //swaps blue and green
                    temp = blue;
                    blue = green;
                    green = temp;
                }
                //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }

    /**
     * Takes the image and swaps every rgb value with another one.
     * red --> green
     * green --> blue
     * blue --> red
     * 
     * @param bi The BufferedImage to change.
     */
    public static void colorShift (BufferedImage bi){
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                //shifts the color values around
                int newRed = blue;
                int newGreen = red;
                int newBlue = green;
                //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                int newColour = packagePixel (newRed, newGreen, newBlue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }

    /**
     * Changes whether to add or subtract the value when clicking on that color.
     * 
     * @param addOrSub The button that indicates adding or subtracting.
     */
    public static void addOrSubtract(TextButton addOrSub){
        if (adding == true){
            adding = false;
            addOrSub.update(" [ - ] ");
        } else if (adding == false){
            adding = true;
            addOrSub.update(" [ + ] ");
        }
    }

    /**
     * Takes the image and either increases or decreases the blue value depending on the add or subtract button.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void changeBlue (BufferedImage bi){
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        //increases blue value
        if (adding == true){
            // Using array size as limit
            for (int x = 0; x < xSize; x++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    // Calls method in BufferedImage that returns R G B and alpha values
                    // encoded together in an integer
                    int rgb = bi.getRGB(x, y);

                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    int[] rgbValues = unpackPixel (rgb);
                    int alpha = rgbValues[0];
                    int red = rgbValues[1];
                    int green = rgbValues[2];
                    int blue = rgbValues[3];

                    if (blue < 220){
                        blue +=10;
                    }
                    //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                    int newColour = packagePixel (red, green, blue, alpha);
                    bi.setRGB (x, y, newColour);
                }
            }
        }
        //decreases blue value
        else if (adding == false){
            // Using array size as limit
            for (int x = 0; x < xSize; x++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    // Calls method in BufferedImage that returns R G B and alpha values
                    // encoded together in an integer
                    int rgb = bi.getRGB(x, y);

                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    int[] rgbValues = unpackPixel (rgb);
                    int alpha = rgbValues[0];
                    int red = rgbValues[1];
                    int green = rgbValues[2];
                    int blue = rgbValues[3];

                    if (blue > 35){
                        blue -=10;
                    }
                    //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                    int newColour = packagePixel (red, green, blue, alpha);
                    bi.setRGB (x, y, newColour);
                }
            }
        }
    }

    /**
     * Takes the image and either increases or decreases the green value depending on the add or subtract button.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void changeGreen (BufferedImage bi){
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        //increases green value
        if (adding == true){
            // Using array size as limit
            for (int x = 0; x < xSize; x++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    // Calls method in BufferedImage that returns R G B and alpha values
                    // encoded together in an integer
                    int rgb = bi.getRGB(x, y);

                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    int[] rgbValues = unpackPixel (rgb);
                    int alpha = rgbValues[0];
                    int red = rgbValues[1];
                    int green = rgbValues[2];
                    int blue = rgbValues[3];

                    if (green < 220){
                        green +=5;
                    }
                    //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                    int newColour = packagePixel (red, green, blue, alpha);
                    bi.setRGB (x, y, newColour);
                }
            }
        }
        //decreases green value
        else if (adding == false){
            // Using array size as limit
            for (int x = 0; x < xSize; x++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    // Calls method in BufferedImage that returns R G B and alpha values
                    // encoded together in an integer
                    int rgb = bi.getRGB(x, y);

                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    int[] rgbValues = unpackPixel (rgb);
                    int alpha = rgbValues[0];
                    int red = rgbValues[1];
                    int green = rgbValues[2];
                    int blue = rgbValues[3];

                    if (green > 35){
                        green -=5;
                    }
                    //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                    int newColour = packagePixel (red, green, blue, alpha);
                    bi.setRGB (x, y, newColour);
                }
            }
        }
    }

    /**
     * Takes the image and either increases or decreases the red value depending on the add or subtract button.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void changeRed (BufferedImage bi){
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        //increases red value
        if (adding == true){
            // Using array size as limit
            for (int x = 0; x < xSize; x++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    // Calls method in BufferedImage that returns R G B and alpha values
                    // encoded together in an integer
                    int rgb = bi.getRGB(x, y);

                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    int[] rgbValues = unpackPixel (rgb);
                    int alpha = rgbValues[0];
                    int red = rgbValues[1];
                    int green = rgbValues[2];
                    int blue = rgbValues[3];

                    if (red < 220){
                        red +=5;
                    }
                    //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                    int newColour = packagePixel (red, green, blue, alpha);
                    bi.setRGB (x, y, newColour);
                }
            }
        }
        //decreases red value
        else if (adding == false){
            // Using array size as limit
            for (int x = 0; x < xSize; x++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    // Calls method in BufferedImage that returns R G B and alpha values
                    // encoded together in an integer
                    int rgb = bi.getRGB(x, y);

                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    int[] rgbValues = unpackPixel (rgb);
                    int alpha = rgbValues[0];
                    int red = rgbValues[1];
                    int green = rgbValues[2];
                    int blue = rgbValues[3];

                    if (red > 35){
                        red -=5;
                    }
                    //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                    int newColour = packagePixel (red, green, blue, alpha);
                    bi.setRGB (x, y, newColour);
                }
            }
        }
    }

    /**
     * Takes the image and either increases or decreases the alpha value depending on the add or subtract button.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void changeAlpha (BufferedImage bi){
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        //increases alpha value
        if (adding == true){
            // Using array size as limit
            for (int x = 0; x < xSize; x++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    // Calls method in BufferedImage that returns R G B and alpha values
                    // encoded together in an integer
                    int rgb = bi.getRGB(x, y);

                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    int[] rgbValues = unpackPixel (rgb);
                    int alpha = rgbValues[0];
                    int red = rgbValues[1];
                    int green = rgbValues[2];
                    int blue = rgbValues[3];

                    if (alpha < 255){
                        alpha +=5;
                    }
                    if (alpha > 255)
                        alpha = 255;
                    //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                    int newColour = packagePixel (red, green, blue, alpha);
                    bi.setRGB (x, y, newColour);
                }
            }
        }
        //decreases alpha value
        else if (adding == false){
            // Using array size as limit
            for (int x = 0; x < xSize; x++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    // Calls method in BufferedImage that returns R G B and alpha values
                    // encoded together in an integer
                    int rgb = bi.getRGB(x, y);

                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    int[] rgbValues = unpackPixel (rgb);
                    int alpha = rgbValues[0];
                    int red = rgbValues[1];
                    int green = rgbValues[2];
                    int blue = rgbValues[3];

                    if (alpha > 0){
                        alpha -=5;
                    }
                    if (alpha < 0)
                        alpha = 0;
                    //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                    int newColour = packagePixel (red, green, blue, alpha);
                    bi.setRGB (x, y, newColour);
                }
            }
        }
    }

    /**
     * Takes the image and turns two of three colors into black and white.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void highlight (BufferedImage bi){
        //get the size of the image to use it in the nested loops below
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        //will only loop for as many pixels that there is in each direction i is the "y axis", j is the "x axis"
        for (int i = 0; i < ySize; i++){
            for (int j = 0; j < xSize; j++){
                //gets an rgba value depicted as a single integer
                int rgb = bi.getRGB(j, i);

                //takes the rgba value and breaks it up into the separate parts
                int[] rgbValues = unpackPixel(rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                //average colors to greyscale
                int average = (red+green+blue)/3;

                if (keepRed == true){ //will only change the pixels where red is not the most dominant color
                    if (red < green || red < blue){
                        green = average;
                        blue = average;
                        red = average;
                    }
                }
                else if (keepGreen == true){ // will only change the pixels where green is not the most dominant color
                    if (green < red || green < blue){
                        green = average;
                        blue = average;
                        red = average;
                    }
                }
                else if (keepBlue == true){ // will only change the pixels where blue is not the most dominant color
                    if (blue < red || blue < green){
                        green = average;
                        blue = average;
                        red = average;
                    }
                }
                //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                int newColor = packagePixel(red, green, blue, alpha);
                bi.setRGB(j, i, newColor);
            }
        }
    }

    /**
     * Changes which color to keep when clicking the highlight button.
     * 
     * @param current The first color you want to swap.
     */
    public static void colorToKeep(TextButton keep){
        //adds one to increase the remainder of the division by 1 each time, creating a sort of 3-state boolean
        colorToKeep++;
        if (colorToKeep%3 == 0){
            keepRed = true;
            keepGreen = false;
            keepBlue = false;
            keep.update(" [ Red ] ");
        } else if (colorToKeep%3 == 1){
            keepRed = false;
            keepGreen = true;
            keepBlue = false;
            keep.update(" [ Green ] ");
        } else if (colorToKeep%3 == 2){
            keepRed = false;
            keepGreen = false;
            keepBlue = true;
            keep.update(" [ Blue ] ");
        }
    }

    /**
     * Takes the image and brightens it.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void brighten (BufferedImage bi){
        //get the size of the image to use it in the nested loops below
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                //increase green value if it's not too high already
                if (green < 220){
                    green +=5;
                }

                //decrease blue value if it's not too low already
                if (blue > 35){
                    blue -=5;
                }
                //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Takes the image and darkens it.
     * 
     * @param bi The BufferedImage to change.
     */
    public static void darken (BufferedImage bi){
        //get the size of the image to use it in the nested loops below
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];
                //decreases the green value if it's not already too low
                if (green > 35){
                    green -=5;
                }
                //increases the blue value if it's not already too high
                if (blue < 220){
                    blue +=5;
                }
                //makes a new color out of the newly modified rgba values and sets it into the BufferedImage
                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Takes in a BufferedImage and returns a GreenfootImage
     * 
     * @param newBi The BufferedImage to convert.
     * 
     * @return GreenfootImage A GreenfootImage built from the BufferedImage provided.
     */
    public static GreenfootImage createGreenfootImageFromBI(BufferedImage newBi){
        GreenfootImage returnImage = new GreenfootImage(newBi.getWidth(), newBi.getHeight());
        BufferedImage backingImage = returnImage.getAwtImage();
        Graphics2D backingGraphics = (Graphics2D) backingImage.getGraphics();
        backingGraphics.drawImage(newBi, null, 0, 0);

        return returnImage;
    }

    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel (int rgbaValue)
    {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >>  8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b, int a)
    {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }
}
