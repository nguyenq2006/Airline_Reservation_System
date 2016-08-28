import java.io.IOException;
import java.util.*;

public class Computer 
{
	private Scanner in;
	private static final String INITIAL_PROMPT = 
			"Enter P to add individual reservation\n"
					+ "Enter G to add group reservation\n"
					+ "Enter C to cancel reservation\n"
					+ "Enter A to see seats availabilty chart\n"
					+ "Enter M to see the manifest list\n"
					+ "Enter Q to quit and save reservation";
	private Network network;
	private boolean done;
	
	/**
	 * construct a computer to interact with users
	 * @param system the reservation system
	 * @param in the scanner
	 */
	public Computer(ReservationSystem system, Scanner in)
	{
		this.in = in;
		this.network = system.access();
		this.done = false;
	}

	/**
	 * start the reservation system
	 */
	public void start()
	{
		System.out.println(INITIAL_PROMPT);
		this.analyze();
	}

	/**
	 * analyze user input
	 */
	public void analyze()
	{
		while(!done)
		{
			String input = in.nextLine().trim();

			if(input.equals("P"))
				this.network.reservationI();
			if(input.equals("G")) 
				this.network.reservationG();
			if(input.equals("C"))
				this.network.cancel();
			if(input.equals("A"))
				this.network.availability();
			if(input.equals("M")) 
				this.network.manifest();
			if(input.equals("Q")) 
				try {
					this.network.quit();
					done = true;
					in.close();
				} catch (IOException e) {
					return;

				}
		}
		//		in.close();
	}

	/**
	 * asking for user information
	 * @param output prompt message
	 * @return the user input
	 */
	public String prompt(String output)
	{
		System.out.print(output);
		return in.nextLine();
	}
	
	/**
	 * print any message from the system
	 * @param message output message
	 */
	public void print(String message)
	{
		System.out.println(message);
	}
}
