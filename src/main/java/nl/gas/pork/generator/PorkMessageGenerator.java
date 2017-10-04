package nl.gas.pork.generator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import nl.gas.pork.model.PorkMessage;
import nl.gas.pork.model.SensorOutput;

public class PorkMessageGenerator {

	public static PorkMessage generatePorkMessage(int someUniqueNumber, Date date) {
		return new PorkMessage(dateToString(date),generateSensorOutput(someUniqueNumber), generateTips(someUniqueNumber));
	}
	
	private static List<SensorOutput> generateSensorOutput(int someUniqueNumber) {
		List<SensorOutput> out = new ArrayList<>();
		out.add(new SensorOutput("1", "Pigs head", generateRandomNum(90, 110)));
		out.add(new SensorOutput("2", "Pigs back", generateRandomNum(95, 115)));
		out.add(new SensorOutput("3", "Pigs ass", generateRandomNum(80, 100)));
		out.add(new SensorOutput("4", "BBQ", generateRandomNum(140, 200)));
		return out;
	}

	private static List<String> generateTips(int someUniqueNumber) {
		List<String> out = new ArrayList<>();
		if(generateRandomNum(1,10) > 7) out.add("Zou het niet doen");
		if(generateRandomNum(1,10) > 5) out.add("Rotatie snelheid moet omhoog"); else out.add("Rotatie snelheid moet omlaag");
		if(generateRandomNum(1,100) > 99) out.add("Varken is klaar");
		return out;
	}

	public static int generateRandomNum(int low, int high){
		Random r = new Random();
		return r.nextInt(high-low) + low;
	}
	
	private static String dateToString(Date date){
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
        TimeZone tz = TimeZone.getTimeZone( "UTC" );
        df.setTimeZone( tz );
        String output = df.format( date );
        int inset0 = 9;
        int inset1 = 6;
        String s0 = output.substring( 0, output.length() - inset0 );
        String s1 = output.substring( output.length() - inset1, output.length() );
        String result = s0 + s1;
        result = result.replaceAll( "UTC", "+00:00" );
        return result;
	}

}
