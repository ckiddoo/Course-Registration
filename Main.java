import java.util.*;
import java.io.*;

public class Main {

	private static Scanner in;

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
		
		//To Do Add list of Courses
		//To Do Add Registered Courses 
		

	}

}
