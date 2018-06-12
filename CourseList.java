import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CourseList extends HashMap<Integer, Course> {
		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public CourseList() throws ParseException, FileNotFoundException{
			File courseListFile = new File("course_list.txt");
			Scanner courseListScanner = new Scanner(courseListFile);
			while(courseListScanner.hasNextLine()){
				String nextLine = courseListScanner.nextLine();
				String[] courseElements = nextLine.split("\\|");
				int courseID = Integer.parseInt(courseElements[0].trim());
				Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(courseElements[1].trim());
				Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(courseElements[2].trim());
				String name = courseElements[3].trim();
				String description = courseElements[4].trim();
				int maxEnrollment = Integer.parseInt(courseElements[5].trim());
				int currentEnrollment = this.getEnrollmentFromFile(courseID);
				Course courseToAdd = new Course(courseID,startDate,endDate,name,description,maxEnrollment,currentEnrollment);
				this.put(courseID, courseToAdd);
			}
			courseListScanner.close();
			
		}
	
	public int getEnrollmentFromFile(int courseIDToSearch) throws FileNotFoundException{
		int sum = 0;
		File enrollmentData = new File("enrollment_data.txt");
		Scanner readEnrollment = new Scanner(enrollmentData);
		while(readEnrollment.hasNextLine()){
			String nextLine = readEnrollment.nextLine();
			String[] enrollmentDataFromFile = nextLine.split(",");
			int courseInFile = Integer.parseInt(enrollmentDataFromFile[1]);
			int status = Integer.parseInt(enrollmentDataFromFile[2]);
			if(courseIDToSearch == courseInFile){
				sum += status;
			}
			}
		readEnrollment.close();
		return sum;
	}
	
	
	public ArrayList<Course> getCoursesInOrder(){
		Course[] allCourses = this.values().toArray(new Course[this.values().size()]);
		ArrayList<Course> orderedCourses = new ArrayList<Course>();
		orderedCourses.add(allCourses[0]);
		for(Course course : allCourses){
			String nameOfInsert = course.getName();
			for(Course courseInOrder : orderedCourses){
				String nameToCompare = courseInOrder.getName();
				if(nameOfInsert.compareToIgnoreCase(nameToCompare)==0){
					break;
				}
				else if(nameOfInsert.compareToIgnoreCase(nameToCompare)<0){
					orderedCourses.add(orderedCourses.indexOf(courseInOrder), course);
					break;
				}
				else{
					orderedCourses.add(course);
					break;
				}
				
			}
		}
		return orderedCourses;
		
		
		
	}
}
