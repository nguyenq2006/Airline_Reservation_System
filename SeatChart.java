
import java.io.IOException;
import java.util.*;
import java.io.PrintStream;

public class SeatChart 
{
	private Reservation[][] first;
	private Reservation[][] econ;
	private int Fstart;
	private int Fend;
	private int Estart;
	private int Eend;
	private String fileName;
	private String info;

	/**
	 * Construct a new seat chart
	 * @param rowsInFirst number of rows in first class
	 * @param seatPerRowF number of seats per row in first class
	 * @param rowsInEcon number of rows in economy class
	 * @param seatPerRowE number of seats per row in economy class
	 * @param fileName the name of the file to sane all the reservation
	 * @param info information about the airline
	 */
	public SeatChart(String rowsInFirst, int seatPerRowF, String rowsInEcon, int seatPerRowE, String fileName, String info)
	{
		int start = Integer.parseInt(rowsInFirst.substring(0, rowsInFirst.indexOf('-')));
		this.Fstart = start;
		int end = Integer.parseInt(rowsInFirst.substring(rowsInFirst.indexOf('-')+1));
		this.Fend = end;
		int rows = end -start + 1;
		this.first = new Reservation[rows][seatPerRowF];

		start = Integer.parseInt(rowsInEcon.substring(0,rowsInEcon.indexOf('-')));
		this.Estart = start;
		end = Integer.parseInt(rowsInEcon.substring(rowsInEcon.indexOf('-')+1));
		this.Eend= end;
		rows = end -start + 1;
		this.econ = new Reservation[rows][seatPerRowE];

		this.fileName = fileName;
		this.info = info;
	}

	/**
	 * reserve the seat for a costumer
	 * @param seatNo seat number
	 * @param reserved a reservation object
	 */
	public void reserve(String seatNo, Reservation reserved)
	{
		String temp = seatNo.substring(0, seatNo.length()-1);
		int seat = Integer.parseInt(temp);
		char col = seatNo.charAt(seatNo.length()-1);
		if(seat >= Fstart && seat <= Fend)
		{
			char a = 'A';
			int section = col - a;
			first[seat-this.Fstart][section] = reserved;
		}
		else if(seat >= Estart && seat <= Eend)
		{
			char a = 'A';
			int section = col - a;
			econ[seat-this.Estart][section] = reserved;
		}

	}

	/**
	 * request all manifest seats
	 * @param serviceClass type of service 
	 * @return list of manifest seats
	 */
	public String manifestSeats(String serviceClass)
	{
		String list = "";
		if(serviceClass.equals("First"))
		{
			for(int i = 0; i < first.length; i++)
			{
				for(int j = 0; j< first[i].length; j++)
				{
					Reservation reserved = first[i][j];
					if(reserved != null)
					{
						if(reserved.isGroup())
						{
							list += reserved.seatNo() + ": " + reserved.memberName() + "\n";
						}
						else
						{
							list += reserved.seatNo() + ": " + reserved.getName() + "\n";
						}
					}
				}
			}
			return list;
		}
		else if(serviceClass.equals("Economy"))
		{
			for(int i = 0; i < econ.length; i++)
			{
				for(int j = 0; j< econ[i].length; j++)
				{
					Reservation reserved = econ[i][j];
					if(reserved != null)
					{
						if(reserved.isGroup())
						{
							list += reserved.seatNo() + ": " + reserved.memberName() + "\n";
						}
						else
						{
							list += reserved.seatNo() + ": " + reserved.getName() + "\n";
						}
					}
				}
			}
			return list;
		}
		return "Request failed";
	}

	/**
	 * request list of available seats
	 * @param serviceClass type of service 
	 * @return list of availability
	 */
	public String availability(String serviceClass)
	{
		String list = "";
		if(serviceClass.equals("First"))
		{
			for(int i = 0; i < first.length; i++)
			{
				char a = 'A';
				String temp = "";
				for(int j = 0; j < first[i].length; j++)
				{
					Reservation reserved = first[i][j];
					if(reserved == null)
					{
						temp += a + ", ";
						a++;
					}
					else a++;
				}
				if(temp.contains(","))
				{
					temp = (i+Fstart) + ": " + temp;
					list += temp.substring(0, temp.length()-2) + "\n";
				}
			}
			return list;
		}
		else if(serviceClass.equals("Economy"))
		{
			for(int i = 0; i < econ.length; i++)
			{
				char a = 'A';
				String temp = "";
				for(int j = 0; j < econ[i].length; j++)
				{
					Reservation reserved = econ[i][j];
					if(reserved == null)
					{
						temp += a + ", ";
						a++;
					}
					else a++;
				}
				if(temp.contains(","))
				{
					temp = (i+Estart) + ": " + temp;
					list += temp.substring(0, temp.length()-2) + "\n";
				}
			}
			return list;
		}
		return "Request failed";
	}

	/**
	 * get the list of reservation
	 * @return list of reservation
	 */
	public String reservationList()
	{
		String list = "";for(int i = 0; i < first.length; i++)
		{
			for(int j = 0; j< first[i].length; j++)
			{
				Reservation reserved = first[i][j];
				if(reserved != null)
				{
					if(reserved.isGroup())
					{
						list += reserved.seatNo() + ", G, " + reserved.getName() + ", "+ reserved.memberName() + "\n";
					}
					else
					{
						list += reserved.seatNo() + ", I, " + reserved.getName() + "\n";
					}
				}
			}
		}
		for(int i = 0; i < econ.length; i++)
		{
			for(int j = 0; j< econ[i].length; j++)
			{
				Reservation reserved = econ[i][j];
				if(reserved != null)
				{
					if(reserved.isGroup())
					{
						list += reserved.seatNo() + ", G, " + reserved.getName() + ", "+ reserved.memberName() + "\n";
					}
					else
					{
						list += reserved.seatNo() + ", I, " + reserved.getName() + "\n";
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * save all the reservation into a file
	 * @throws IOException if file not found
	 */
	public void save() throws IOException
	{
		PrintStream out = new PrintStream(fileName);
		
		out.print(info + "\n" + this.reservationList());
		out.close();
	}

	/**
	 * get the a window sear
	 * @param serviceClass type of service
	 * @return a seat number for window seat
	 */
	public String windowSeat(String serviceClass)
	{
		if(serviceClass.equals("First"))
		{
			for(int i = 0; i < first.length; i++)
			{
				Reservation reserved = first[i][0];
				if(reserved == null)
				{
					String seat = (i+1) + "A";
					return seat;
				}
				else
				{
					reserved = first[i][first[i].length-1];
					if(reserved == null)
					{
						String seat = (i+1) + "";
						char a = 'A';
						char letter = (char)(first[i].length-1 + a);
						seat += letter;
						return seat;
					}
				}
			}	
		}
		else if(serviceClass.equals("Economy"))
		{
			for(int i = 0; i < econ.length; i++)
			{
				Reservation reserved = econ[i][0];
				if(reserved == null)
				{
					String seat = (i+Estart) + "A";
					return seat;
				}
				else
				{
					reserved = econ[i][econ[i].length-1];
					if(reserved == null)
					{
						String seat = (i+Estart) + "";
						char a = 'A';
						char letter = (char)(econ[i].length-1+a);
						seat += letter;
						return seat;
					}
				}
			}
		}
		return null;
	}

	/**
	 * get an aisle seat
	 * @param serviceClass type of service
	 * @return seat number for aisle seat
	 */
	public String aisleSeat(String serviceClass)
	{
		String seat = null;
		if(serviceClass.equals("First"))
		{
			for(int i = 0; i < first.length; i++)
			{
				int aisle = first[i].length/2;
				if(first[i][aisle-1] == null)
				{
					char letter = (char)('A'+ (aisle-1));
					seat = "" + (i+1) + letter;
					return seat;
				}
				else if(first[i][aisle] == null)
				{
					char letter = (char)('A'+ aisle);
					seat = "" + (i+1) + letter;
					return seat;
				}
			}
		}
		else if(serviceClass.equals("Economy"))
		{
			for(int i = 0; i < econ.length; i++)
			{
				int aisle = econ[i].length/2;
				if(econ[i][aisle-1] == null)
				{
					char letter = (char)('A'+ (aisle-1));
					seat = "" + (i+this.Estart) + letter;
					return seat;
				}
				else if(econ[i][aisle] == null)
				{
					char letter = (char)('A'+ aisle);
					seat = "" + (i+this.Estart) + letter;
					return seat;
				}
			}
		}
		return seat;
	}

	/**
	 * get a center seat
	 * @param serviceClass type of service
	 * @return seat number for center seat
	 */
	public String centerSeat(String serviceClass)
	{
		String seat = null;
		if(serviceClass.equals("First"))
		{
			for(int i = 0; i < first.length; i++)
			{
				int aisle = first[i].length/2;
				if(aisle <= 2)
				{
					return null;
				}
				int j = 1;
				while(j < (first[i].length-1))
				{
					if(j == aisle || j == (aisle-1))
					{
						j++;
					}
					if(first[i][j] == null)
					{
						char letter = (char)('A'+ j);
						seat = "" + (i+1) + letter;
						return seat;
					}
					j++;
				}
			}
		}
		else if(serviceClass.equals("Economy"))
		{
			for(int i = 0; i < econ.length; i++)
			{
				int aisle = econ[i].length/2;
				if(aisle <= 2)
				{
					return null;
				}
				int j = 1;
				while(j < (econ[i].length-1))
				{
					if(j == (aisle-1))
					{
						j+=2;
					}
					if(econ[i][j] == null)
					{
						char letter = (char)('A'+ j);
						seat = "" + (i+this.Estart) + letter;
						return seat;
					}
					j++;
				}
			}
		}
		return seat;
	}

	/**
	 * gets seats for group
	 * @param numberOfSeats the number of seats
	 * @param serviceClass  type of service
	 * @return list of seat numbers
	 */
	public String[] groupSeat(int numberOfSeats, String serviceClass)
	{
		String[] seats = new String[numberOfSeats];
		String available = this.availability(serviceClass);
		if(!available.equals(""))
		{
			Scanner read = new Scanner(available);
			String[] largest = read.nextLine().split(" ");
			String[] temp = largest;
			while(read.hasNextLine())
			{
				String line = read.nextLine();
				temp = line.split(" ");
				if((largest.length-1) >= numberOfSeats)
				{
					String row = largest[0].substring(0, largest[0].indexOf(":"));
					for(int i = 1; i < (numberOfSeats + 1); i++)
					{
						seats[i-1] = row + largest[i].trim().charAt(0);
					}
					return seats;
				}
				if(temp.length > largest.length)
				{
					largest = temp;
				}

			}
			String row = largest[0].substring(0, largest[0].indexOf(":"));
			int i = 1;
			boolean done = false;
			while(!done)
			{
				if(i < (numberOfSeats + 1) && i < largest.length)
				{
					seats[i-1] = row + largest[i].trim().charAt(0);
				}
				else done = true;
				i++;
			}
		}
		return seats;
	}

	/**
	 * cancel a reservation
	 * @param type type of reservation [G]roup or [I]ndividual
	 * @param name name of the reservation
	 * @return return true if the reservation was removed, or otherwise
	 */
	public boolean cancelReservation(String type, String name)
	{
		boolean modified = false;
		for(int i = 0; i < first.length; i++)
		{
			for(int j = 0; j< first[i].length; j++)
			{
				Reservation reser = first[i][j];
				if(reser != null)
				{
					if(reser.getName().equals(name))
					{
						first[i][j] = null;
						if(type.equals("I"))
						{
							return true;
						}
						modified = true;
					}
				}
			}
		}
		for(int i = 0; i < econ.length; i++)
		{
			for(int j = 0; j < econ[i].length; j++)
			{
				Reservation reser = econ[i][j];
				if(reser != null)
				{
					if(reser.getName().equals(name))
					{
						econ[i][j] = null;
						if(type.equals("I"))
						{
							return true;
						}
						modified = true;
					}
				}
			}
		}
		return modified;
	}
}
