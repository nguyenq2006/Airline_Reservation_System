import java.io.IOException;
import java.util.*;

public class Network 
{
	private SeatChart chart;
	private Computer comp;
	
	/**
	 * Construct a new airline network
	 * @param chart the airline seating chart
	 */
	public Network(SeatChart chart)
	{
		this.chart = chart;
	}
	
	/**
	 * connect the computer with the network
	 * @param comp the computer to connect to
	 */
	public void connect(Computer comp)
	{
		this.comp = comp;
	}
	
	/**
	 * make a reservation for an individual
	 */
	public void reservationI()
	{
		String name = comp.prompt("Name: ").trim();
		String serviceClass = comp.prompt("Service Class: ").trim();
		if(this.chart.manifestSeats(serviceClass).contains(name))
		{
			comp.print("Reservation existed");
			return;
		}
		String pref = comp.prompt("Seat Preference: ").trim();
		String seat = null;
		if(pref.equals("W"))
			seat = this.chart.windowSeat(serviceClass);
		else if(pref.equals("A"))
			seat = this.chart.aisleSeat(serviceClass);
		else if(pref.equals("C"))
			seat = this.chart.centerSeat(serviceClass);
		if(seat != null)
		{
			System.out.println("Seat Number:" + seat);
			this.chart.reserve(seat, new Reservation(name, seat)); 
		}
		else
		{
			comp.print("Request failed");
		}
//		this.analyze();
	}

	/**
	 * make a reservation for group
	 */
	public void reservationG()
	{
		String groupName = comp.prompt("Group Name: ").trim();
		String names = comp.prompt("Names: ").trim();
		String serviceClass = comp.prompt("Service Class: ").trim();
		String list = "";
		//		int counter = 0;
		String manifest = this.chart.reservationList();
		if(manifest.contains(groupName))
		{
			comp.print("Reseration existed");
			return;
		}
		
		String[] members = names.split(",");
		String[] seats = this.chart.groupSeat(members.length, serviceClass);
		if((seats != null) && (seats.length == members.length))
		{
			int i = 0; int j = 0;
			boolean done = false;
			while(!done)
			{
				if(seats[j] == null)
				{
					seats = this.chart.groupSeat(members.length - i, serviceClass);
					j = 0;
					if(seats[0] == null) 
					{
						this.chart.cancelReservation("G", groupName);
						done = true;
						comp.print("Request failed");
						return;
					}
				}
				else
				{
					this.chart.reserve(seats[j], new Reservation(groupName, members[i].trim(), seats[j]));
					list += seats[j] + ": " + members[i].trim() + "\n";
					i++; j++;
					if(i == members.length) done = true;
				}
			}
			System.out.print(list);
		}
		else
		{
			comp.print("Request failed");
		}

	}

	/**
	 * cancel reservation
	 */
	public void cancel()
	{
		String type = comp.prompt("Cancel [I]ndividual or [G]roup? ");
		String name = "";
		if(type.equals("I"))
		{
			name = comp.prompt("Name: ");
		}
		else
		{
			name = comp.prompt("Group Name: ");
		}
		boolean modified = this.chart.cancelReservation(type, name);
		if(modified)
		{
			comp.print("Reservation cancelled");
		}
		else
		{
			comp.print("Reservation does not exist");
		}
	}

	/**
	 * get a list of seats availability
	 */
	public void availability()
	{
		String input = comp.prompt("Service Class: ");
		System.out.println(this.chart.availability(input));
	}

	/**
	 * get a list of manifest seat
	 */
	public void manifest()
	{
		String input = comp.prompt("Service Class: ");
		String out = this.chart.manifestSeats(input);
		System.out.println(out);
	}

	/**
	 * quit the program
	 * @throws IOException if the file does not exist
	 */
	public void quit() throws IOException
	{
		this.chart.save();
	}

}
