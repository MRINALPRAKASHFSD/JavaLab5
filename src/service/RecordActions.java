package service;

import model.Student;

public interface RecordActions {
    void addStudent(Student student);
    void deleteStudent(int rollNo) throws StudentNotFoundException;
    void updateStudent(Student student) throws StudentNotFoundException;
    Student searchStudent(int rollNo) throws StudentNotFoundException;
    void viewAllStudents();
}

