
public class Reservation 
{
	private String name;
	private String groupMember;
	private String seat;
	private String detail;
	private boolean group;

	/**
	 * make a individual reservation object
	 * @param name name of the passenger
	 * @param seat seat number and letter
	 */
	public Reservation(String name, String seat)
	{
		this.name = name;
		this.seat = seat;
		this.detail = seat + ", I, " + name + "\n";
		this.group = false;
	}

	/**
	 * make a new group reservation
	 * @param groupName name of the group
	 * @param name name of a group member
	 * @param seat a seat numbers and letter
	 */
	public Reservation(String groupName, String name, String seat)
	{
		this.name = groupName;
		this.groupMember = name;
		this.seat = seat;
		this.detail = seat + ", G, " + groupName + ", " + name + "\n";
		this.group = true;
	}

	/**
	 * get the name of a passenger or a group name
	 * @return name of the passenger or group
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * get name of the members in the group
	 * @return member names
	 */
	public String memberName()
	{
		return this.groupMember;
	}

	/**
	 * get the seat numbers
	 * @return the array of seat numbers
	 */
	public String seatNo()
	{
		return this.seat;
	}

	/**
	 * get the description of the reservation(seat number, type of reservation, name)
	 * @return the description
	 */
	public String getDetail()
	{
		return this.detail;
	}

	/**
	 * to check whether the reservation is a group reservation or not
	 * @return true if it is group reservation, or otherwise
	 */
	public boolean isGroup()
	{
		return group;
	}
}
