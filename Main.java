import java.util.*;
import java.io.*;
import java.text.ParseException;

public class Main {

	private static Scanner in;
	private static CourseList currentCourseList;

	public static void main(String[] args) throws IOException {
		// Create necessary files if do not exist
		File enrollmentData = new File("enrollment_data.txt");
		enrollmentData.createNewFile();
		File courseListFile = new File("course_list.txt");
		courseListFile.createNewFile();
		//Prompt to sign in or create new user
		System.out.println("Please enter your email");
		in = new Scanner(System.in);
		String email = in.next();
		String password = "";
		//txt file to store list of Student Emails and Passwords
		File studentList = new File("student_list.txt");
		//Create file if one doesn't exist
		studentList.createNewFile();
		Scanner fileRead = new Scanner(studentList);
		boolean studentFound = false;
		boolean loginSuccessful = false;
		//Find student's email in list of students
		while(fileRead.hasNextLine()){
			String nextLine = fileRead.nextLine();
			String emailInFile = nextLine.substring(0, nextLine.indexOf(","));
			String passwordInFile = nextLine.substring(nextLine.indexOf(",")+1);
			//If student email if found, ask for password (continue asking until right)
			if(email.equals(emailInFile)){
				studentFound = true;
				System.out.println("Please enter your password");
				while(!loginSuccessful){
					password = in.next();
					if(password.equals(passwordInFile)){
						System.out.println("You have successfully logged in.");
						loginSuccessful = true;
				}
					else{
						System.out.println("You have entered an incorrect password"
								+ ", please try again");
					}
				}
				break;
			}
		}
		fileRead.close();
		//If it is a new email, add email to student list
		if(!studentFound){
			System.out.println("It seems that this is your first time logging in"
					+ ", welcome! \n Please enter your password");
			password = in.next();
			try {
				FileWriter fw = new FileWriter(studentList,true);
				Scanner sc = new Scanner(studentList);
				if(!sc.hasNextLine()){
					fw.write(email+","+password);
					fw.close();
				}
				else{
					fw.write(System.getProperty("line.separator")+email+","+password);
					fw.close();
				}
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Use email to create new student and use this as student for session
		Student loggedInStudent = new Student(email,password);
		
		//Initialize current course list based on available data in txt file
		try {
			currentCourseList = new CourseList();
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		//Add previously registered courses to student's registered courses
		Map<Integer,Integer> coursesFromEnrollment = new HashMap<Integer,Integer>();
		Scanner readEnrollment = new Scanner(enrollmentData);
		while(readEnrollment.hasNextLine()){
			String nextLine = readEnrollment.nextLine();
			String[] lineData = nextLine.split(",");
			String emailInFile = lineData[0];

			if(emailInFile.equals(loggedInStudent.getEmail())){
				if(coursesFromEnrollment.get(Integer.parseInt(lineData[1])) == null){
					coursesFromEnrollment.put(Integer.parseInt(lineData[1]), Integer.parseInt(lineData[2]));
				}
				else{
					int status = coursesFromEnrollment.get(Integer.parseInt(lineData[1]));
					status += Integer.parseInt(lineData[2]);
					coursesFromEnrollment.put(Integer.parseInt(lineData[1]), status);
				}
			}
		}
			for(Map.Entry<Integer, Integer> entry : coursesFromEnrollment.entrySet()){
				int enrolledStatus = entry.getValue();
				if(enrolledStatus == 1){
					int courseID = entry.getKey();
					Course courseToAdd = currentCourseList.get(courseID);
					loggedInStudent.addCourseToEnrolled(courseToAdd);
				}
			}
		
		readEnrollment.close();
		
		boolean complete = false;
		while(!complete){
			System.out.print("What would you like to see? (Please enter the number corresponding to your choice)\n"
					+ "1. All courses \n"
					+ "2. Courses I have already registered for \n"
					+ "3. Register for a course \n"
					+ "4. Drop a course \n"
					+ "5. Exit \n");
			int optionSelected = in.nextInt();
			
			switch(optionSelected){
			//Print all available courses
			//TODO Adjust Print to Print Format
			case 1: 	System.out.println("Course ID | Course Name | Course Description | Start Date| End Date | Max Enrollment | Current Enrollment");
						ArrayList<Course> orderedCourses= currentCourseList.getCoursesInOrder();
						for(Course course : orderedCourses){
							System.out.println(course.getCourseID()+"|"+course.getName()+"|"+course.getDescription()+"|"
							+course.getStartDate()+"|"+course.getEndDate()+"|"+course.getMaxEnrollment()+"|"+
							course.getCurrentEnrollment());
			}break;
			//Print Courses that student is currently enrolled in
			case 2: 		loggedInStudent.getEnrolledCourses();
							break;
			
			//Enroll In Course Based on CourseID
			case 3:	System.out.println("Please enter the course ID of the course that you wish to register for.");
					int courseIDToRegister = in.nextInt();
					if(currentCourseList.get(courseIDToRegister)==null){
						System.out.println("That is not a valid course ID, please try again.");
						break;
					}
					else {
						Course courseToRegister = currentCourseList.get(courseIDToRegister);
						loggedInStudent.enrollInCourse(courseToRegister);
						break;						
					}
					//Drop Course Based on CourseID
			case 4: 			
					System.out.println("Please enter the course ID of the course that you wish to drop.");
					int courseIDToDrop = in.nextInt();
					if(currentCourseList.get(courseIDToDrop)==null){
						System.out.println("That is not a valid course ID, please try again.");
						break;
					}
					else{
						Course courseToDrop = currentCourseList.get(courseIDToDrop);
						loggedInStudent.dropCourse(courseToDrop);
						break;
					}
					
			case 5: System.out.println("Session has ended, please visit again soon.");
					complete = true;
			
			}
		}
				
		

	}

}
