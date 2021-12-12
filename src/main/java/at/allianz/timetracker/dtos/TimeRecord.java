package at.allianz.timetracker.dtos;

import java.util.Date;

/**
 * REST DTO TimeRecord Object
 */
public class TimeRecord {
	private String email;
    private Date start;
    private Date end;
    
    
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date timeFrom) {
		this.start = timeFrom;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}

    
}
