package at.allianz.timetracker.dtos;

/**
 * REST DTO TimeRecord Object
 */
public class TimeRecord {
	private String email;
    private String start;
    private String end;
    
    
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}

    
}
