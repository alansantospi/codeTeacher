package utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.Classroom.Courses.CourseWork;
import com.google.api.services.classroom.Classroom.Courses.CourseWork.StudentSubmissions.Patch;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.Attachment;
import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.DriveFile;
import com.google.api.services.classroom.model.ListCourseWorkResponse;
import com.google.api.services.classroom.model.ListCoursesResponse;
import com.google.api.services.classroom.model.ListStudentSubmissionsResponse;
import com.google.api.services.classroom.model.Student;
import com.google.api.services.classroom.model.StudentSubmission;
import com.google.api.services.classroom.model.UserProfile;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.common.collect.ImmutableSet;

import codeteacher.VirtualClassLoader;
import codeteacher.analyzers.ClassAnalyzer;

public class ClassroomService {
	private static final String APPLICATION_NAME = "Google Classroom API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_CLASSROOM_PATH = "tokens_classroom";
	private static final String TOKENS_DRIVE_PATH = "tokens_drive";
	private static Credential credential;
	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
//    private static final List<String> SCOPES = Collections.singletonList(ClassroomScopes.CLASSROOM_COURSES_READONLY);
//    private static String x[] = {ClassroomScopes.CLASSROOM_COURSES_READONLY,
//    		ClassroomScopes.CLASSROOM_COURSEWORK_STUDENTS_READONLY};
//    private static final List<String> SCOPES = Arrays.asList(x); 

	private static final Set<String> SCOPES = new ImmutableSet.Builder<String>().addAll(ClassroomScopes.all())
			.addAll(DriveScopes.all()).build();

	private static final String CREDENTIALS_CLASSROOM_PATH = "/credentials.classroom.json";
	private static final String CREDENTIALS_DRIVE_PATH = "/credentials.drive.json";

	private static Classroom service;
	
	/** Global Drive API client. */
	private static Drive drive;
	
	/** Global instance of the HTTP transport. */
	private static NetHttpTransport httpTransport;
	
	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT        The network HTTP Transport.
	 * @param TOKENS_DIRECTORY_PATH
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final HttpTransport HTTP_TRANSPORT, final String CREDENTIALS_FILE_PATH,
			final String TOKENS_DIRECTORY_PATH) throws IOException {
//    	if (credential == null) {
		// Load client secrets.
		InputStream in = ClassroomService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

		credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//    	}
		return credential;
	}
	
	public static Drive drive() {
		if (drive == null) {
			try {
				httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				drive = getDriveService(httpTransport);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return drive;
	}
	
	private static Classroom classroom() {
		if (service == null) {
			try {
				httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				service = getClassroomService(httpTransport);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return service;
	}

	public static void main(String... args) throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Drive driveService = getDriveService(HTTP_TRANSPORT);
		Classroom service = classroom();

		List<Course> courses = new ArrayList<Course>();
		Course myCourse = getCourse("37481930934");
		courses.add(myCourse);

		if (courses == null || courses.size() == 0) {
			System.out.println("No courses found.");
		} else {
			System.out.println("Courses:");
			for (Course course : courses) {
				System.out.printf("%s\n", course.getName());
				String courseId = course.getId();
				List<com.google.api.services.classroom.model.CourseWork> courseWorkList = getCourseWorks(courseId);
				for (com.google.api.services.classroom.model.CourseWork cW : courseWorkList) {
					System.out.printf("%s\n", cW.getTitle());
					System.out.println(cW.getDescription());
					String courseWorkId = cW.getId();
					List<StudentSubmission> submissions = getStudentSubmissions(courseId, courseWorkId);
					for (StudentSubmission sub : submissions) {
						String userId = sub.getUserId();
						UserProfile userProfile = service.userProfiles().get(userId).execute();
						System.out.printf("%s - %s - %s\n", userId, userProfile.getName().getFullName(),
								sub.getAssignedGrade());

						List<Attachment> attachments = sub.getAssignmentSubmission().getAttachments();
						for (Attachment attach : attachments) {
							DriveFile driveFile = attach.getDriveFile();
							System.out.println(driveFile.getTitle());
							System.out.println(driveFile.getAlternateLink());

//							String fileId = "0BwwA4oUTeiV1UVNwOHItT0xfa2M";
							String fileId = driveFile.getId();
							System.out.println(fileId);

							ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
							driveService.files().get(fileId).executeMediaAndDownloadTo(byteArrayOutputStream);

							final FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
							File file = driveService.files().get(fileId).execute();
							java.io.File f = new java.io.File(file.getName());
							OutputStream outStream = new FileOutputStream(f);
							byteArrayOutputStream.writeTo(outStream);
							ZipFile zipFile = new ZipFile(f);
							Path path = UnzipUtils.extract(zipFile, fs);

//							ByteArrayInputStream inStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
//							ZipInputStream zip = new ZipInputStream(inStream);
//							ZipEntry entry = zip.getNextEntry();
//							System.out.println(entry.getName());
//							
//							UnzipUtils.extract(zip, fs);

							// Create a new class loader with the directory
							Path root = fs.getPath("src");
//							compile(root);
							VirtualClassLoader classLoader = new VirtualClassLoader(root);

							String klazzName = "MetodoGetClass";
							boolean recursive = true;
							boolean caseSensitive = false;
							boolean regex = true;
							int value = 1;
							
							ClassAnalyzer classAnalyzer = new ClassAnalyzer(classLoader, klazzName, recursive, caseSensitive, regex, value);
							boolean error = classAnalyzer.isError();
							System.out.println(error);
						}
					}
				}
			}
		}
	}

	/**
	 * Retrieve student submissions that belong to a courseWork
	 * @param courseId
	 * @param courseWorkId
	 * @return
	 * @throws IOException
	 */
	public static List<StudentSubmission> getStudentSubmissions(String courseId, String courseWorkId)
			throws IOException { 
		ListStudentSubmissionsResponse submissionResponse = classroom().courses().courseWork().studentSubmissions()
				.list(courseId, courseWorkId).execute();
		List<StudentSubmission> submissions = submissionResponse.getStudentSubmissions();
		return submissions;
	}
	
	public static StudentSubmission getStudentSubmission(String courseId, String courseWorkId, String id)
			throws IOException { 
		StudentSubmission submission = classroom().courses().courseWork().studentSubmissions()
				.get(courseId, courseWorkId, id).execute();
		return submission;
	}
	
	/**
	 * Grade student responses
	 * @param courseId
	 * @param courseWorkId
	 * @param studentId
	 * @param sub
	 * @return
	 * @throws IOException
	 */
	public static StudentSubmission gradeStudent(String courseId, String courseWorkId, String studentId, StudentSubmission sub) throws IOException {
		String id = sub.getId();
		String updateMask = StudentSubmissionUpdateMask.DRAFT_GRADE.toString();

		Patch patch = classroom().courses().courseWork().studentSubmissions()
				.patch(courseId, courseWorkId, id, sub).setUpdateMask(updateMask);

		return patch.execute();
	}

	private static Course getCourse(String courseId) throws IOException {
		return classroom().courses().get(courseId).execute();
	}
	
	/**
	 * Retrieve courses
	 * @param pageSize
	 * @return
	 * @throws IOException
	 */
	public static List<Course> getCourses(Integer pageSize) throws IOException {
		// List the first 10 courses that the user has access to.
      ListCoursesResponse response = classroom().courses().list()
              .setPageSize(pageSize)                                              
              .execute();
      List<Course> courses = response.getCourses();
		
		return courses;
	}

	public static com.google.api.services.classroom.model.CourseWork getCourseWork(String courseId, String courseWorkId) throws IOException {
		return classroom().courses().courseWork().get(courseId, courseWorkId).execute();
	}
	
	private static Classroom getClassroomService(final HttpTransport HTTP_TRANSPORT) throws IOException {
		return new Classroom.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				getCredentials(HTTP_TRANSPORT, CREDENTIALS_CLASSROOM_PATH, TOKENS_CLASSROOM_PATH))
						.setApplicationName(APPLICATION_NAME).build();
	}

	private static Drive getDriveService(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				getCredentials(HTTP_TRANSPORT, CREDENTIALS_DRIVE_PATH, TOKENS_DRIVE_PATH))
						.setApplicationName(APPLICATION_NAME).build();
	}

	public static void download(String fileId, OutputStream byteArrayOutputStream) throws IOException {
		drive().files().get(fileId).executeMediaAndDownloadTo(byteArrayOutputStream);
	}

	public static UserProfile getUserProfile(String userId) throws IOException {
		return classroom().userProfiles().get(userId).execute();
	}

	public static com.google.api.services.classroom.model.CourseWork createCourseWork(String courseId, com.google.api.services.classroom.model.CourseWork courseWork) throws IOException {
		return classroom().courses().courseWork().create(courseId, courseWork).execute();
	}
	
	public static void deleteCourseWork(String courseId, String courseWorkId) throws IOException {
		classroom().courses().courseWork().delete(courseId, courseWorkId).execute();
	}
	
	public static List<com.google.api.services.classroom.model.CourseWork> getCourseWorks(String courseId) throws IOException {
		CourseWork courseWork = classroom().courses().courseWork();
		ListCourseWorkResponse courseWorkResponse = courseWork.list(courseId).execute();
		List<com.google.api.services.classroom.model.CourseWork> courseWorkList = courseWorkResponse
				.getCourseWork();
		
		return courseWorkList;
	}
	
	public static void updateCourseWork(String courseId, String courseWorkId, com.google.api.services.classroom.model.CourseWork courseWork ,CourseWorkUpdateMask updateMask) throws IOException {
		classroom().courses().courseWork().patch(courseId, courseWorkId, courseWork).setUpdateMask(updateMask.toString()).execute();
	}
	
	/**
	 * Possible types of work
	 * @author Alan Santos
	 */
	public enum CourseWorkType { 
		/** No work type specified. This is never returned.*/
		COURSE_WORK_TYPE_UNSPECIFIED,	
		/** An assignment. */
		ASSIGNMENT,
		/** A short answer question.*/
		SHORT_ANSWER_QUESTION,	
		/** A multiple-choice question. */
		MULTIPLE_CHOICE_QUESTION;	
	}
	
	public enum StudentSubmissionUpdateMask { 
		ASSIGNED_GRADE("assignedGrade"),	
		DRAFT_GRADE("draftGrade");
		
		private String description;
		
		private StudentSubmissionUpdateMask(String description) {
			this.description = description;
		}
		@Override
		public String toString() {
			return description;
		}
	}
	
	public enum CourseWorkUpdateMask {
		/** The following fields may be specified by teachers:*/
		TITLE("title"),
		DESCRIPTION("description"),
		STATE("state"),
		DUEDATE("dueDate"),
		DUETIME("dueTime"),
		MAX_POINTS("maxPoints"),
		SCHEDULED_TIME("scheduledTime"),
		SUBMISSION_MODIFICATION_MODE("submissionModificationMode");
		
		private String description;
		
		private CourseWorkUpdateMask(String description) {
			this.description = description;
		}
		@Override
		public String toString() {
			return description;
		}
	}
	
	public enum CourseWorkState {
		/** No state specified. This is never returned.*/
		COURSE_WORK_STATE_UNSPECIFIED,
		
		/** Status for work that has been published. This is the default state.*/
		PUBLISHED,
		
		/** Status for work that is not yet published. Work in this state is visible only to course teachers and domain administrators.*/
		DRAFT,
		
		/** Status for work that was published but is now deleted. Work in this state is visible only to course teachers and domain administrators. Work in this state is deleted after some time.*/
		DELETED;
	}

	/**
	 * Retrieve a list of students that belong to a course
	 * @param courseId
	 * @return
	 * @throws IOException
	 */
	public static List<Student> getStudents(String courseId) throws IOException {
		return classroom().courses().students().list(courseId).execute().getStudents();
	}
}