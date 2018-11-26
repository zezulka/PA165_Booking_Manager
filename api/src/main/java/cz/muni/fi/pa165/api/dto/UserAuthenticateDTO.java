package cz.muni.fi.pa165.api.dto;

/**
 *
 *@author Soňa Barteková
 * 
 */
public class UserAuthenticateDTO {
	
	private Long usedId;
	private String password;
	
	public Long getUsedId() {
		return usedId;
	}
	public void setUsedId(Long usedId) {
		this.usedId = usedId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
