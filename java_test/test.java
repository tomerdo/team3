import org.openni.SensorType;
import org.openni.VideoMode;
import org.openni.VideoStream.CameraSettings;


public class test {

	public static void main(String[] args) {

		
		DepthCamera camera=null;
		try {
//			camera= new DepthCamera("./sample.noi" );
			camera= new DepthCamera();
		} catch (Exception e) { 
			 
			e.printStackTrace();
			System.exit(0);
		}
		
		
		 
		
		 final SimpleViewerApplication app = new SimpleViewerApplication(camera);
		 recordTimer(camera, "sample.noi", 1000);
	     app.run();
		 
		 
		  
	     camera.close();
	     
	} 

	
	public static void print(Object o){
		System.out.print(o);
	}
	public static void println(Object o){
		System.out.println(o);
	}
	
	public static void recordTimer(DepthCamera camera, String fileName ,int milisec){
		
		camera.startRecording(fileName);
		try {
			Thread.sleep(milisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{ 
			camera.stopRecording();
		}
		
		
	}
}
