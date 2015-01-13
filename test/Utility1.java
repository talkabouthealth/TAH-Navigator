import java.util.Random;
public class Utility1 {
	

	//import org.apache.xpath.operations.String;

	public static java.lang.String getRandomNumber()
	   {
	    Random randomGenerator = new Random();
	    return ""+randomGenerator.nextInt(1000);
	   }
	}


