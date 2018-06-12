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
	
	public void addCourseToEnrolled(Course course){
		enrolledCourses.add(course);
	}
	
	public void enrollInCourse(Course courseToEnroll){
		int numStudents = courseToEnroll.getCurrentEnrollment();
		int maxStudents = courseToEnroll.getMaxEnrollment();
		int courseInList = enrolledCourses.indexOf(courseToEnroll);
		if(courseInList>=0){
			System.out.println("You are already enrolled in this course.");
		}
		else if(numStudents<maxStudents){
			courseToEnroll.incrementCurrentEnrollment();
			this.enrolledCourses.add(courseToEnroll);
			File enrollmentData = new File("enrollment_data.txt");
			try {
				enrollmentData.createNewFile();
				PrintWriter pw = new PrintWriter(enrollmentData);
				pw.write(this.getEmail()+","+courseToEnroll.getCourseID()+","+"1"+"\n");
				pw.close();
				System.out.println("You have successfully enrolled in " 
						+ courseToEnroll.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println(courseToEnroll.getName() +
					"is currently full, please select a different course.");
		}
	}
	
	public void dropCourse(Course courseToDrop){
		int courseInList = enrolledCourses.indexOf(courseToDrop);
		if(courseInList >=0){
			courseToDrop.decrementCurrentEnrollment();
			this.enrolledCourses.remove(courseInList);
			File enrollmentData = new File("enrollment_data.txt");
			try {
				enrollmentData.createNewFile();
				PrintWriter pw = new PrintWriter(enrollmentData);
				pw.write(this.getEmail()+","+courseToDrop.getCourseID()+","+"-1"+"\n");
				pw.close();
				System.out.println("You have successfully dropped " 
						+ courseToDrop.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
			//To Do add functionality to remove enrollment from file
		}
		else{
			System.out.println("You are not currently enrolled in "
					+ courseToDrop + "therefore it cannot be dropped.");
		}
	}
	
	public void getEnrolledCourses(){
		if(enrolledCourses.size()==0){
			System.out.println("You are not currently enrolled in any courses.");
		}
		else{
			for(Course course : this.enrolledCourses){
				System.out.println(course.getCourseID()+"|"+course.getName()+"|"+course.getDescription()+"|");
			}
		}
		
	}
	
}
