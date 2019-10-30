package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

import com.github.marschall.memoryfilesystem.MemoryFileSystemBuilder;
import com.google.api.client.json.GenericJson;
import com.google.api.services.classroom.model.Attachment;
import com.google.api.services.classroom.model.DriveFile;
import com.google.api.services.classroom.model.GradeHistory;
import com.google.api.services.classroom.model.StateHistory;
import com.google.api.services.classroom.model.StudentSubmission;
import com.google.api.services.classroom.model.SubmissionHistory;
import com.google.api.services.classroom.model.UserProfile;

import codeteacher.VirtualClassLoader;
import codeteacher.analyzers.AbstractAnalyzer;
import codeteacher.analyzers.Analyzer;
import codeteacher.analyzers.ClassAnalyzer;
import codeteacher.analyzers.CompositeAnalyzer;
import codeteacher.analyzers.MethodAnalyzer;
import codeteacher.analyzers.PrivateMethodAnalyzer;
import codeteacher.analyzers.PublicMethodAnalyzer;
import codeteacher.result.Performance;
import gui.TestRunner;
import utils.ClassroomService.CourseWorkState;
import utils.ClassroomService.CourseWorkType;
import utils.ClassroomService.CourseWorkUpdateMask;

public class ClassroomTest {

	public static void main(String[] args) throws IOException {
		
		
		String courseId1 = "37481930934";
		String courseId3 = "37632060809";	
		
		
		String courseWorkId1 = "37481930964";
		String courseWorkId2 = "20745430328";
		String courseWorkId3 = "37634489289";
		
		String studentId1 = "112042122216619267519";
		String studentId2 = "110008950465713080864";
//		getCourse(courseId, courseWorkId2);
//		testCreateCourseWork(courseId);
//		testGetCourseWorks(courseId);
		testGetStudentSubmissions(courseId3, courseWorkId3);
		
//		testUpdateCourseWork(courseId, courseWorkId2, CourseWorkUpdateMask.MAX_POINTS);
		
//		testGetStudents(courseId);
//		testGradeStudent(courseId, courseWorkId2, studentId2);
	}
	
	private static void testGetStudentSubmissions(String courseId, String courseWorkId) throws IOException {

		List<StudentSubmission> submissions = ClassroomService.getStudentSubmissions(courseId, courseWorkId);
		submissions.forEach(s -> {
//			s.geti
			List<SubmissionHistory> hist = s.getSubmissionHistory();
//			hist.forEach(h -> {
//				StateHistory stateHistory = h.getStateHistory();
//				if (stateHistory != null) System.out.println(stateHistory);
//				GradeHistory gradeHistory = h.getGradeHistory();
//				if (gradeHistory != null) System.out.println(gradeHistory);
//			});
			
			List<GenericJson> collect = 
			hist.stream().map(subH -> {
				GenericJson json = null;
				StateHistory stateHistory = subH.getStateHistory();
				GradeHistory gradeHistory = subH.getGradeHistory();
				if (stateHistory != null) 
					json = stateHistory;
				else if (gradeHistory != null) 
					json = gradeHistory;
				return json;
			})
					.collect(Collectors.toList());
			
			List<StateHistory> stateHist = hist.stream().map(SubmissionHistory::getStateHistory).filter(sh -> {
				return sh != null;
			}).collect(Collectors.toList());
			
			List<String> stateUsers = stateHist.stream().map(StateHistory::getActorUserId).collect(Collectors.toList());
			
			List<GradeHistory> gradeHist = hist.stream().map(SubmissionHistory::getGradeHistory).filter(gh -> {
				return gh != null;
			}).collect(Collectors.toList());
			
			List<String> gradeUsers = gradeHist.stream().map(GradeHistory::getActorUserId).collect(Collectors.toList());
			
			stateUsers.addAll(gradeUsers);
			
			GenericJson json = new GenericJson();
			List<String> userNames = new ArrayList<>();
			stateUsers.forEach(userId -> {
				UserProfile userProfile = null;
				try {
					userProfile = ClassroomService.getUserProfile(userId);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				userNames.add(userProfile.getName().getFullName());
			});
			
			System.out.println(collect);
		});
		
	}

	private static void testGetStudents(String courseId) throws IOException {
		ClassroomService.getStudents(courseId).forEach(System.out::println);
	}
	
	private static void testGetCourseWorks(String courseId) throws IOException {
		ClassroomService.getCourseWorks(courseId).forEach(cw -> System.out.println(cw.getId()));
	}
	
	private static void testCreateCourseWork(String courseId) throws IOException {
		
		com.google.api.services.classroom.model.CourseWork courseWork = new com.google.api.services.classroom.model.CourseWork();
		courseWork.setTitle("Ant colonies");
		courseWork.setDescription("Read the article about ant colonies and complete the quiz.");
		courseWork.setWorkType(CourseWorkType.ASSIGNMENT.toString());
		courseWork.setState(CourseWorkState.PUBLISHED.toString());
		courseWork.setMaxPoints(10d);
		
		ClassroomService.createCourseWork(courseId, courseWork);
	}
	
	private static void testUpdateCourseWork(String courseId, String courseWorkId, CourseWorkUpdateMask updateMask) throws IOException {
		com.google.api.services.classroom.model.CourseWork courseWork = ClassroomService.getCourseWork(courseId, courseWorkId);
		Double maxPoints = 10d;
		courseWork.setMaxPoints(maxPoints);
		ClassroomService.updateCourseWork(courseId, courseWorkId, courseWork, updateMask);
	}
	
	private static com.google.api.services.classroom.model.CourseWork testGetCourseWork(String courseId, String courseWorkId) throws IOException {
		return ClassroomService.getCourseWork(courseId, courseWorkId);
	}
	
	private static void testDeleteCourseWork(String courseId, String courseWorkId) throws IOException {
		ClassroomService.deleteCourseWork(courseId, courseWorkId);
	}
	
	private static void testGradeStudent(String courseId, String courseWorkId, String studentId) throws IOException {
		StudentSubmission sub = ClassroomService.getStudentSubmissions(courseId, courseWorkId).stream()
				.filter( s -> s.getUserId().equals(studentId))
				.collect(Collectors.toList()).get(0);
		sub.set("draftGrade", 7.8d);
		ClassroomService.gradeStudent(courseId, courseWorkId, studentId, sub);
	}
	
	private static void getCourse(String courseId, String courseWorkId) throws IOException {
		
		FileSystem fs = MemoryFileSystemBuilder.newEmpty().build();
		
		List<StudentSubmission> submissions = ClassroomService.getStudentSubmissions(courseId, courseWorkId);
		
		submissions.forEach(sub -> {
			List<Attachment> attachments = sub.getAssignmentSubmission().getAttachments();
			
			if (attachments == null || attachments.isEmpty()) {
				System.out.println("No attached files found!");
				return;
			}
			
			List<Attachment> zipAttachments = attachments.stream()
					.filter( a -> { 
						return a.getDriveFile().getTitle().endsWith(".Zip"); 
						})
					.collect(Collectors.toList());
			
			if (zipAttachments == null || zipAttachments.isEmpty()) {
				System.out.println("No zip files found!");
				return;
			} else {
				zipAttachments.forEach(attach -> {
					DriveFile driveFile = attach.getDriveFile();
					String fileId = driveFile.getId();

					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					try {
						ClassroomService.download(fileId, byteArrayOutputStream);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ByteArrayInputStream inStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
					ZipInputStream zip = new ZipInputStream(inStream);
					
					try {
					
						
						Path root = fs.getPath("EntendendoGetClass", "src");
						
						UnzipUtils.extract(zip, root);
						
						root.toFile();
						
						
						
						// Create a new class loader with the directory
						VirtualClassLoader classLoader = new VirtualClassLoader(root);
						
						DynamicCompiler.compile(root, classLoader);
						
						String klazzName = "MetodoGetClass";
						boolean recursive = true;
						boolean caseSensitive = false;
						boolean regex = false;
						int value = 1;
						
						ClassAnalyzer classAnalyzer = new ClassAnalyzer(classLoader, klazzName, recursive, caseSensitive, regex, value);
						MethodAnalyzer methodAnalyzer = new MethodAnalyzer(classAnalyzer, true, "void", "main", true, false, 3, new String[] {String[].class.getName()});
						PublicMethodAnalyzer publicMethodAnalyzer = new PublicMethodAnalyzer(methodAnalyzer, 4);
						PrivateMethodAnalyzer privateMethodAnalyzer = new PrivateMethodAnalyzer(methodAnalyzer, 4);
						
//						methodAnalyzer.add(publicMethodAnalyzer);
						methodAnalyzer.add(privateMethodAnalyzer);
						
						classAnalyzer.add(methodAnalyzer);
						
						ClassAnalyzer classAnalyzer2 = new ClassAnalyzer(classLoader, "EntendendoGetClass", recursive, caseSensitive, regex, value);
						
						List<CompositeAnalyzer<AbstractAnalyzer>> analyzers = new ArrayList<>();
						analyzers.add(classAnalyzer);
						analyzers.add(classAnalyzer2);
						
						CompositeAnalyzer<AbstractAnalyzer> composite = new CompositeAnalyzer<>();
						TestRunner command = new TestRunner(composite);
						
						analyzers.forEach(a -> buildCommand(command, a));
						
						Performance perform = command.execute();
						System.out.println(perform);
						
						int totalErrors = perform.getTotalErrors();
						
						String userId = sub.getUserId();
//						UserProfile userProfile = ClassroomQuickstart.getUserProfile(userId);
						
						Double draftGrade = Double.valueOf(totalErrors);
						sub.setDraftGrade(draftGrade);
						
						try {
							ClassroomService.gradeStudent(courseId, courseWorkId, userId, sub);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
		});
	}
	
	private static TestRunner buildCommand(TestRunner command, AbstractAnalyzer analyzer) {
		TestRunner slave = new TestRunner(analyzer);
		command.add(slave);
		
		if (!(analyzer instanceof CompositeAnalyzer<?>)) {
			return command;
		}
		
		CompositeAnalyzer<AbstractAnalyzer> compositeAnalyzer = (CompositeAnalyzer<AbstractAnalyzer>) analyzer;
		List<AbstractAnalyzer> children = compositeAnalyzer.getChildren();
		int childCount = children.size();

		if (childCount == 0) {
			return command;
		}

		for (int index = 0; index < childCount; index++) {
			Analyzer child = compositeAnalyzer.getChild(index);
			buildCommand(slave, (AbstractAnalyzer) child);
		}
		return command;
	}
}
