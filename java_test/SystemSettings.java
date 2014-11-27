
public class SystemSettings {

	
	private static final String nativeOpenNiDirectory="C:\\Program Files\\OpenNI2\\Tools\\OpenNI2.dll";
	
	public static final void initLibOpenNi() throws Exception{
		try {
  	        System.load( nativeOpenNiDirectory);
  	    }
  	    catch (Exception e)
  	    {
  	    	throw new Exception("OpenNI2.dll problam");
  	    }
	}
}
