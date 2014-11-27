


import org.openni.*;
import org.openni.VideoStream.CameraSettings;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class SimpleViewerApplication  {

    private JFrame mFrame;
    private JPanel mPanel;
    private SimpleViewer mViewer;
    private boolean mShouldRun = true;
    private DepthCamera camera;
    private JButton mButton;
 
    public SimpleViewerApplication(DepthCamera camera) {
    	this.camera = camera;
        
        mFrame = new JFrame("OpenNI Simple Viewer");
        mPanel = new JPanel();
        mViewer = new SimpleViewer();
        
        mButton =new JButton("Take Frame");
        
        mButton.addActionListener(
        	new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
				}}
		     		
        );
        // register to key events
        mFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {}
            
            @Override
            public void keyReleased(KeyEvent arg0) {}
            
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    mShouldRun = false;
                }
            }
        });
        
        // register to closing event
        mFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mShouldRun = false;
            }
        });

        
        
        mViewer.setSize(800,600);
        mFrame.add("North", mPanel);
        mFrame.add("Center", mViewer);
        mFrame.add("South",mButton);
        mFrame.setSize(mViewer.getWidth() + 20, mViewer.getHeight() + 80);
        mFrame.setVisible(true);
        
        
        mViewer.setStream(camera.stream);
        mViewer.setSize(camera.stream.getVideoMode().getResolutionX(), camera.stream.getVideoMode().getResolutionX());
        mFrame.setSize(mViewer.getWidth() + 20, mViewer.getHeight() + 80);
        camera.stream.start();
    }
       

  
     
     
    void run() {
        while (mShouldRun) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mFrame.dispose();
    }
}
 