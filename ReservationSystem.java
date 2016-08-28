import java.io.*;
import java.util.*;

public class ReservationSystem 
{
	private String fileName;
	private SeatChart chart;
	private String airlineInfo;
	private Network network;

	/**
	 * construct a new reservation system
	 * @param input name of the file
	 * @throws IOException if file not found
	 */
	public ReservationSystem(String input) throws IOException
	{
		this.fileName = input;
		this.read();
		this.network = new Network(chart);
	}

	/**
	 * read the input file
	 * @throws IOException
	 */
	private void read() throws IOException
	{
		Scanner in = new Scanner(new File(fileName));
		while(in.hasNextLine())
		{
			String line = in.nextLine();
			Scanner read = new Scanner(line);
			if(line.contains("First") && line.contains("Economy"))
			{
				this.airlineInfo = line;
				while(read.hasNext())
				{
					String next = read.next();
					if(next.equalsIgnoreCase("First"))
					{
						String rowsInFirst = ""; 
						int seatPerRowF = 0;
						String rowsInEcon = "";
						int seatPerRowE = 0;
						next = read.next();
						rowsInFirst = next.substring(0,next.length()-1);

						read.next();
						next = read.next();
						seatPerRowF= next.charAt(next.length()-2) - next.charAt(0) + 1;

						read.next();
						next = read.next();
						seatPerRowF += next.charAt(next.length()-2) - next.charAt(0) + 1;

						next = read.next();
						if(next.equalsIgnoreCase("Economy"))
						{
							next = read.next();
							rowsInEcon = next.substring(0,next.length()-1);

							read.next();
							next = read.next();
							seatPerRowE= next.charAt(next.length()-2) - next.charAt(0) + 1;

							read.next();
							next = read.next();
							seatPerRowE += next.charAt(next.length()-1) - next.charAt(0) + 1;
						}

						chart = new SeatChart(rowsInFirst, seatPerRowF, rowsInEcon, seatPerRowE, fileName, this.airlineInfo);

					}

				}
			}
			else if(!line.equals(""))
			{
				read = read.useDelimiter(",");
				String seatNo = read.next().trim();
				while(read.hasNext())
				{
					String next = read.next();
					if(next.contains("I"))
					{
						String name = read.next().trim();
						Reservation reserved = new Reservation(name, seatNo);
						this.chart.reserve(seatNo, reserved);
					}
					else if(next.contains("G"))
					{
						String groupName = read.next().trim();
						String name = read.next().trim();
						Reservation reserved = new Reservation(groupName, name, seatNo);
						this.chart.reserve(seatNo, reserved);
					}
				}
			}


			read.close();
		}
		in.close();
	}

	
	/**
	 * get the seating chart
	 * @return the seating chart
	 */
	public SeatChart getChart()
	{
		return this.chart;
	}
	
	/**
	 * to access the airline network
	 * @return network
	 */
	public Network access()
	{
		return this.network;
	}
	
	/**
	 * main class to run the program
	 * @param args File name
	 * @throws IOException if file not found
	 */
	public static void main(String[] args) throws IOException
	{
		String filename = args[0];
		ReservationSystem system = new ReservationSystem(filename);
		Scanner console = new Scanner(System.in);
		Computer comp = new Computer(system, console);
		Network net = system.access();
		net.connect(comp);
		comp.start();
	}
}
