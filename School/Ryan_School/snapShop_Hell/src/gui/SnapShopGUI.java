/*
 * Nwoke Fortune Chiemeziem.
 * Assignment 4 - winter 2015.
 */

package gui;

import filters.EdgeDetectFilter;
import filters.EdgeHighlightFilter;
import filters.Filter;
import filters.FlipHorizontalFilter;
import filters.FlipVerticalFilter;
import filters.GrayscaleFilter;
import filters.SharpenFilter;
import filters.SoftenFilter;
import image.PixelImage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This is a Snap Shop GUI application.
 * @author Nwoke Chiemeziem Fortune
 * @version winter 2015
 */
public class SnapShopGUI extends JFrame {
    // constants to capture screen dimensions
    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /** The width of the screen. */
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;

    /** The height of the screen. */
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;

    /** A Factor for scaling the size of the GUI relative to the current screen size. */
    private static final int SCALE = 3;
    
    /** A Factor for setting the dimension of the GUI. */
    private static final int DIMENSION = 4;

    /** The JPanel on the north containing the filter buttons. */
    private final JPanel myNorthPanel;
    
    /**This is my image panel that contains the displayed image. */
    private final JPanel myImagePanel;

    /**This is my image the will be edited. */
    private PixelImage myImage;

    /**This image label contains the image.*/
    private JLabel myImageLabel;
    
    /**The hash map, that maps the buttons to their corresponding filters.*/
    private final  Map<JButton, Filter> myMap;
    
    /**The south panel containing open, save and close buttons.*/
    private final JPanel mySouthPanel;
    
    /**The directory for the image.*/
    private File myDirectory;

    /** The names of all my filter buttons. */
    private final String[] myButtonNames = {"Edge Detect", "Edge Highlight",
                                            "Flip Horizontal", "Flip Vertical",
                                            "Gray Scale", "Sharpes", "Softes"};

    /** This is an array containing all the filters. */
    private final Filter[] myFilters = {new EdgeDetectFilter(), new EdgeHighlightFilter(),
                                        new FlipHorizontalFilter(), new FlipVerticalFilter(),
                                        new GrayscaleFilter(), new SharpenFilter(),
                                        new SoftenFilter()};


    /**
     * This constructor initializes my fields.
     */
    public SnapShopGUI() {
        super("Snap Shop");
        myNorthPanel = new JPanel();
        myImagePanel = new JPanel();
        
        myMap = new HashMap<>();
        mySouthPanel = new JPanel();
        myDirectory =  new File("./sample_images");
     
    }

    /**
     * This method starts the GUI and makes it visible.
     */
    public final void start() {

        setSize(SCREEN_WIDTH / SCALE, SCREEN_HEIGHT / SCALE);
        
        setLocation(SCREEN_WIDTH / DIMENSION - getWidth() / DIMENSION,
                    SCREEN_HEIGHT / DIMENSION - getHeight() / DIMENSION);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createNorthPanel(false);
        createSouthPanel(false);

        pack();
        setVisible(true);
    }

    /**
     * This method creates the north panel containing the filter buttons.
     * @param theButtonState signifies if the button is enabled or disabled.
     *  If true, its enabled and false otherwise.
     */
    private void createNorthPanel(final boolean theButtonState) {
        
        for (int i = 0; i < myButtonNames.length; i++) {
            final JButton button = new JButton(myButtonNames[i]);

            myMap.put(button, myFilters[i]);
            myNorthPanel.add(button);
            button.setEnabled(theButtonState);
           
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent theEvent) {

                    filterImage(myImage, button);
                }
            });
            add(myNorthPanel, BorderLayout.NORTH);
        }

    }

    /**
     * This method filters the image, and re-packs the GUI.
     * 
     * @param theImage This is the image to be filtered.
     * @param theButton This is the button that was clicked.
     */
    private void filterImage(final PixelImage theImage, final JButton theButton) {
    
        myMap.get(theButton).filter(theImage);
        myImageLabel.setIcon(new ImageIcon(theImage));

    }
    /**
     * This method creates the south panel containing the open, save and close image button.
     * @param theButtonState signifies if the button is enabled or disabled.
     *  If true, its enabled and false otherwise.
     */
    private void createSouthPanel(final boolean theButtonState) {

        final JButton openButton = new JButton("Open...");
        final JButton saveButton = new JButton("Save As...");
        final JButton closeImageButton = new JButton("Close Image");
      
        mySouthPanel.add(openButton);
        mySouthPanel.add(saveButton);
        mySouthPanel.add(closeImageButton);
       
        saveButton.setEnabled(theButtonState);
        closeImageButton.setEnabled(theButtonState);
       
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                try {
                    openFile();
                } catch (final IOException exception) {

                    exception.printStackTrace();
                }

            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {

                try {
                    openSaveFile();
                } catch (final IOException exception) {

                    exception.printStackTrace();
                }

            }
        });

        closeImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {

                myImagePanel.removeAll();
                myNorthPanel.removeAll();
                mySouthPanel.removeAll();

                createNorthPanel(false);
                createSouthPanel(false);
                pack();

            }
        });
        
        add(mySouthPanel, BorderLayout.SOUTH);

    }
   
    /**
     * This method opens the JFile chooser when the open button is clicked.
     * 
     * @throws IOException if image loading fails.
     */
    private void openSaveFile() throws IOException {
        final JFileChooser chooser = new JFileChooser(myDirectory);
        final FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "gif");
        chooser.showSaveDialog(this);
        
        chooser.setFileFilter(filter);
//        System.out.println(chooser.ge);
//        if (chooser.getDialogType() == JFileChooser.APPROVE_OPTION)
//            javax.imageio.ImageIO.write(myImage, "png", new File());
        
    }

    /**
     * This method opens the JFile chooser when the open button is clicked.
     * 
     * @throws IOException if image loading fails.
     */
    private void openFile() throws IOException {
        final JFileChooser chooser = new JFileChooser(myDirectory);
                                                       
        final int optionSelected = chooser.showOpenDialog(this);

        if (chooser.getSelectedFile() != null 
                                        && optionSelected != JFileChooser.CANCEL_OPTION) {
            if (isAnImageFile(chooser.getSelectedFile())) {

                myDirectory = chooser.getCurrentDirectory();
                
                myNorthPanel.removeAll();
                mySouthPanel.removeAll();
                createNorthPanel(true);
                createSouthPanel(true);
                createmyImagePanel(chooser.getSelectedFile());
            } else {
                JOptionPane.showMessageDialog(null,
                                              "The selected file did not contain an image.",
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This method creates the image and centers it.
     * 
     * @param theImageFile this is the name of the file image.
     * @throws IOException if image loading fails.
     */
    private void createmyImagePanel(final File theImageFile) throws IOException {

        myImage = PixelImage.load(new File(theImageFile.toString()));
        myImageLabel = new JLabel();
        myImageLabel.setIcon(new ImageIcon(myImage));

        myImagePanel.removeAll();

        myImagePanel.add(myImageLabel);

        add(myImagePanel, BorderLayout.CENTER);
        pack();

    }

    /**
     * This method checks if the file clicked is an image file.
     * 
     * @param theImageFile the image file to be checked.
     * @return the returns true if the file is an image else it pops up an error dialog.
     */
    private boolean isAnImageFile(final File theImageFile) {
        boolean isImageFile = false;
        final String[] allImageExtension = {".bmp", ".gif", ".jpg", ".png"};
        final String[] imageName = theImageFile.toString().split("\\\\");

        for (int i = 0; i < allImageExtension.length; i++) {
            if (imageName[imageName.length - 1].toUpperCase().
                                            contains(allImageExtension[i].toUpperCase())) {
                isImageFile = true;
            }
        }
        return isImageFile;
    }
}
