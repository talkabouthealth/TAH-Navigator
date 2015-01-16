package nav.dto;

public class CareMember {
	private Integer id;
	private String firstName;
	private String lastName;
	private String designation;
	private String phone;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj instanceof CareMember){
			CareMember cm =  (CareMember) obj;
			if(this.id.equals(cm.getId())) {
				returnValue = true;
			}
		}
		return returnValue;
	}
	
	@Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
