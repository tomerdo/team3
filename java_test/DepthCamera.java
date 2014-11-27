import java.awt.Frame;
import java.util.List;

import org.openni.Device;
import org.openni.DeviceInfo;
import org.openni.OpenNI;
import org.openni.PixelFormat;
import org.openni.Recorder;
import org.openni.SensorType;
import org.openni.VideoFrameRef;
import org.openni.VideoMode;
import org.openni.VideoStream;
import org.openni.VideoStream.CameraSettings;

/**
 * 
 * @author Yonatan Tzulang && Tomer Dobkin 
 * @version  0.1
 * @since 2014-11-24
 */


public class DepthCamera {
	
//	private static char MAX_RES=1;
//	private static char MAX_FPS=2;
//	private static char MAX_PIX=4;
	
	public Device device;
	public VideoMode vMode;
	public VideoStream stream;
	public Recorder recorder;
	
	private boolean isRecording;
	/**
	 * Camera Constructor for physical camera
	 * 
	 * @return Camera object
	 * @throws Exception 		if no camera is connected 
	 */
	DepthCamera() throws Exception{
		
		SystemSettings.initLibOpenNi();
		
		OpenNI.initialize();
		
        List<DeviceInfo> devicesInfo = OpenNI.enumerateDevices();
        if (devicesInfo.isEmpty()) 
    	  	throw new Exception("No Device Connected");
      
        this.device= Device.open(devicesInfo.get(0).getUri());
         
        
        if (!device.hasSensor(SensorType.DEPTH)) 
                    		throw new Exception("Device does not seport depth sensonr");
        init();     
	}
	
	
	/**
	 * Camera Constructor for noi file 
	 * 
	 * @param	fileName	a string for the noi record file
	 * @return	Camera object
	 * @throws	Exception if a file error occur  
	 */
	DepthCamera(String fileName) throws Exception{
		
		SystemSettings.initLibOpenNi();
		
		OpenNI.initialize();
        this.device= Device.open(fileName);
        if (this.device==null)
        	throw new Exception("Eror opening "+ fileName + " file");
        if (!device.hasSensor(SensorType.DEPTH)) 
                    		throw new Exception("Device does not seport depth sensonr");
        init();
	}
	
	
	// initialising Camera variables
	private void init(){
		 
		 this.stream= VideoStream.create( this.device, SensorType.DEPTH);
		 this.vMode= getVideoMode();
		 if (!this.device.isFile()) 
			 
			 this.stream.setVideoMode(vMode);
		 this.isRecording=false;
	}
	
	
	/**
 	 * Release Camera resources 
 	 * 
 	 */
	public void close(){
		stream.stop();
		device.close();
    	OpenNI.shutdown(); 
    }
	
	/* 
	 * Heuristic for getting the best resolution and FPS 
	 *  for now its arbitrary
	 *  
	 */
	private VideoMode getVideoMode( ){
		 

		
		if (!this.device.isFile() )
//			return stream.getSensorInfo().getSupportedVideoModes().get(0);
//		else
			return stream.getSensorInfo().getSupportedVideoModes().get(5);
		return null;
	}
	
	
	
	public static String pixelFormatToName(PixelFormat format) {
        switch (format) {
            case DEPTH_1_MM:    return "1 mm";
            case DEPTH_100_UM:  return "100 um";
            case SHIFT_9_2:     return "9.2";
            case SHIFT_9_3:     return "9.3";
            case RGB888:        return "RGB";
            default:            return "UNKNOWN";
        }}
	
	/**
	 * Saves the camera output on a file
 
	 * 
	 * @args filename	file name to save the record
	 */
	synchronized public void  startRecording(String filename){
		if (isRecording)
			throw new RuntimeException("record fail- allready recording");
		
		if (!device.isFile()){
			isRecording=true;
			recorder=Recorder.create(filename);
			stream.start();
			recorder.addStream(this.stream, false);
			recorder.start();
		} else {
			//throw new RuntimeException("can't record from a file");
		}
	    
	    
	 	
	
	}
	synchronized public void stopRecording(){
		if (!isRecording)
			throw new RuntimeException("Stop recording fail- no recording is activated");
		
		isRecording=false;
		recorder.stop();
		stream.stop();
		recorder.destroy();
	}
	
	/**
	 * 
	 * @return a string Containing the video mode 
	 */
	public String getVideoModeInfo(){
		
            return (String.format("%d x %d @ %d FPS (%s)",
                vMode.getResolutionX(),
                vMode.getResolutionY(), 
                vMode.getFps(),
                pixelFormatToName(vMode.getPixelFormat())));
            
	}
	
	public VideoFrameRef getFrame(){
		
		VideoFrameRef frame=stream.readFrame();
		return frame;
		
		
	}
	
	 
	
	
}
	
	
	
