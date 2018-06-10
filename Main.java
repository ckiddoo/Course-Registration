import java.util.*;
import java.io.*;
import java.text.ParseException;

public class Main {

	private static Scanner in;
	private static CourseList currentCourseList;

	public static void main(String[] args) throws IOException {
		// Auto-generated method stub
		
		//Prompt to sign in or create new user
		System.out.print("Please enter your email");
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
				System.out.print("Please enter your password");
				while(!loginSuccessful){
					password = in.next();
					if(password.equals(passwordInFile)){
						System.out.print("You have successfully logged in");
						loginSuccessful = true;
				}
					else{
						System.out.print("You have entered an incorrect password"
								+ ", please try again");
					}
				}
				break;
			}
		}
		fileRead.close();
		//If it is a new email, add email to student list
		//To Do ask if email was entered correctly
		if(!studentFound){
			System.out.print("It seems that this is your first time logging in"
					+ ", welcome! \n Please enter your password");
			password = in.next();
			try {
				PrintWriter pw = new PrintWriter(studentList);
				pw.write(email+","+password+"\n");
				pw.close();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Add previously registered courses to student's registered courses
		File enrollmentData = new File("enrollment_data.txt");
		Scanner readEnrollment = new Scanner(enrollmentData);
		while(readEnrollment.hasNextLine()){
			String nextLine = readEnrollment.nextLine();
			String[] lineData = nextLine.split(",");
			String emailInFile = lineData[0];
			Map<Integer,Integer> coursesFromEnrollment = new HashMap<Integer,Integer>();
			if(emailInFile.equals(loggedInStudent.getEmail())){
				coursesFromEnrollment.put(Integer.parseInt(lineData[1]), Integer.parseInt(lineData[2]));
			}
			for(Map.Entry<Integer, Integer> entry : coursesFromEnrollment.entrySet()){
				int enrolledStatus = entry.getValue();
				if(enrolledStatus == 1){
					int courseID = entry.getKey();
					Course courseToAdd = currentCourseList.get(courseID);
					loggedInStudent.addCourseToEnrolled(courseToAdd);
				}
			}
		}
		readEnrollment.close();
		
		//Print Courses that student is currently enrolled in
		loggedInStudent.getEnrolledCourses();
		
		//To Do Add list of Courses
		ArrayList<Course> orderedCourses= currentCourseList.getCoursesInOrder();
		//Print Course Info
		for(Course course : orderedCourses){
			System.out.println(course.getCourseID()+"|"+course.getName()+"|"+course.getDescription()+"|"
					+course.getStartDate()+"|"+course.getEndDate()+"|"+course.getMaxEnrollment()+"|"+
					course.getCurrentEnrollment());
		}
		
		//Enroll In Course Based on CourseID
		System.out.print("Please enter the course ID of the course that you wish to register for.");
		int courseIDToRegister = in.nextInt();
		Course courseToRegister = currentCourseList.get(courseIDToRegister);
		loggedInStudent.enrollInCourse(courseToRegister);
		
		//Drop Course Based on CourseID
		System.out.print("Please enter the course ID of the course that you wish to drop.");
		int courseIDToDrop = in.nextInt();
		Course courseToDrop = currentCourseList.get(courseIDToDrop);
		loggedInStudent.dropCourse(courseToDrop);

	}

}
