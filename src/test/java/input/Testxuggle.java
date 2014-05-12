/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.input;

import com.xuggle.mediatool.demos.DecodeAndCaptureFrames;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.video.AConverter;

/**
 *
 * @author ncarrara
 */
public class Testxuggle {
    
    private static final String inputFilename = "AFightD.mpg";
    
    public static void main(String[] args){
        
        
        /*
         *  IContainer container = IContainer.make();  
 if (container.open("myfile.flv", IContainer.Type.READ, null) <0) 
  
   throw new RuntimeException("failed to open");  
 int numStreams = container.getNumStreams();  
 for(i = 0; i < numStreams; i++) {  
   IStream stream = container.getStream(i);  
   ...query IStream for stream information...  
 }  
 IPacket packet = IPacket.make();  
 while(container.readNextPacket(packet) >= 0)  
 {  
   ... Do something with the packet...  
 }  
 container.close(); 
         */
        
       /* public class IContainer {
...
public int seekKeyFrame(int streamIndex, long minTimeStamp,
  long targetTimeStamp, long maxTimeStamp, int flags);
...
}*/
        
        /*IContainer container = IContainer.make();

        // Open up the container
        if (container.open(inputFilename, IContainer.Type.READ, null) < 0) {
                System.out.println("Could not open file: " + inputFilename);
        }
        
        container.seekKeyFrame(streamIndex, 0,1000, IContainer.SEEK_FLAG_FRAME);*/
        
        int msBetweenCapture = 1;
        
        DecodeAndCaptureFrames decodeAndCaptureFrames = new  DecodeAndCaptureFrames("tmp/AFightD.mpg");
        //decodeAndCaptureFrames.main();
    }
    
}
