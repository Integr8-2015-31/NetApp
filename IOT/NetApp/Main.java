
import java.util.ArrayList;


public class Main {

	static int time = 0;
	private final static int sleepTime = 1000;
	
	public static void main(String[] args) {

		
		int x = 5;
		
		ArrayList<Event> events = new ArrayList<Event>();

		while(true) {

		double eventChance = Math.random();						//create event
		if(eventChance < .02d) {  
			events.add(new Event("LongTerm", (int) (Math.random() * 100) + 200));
			
		} else if(eventChance < .15d) {
			events.add(new Event("Critical", (int) (Math.random() * 20) + 10));
			
		} else if(eventChance < .35d) {
			events.add(new Event("Immediate", (int) (Math.random() * 7) + 3));
		}
		
		for(Event e:events) {					// update event times
			e.setTime(e.getTime() - 1);
		}
		
		for(int i=0; i<events.size();i++) {   // remove events that are over
			Event e = events.get(i);
			//System.out.println(e.type);
			if(e.getTime() < 1) {
				events.remove(i);
			}
		}

		
		for(Event e:events) {
			System.out.println("Event Type: " + e.getType() + ", Event Life: " + e.getTime());
		}

		System.out.println();
		time=time+1;
		
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
	}
}
