package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Student;

public class StudentManager implements RecordActions {
    private Map<Integer, Student> studentMap = new LinkedHashMap<>();

    public StudentManager(List<Student> initial) {
        if (initial != null) {
            for (Student s : initial) {
                studentMap.put(Integer.valueOf(s.getRollNo()), s);
            }
        }
    }

    @Override
    public void addStudent(Student student) {
        Integer key = Integer.valueOf(student.getRollNo());
        if (studentMap.containsKey(key)) {
            System.out.println("Student with roll no " + key + " already exists.");
        } else {
            studentMap.put(key, student);
        }
    }

    @Override
    public void deleteStudent(int rollNo) throws StudentNotFoundException {
        Integer key = Integer.valueOf(rollNo);
        if (studentMap.remove(key) == null) {
            throw new StudentNotFoundException("Student with roll no " + rollNo + " not found.");
        } else {
            System.out.println("Student record deleted.");
        }
    }

    @Override
    public void updateStudent(Student student) throws StudentNotFoundException {
        Integer key = Integer.valueOf(student.getRollNo());
        if (!studentMap.containsKey(key)) {
            throw new StudentNotFoundException("Student with roll no " + key + " not found.");
        }
        studentMap.put(key, student);
        System.out.println("Student record updated.");
    }

    @Override
    public Student searchStudent(int rollNo) throws StudentNotFoundException {
        Integer key = Integer.valueOf(rollNo);
        Student s = studentMap.get(key);
        if (s == null) {
            throw new StudentNotFoundException("Student with roll no " + rollNo + " not found.");
        }
        return s;
    }

    @Override
    public void viewAllStudents() {
        if (studentMap.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }
        Iterator<Student> it = getAllStudents().iterator();
        while (it.hasNext()) {
            Student s = it.next();
            s.displayDetails();
            System.out.println();
        }
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentMap.values());
    }

    public void sortByMarksDesc() {
        List<Student> list = getAllStudents();
        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student a, Student b) {
                return Double.compare(b.getMarks(), a.getMarks());
            }
        });
        Iterator<Student> it = list.iterator();
        System.out.println("Sorted Student List by Marks:");
        while (it.hasNext()) {
            Student s = it.next();
            s.displayDetails();
            System.out.println();
        }
    }
}
