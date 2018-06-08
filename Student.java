import java.util.*;
import java.io.*;



public class Student {
	private String email;
	private String password;
	private ArrayList<Course> enrolledCourses;
	
	public Student(String email, String password){
		this.email = email;
		this.password = password;
		this.enrolledCourses = new ArrayList<Course>();
	}
	
	public void changePassword(String newPassword){
		password = newPassword;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void enrollInCourse(Course courseToEnroll){
		int numStudents = courseToEnroll.getCurrentEnrollment();
		int maxStudents = courseToEnroll.getMaxEnrollment();
		if(numStudents<maxStudents){
			courseToEnroll.incrementCurrentEnrollment();
			this.enrolledCourses.add(courseToEnroll);
			File enrollmentData = new File("enrollment_data.txt");
			try {
				enrollmentData.createNewFile();
				PrintWriter pw = new PrintWriter(enrollmentData);
				pw.write(this.getEmail()+","+courseToEnroll.getCourseID()+"\n");
				pw.close();
				System.out.print("You have successfully enrolled in " 
						+ courseToEnroll.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.print(courseToEnroll.getName() +
					"is currently full, please select a different course.");
		}
	}
	
	public void dropCourse(Course courseToDrop){
		int courseInList = enrolledCourses.indexOf(courseToDrop);
		if(courseInList >=0){
			courseToDrop.decrementCurrentEnrollment();
			this.enrolledCourses.remove(courseInList);
			System.out.print("You have successfully dropped "
					+ courseToDrop.getName());
			//To Do add functionality to remove enrollment from file
		}
		else{
			System.out.print("You are not currently enrolled in "
					+ courseToDrop + "therefore it cannot be dropped.");
		}
	}
	
}
