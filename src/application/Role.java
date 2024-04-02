package application;

public class Role {
	private String roleType;
	
	//Constructor
	public Role(String role) {
		this.roleType = role;
	}
	
	//Get role type
	public String getRoleType() {
		return roleType;
	}
	
	//Set role type
	public void setRoleType(String role) {
		this.roleType = role;
	}
	
}
