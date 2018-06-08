import java.util.*;

public class Course {
	private int courseID;
	private Date startDate;
	private Date endDate;
	private String name;
	private String description;
	private int maxEnrollment;
	private int currentEnrollment;
	
	public Course(int courseID, Date startDate, 
			Date endDate, String name, String description,
			int maxEnrollment, int currentEnrollment)
	{
		this.courseID = courseID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
		this.description = description;
		this.maxEnrollment = maxEnrollment;
		this.currentEnrollment = currentEnrollment;
	}
	
	public int getCourseID(){
		return this.courseID;
	}
	
	public void setCourseID(int newID){
		this.courseID = newID;
	}
	
	public Date getStartDate(){
		return this.startDate;
	}
	
	public void setStartDate(Date newStartDate){
		this.startDate = newStartDate;
	}
	
	public Date getEndDate(){
		return this.endDate;
	}
	
	public void setEndDate(Date newEndDate){
		this.endDate = newEndDate;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String newName){
		this.name = newName;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String newDescription){
		this.description = newDescription;
	}
	
	public int getMaxEnrollment(){
		return this.maxEnrollment;
	}
	
	public void setMaxEnrollment(int newMaxEnrollment){
		this.maxEnrollment= newMaxEnrollment;
	}
	
	public int getCurrentEnrollment(){
		return this.currentEnrollment;
	}
	
	public void incrementCurrentEnrollment(){
		this.currentEnrollment += 1;
	}
	
	public void decrementCurrentEnrollment(){
		this.currentEnrollment -= 1;
	}
}
