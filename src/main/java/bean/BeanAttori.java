package bean;

public class BeanAttori {
	
	private int actorId;
	private String firstName;
	private String lastName;
	
	public BeanAttori() {
		super();
	}

	/**
	 * @param firstName
	 * @param lastName
	 */
	public BeanAttori(int actorId, String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.actorId = actorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getActorId() {
		return actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

}
