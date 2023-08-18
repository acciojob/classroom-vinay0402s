package com.driver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class StudentRepository {

    //use hashmap to store student DB
    HashMap<String,Student> studentDB = new HashMap<>();
    HashMap<String, Teacher> teacherDB = new HashMap<String, Teacher>();
    HashMap<String, List<String>> teacherStudentDB = new HashMap<String, List<String>>();
//    teacher names (keys) and lists of student names (values).

    public void addStudent(Student student){
        studentDB.put(student.getName(),student);
    }

    public void addTeacher(Teacher teacher){
        teacherDB.put(teacher.getName(),teacher);
    }

    public void addStudentTeacherPair(String student, String teacher){
        if(studentDB.containsKey(student) && teacherDB.containsKey(teacher)){
            List<String> studentList = teacherStudentDB.getOrDefault(teacher,new ArrayList<>());
            studentList.add(student);
            teacherStudentDB.put(teacher,studentList);
        }
    }

    //get student by name
    public Student getStudentByName(String name){
           return studentDB.get(name);
    }

    public Teacher getTeacherByName(String name){
        return teacherDB.get(name);
    }
    public List<String> getStudentsByTeacherName(String teacher){
        return teacherStudentDB.getOrDefault(teacher,new ArrayList<>());
    }

    public List<String> getAllStudents(){
        List<String> studentList = new ArrayList<>();
        for(String s : studentDB.keySet()){
            studentList.add(s);
        }
        return studentList;
    }

    public void deleteTeacherByName(String teacher){
        // it will be present in teacher as well ass teacherStudentDB
        if(teacherDB.containsKey(teacher)){
            //teacher is present in teacher db
            if(teacherStudentDB.containsKey(teacher)){
                for(String student : teacherStudentDB.get(teacher)){
                    teacherStudentDB.remove(student);
                }
                teacherStudentDB.remove(teacher);
            }
            teacherDB.remove(teacher);
        }
    }

    public void deleteAllTeachers(){
        for(String teacher:teacherDB.keySet()){
            if(teacherStudentDB.containsKey(teacher)){
                for(String s:teacherStudentDB.get(teacher)){
                    studentDB.remove(s);
                }
                teacherStudentDB.remove(teacher);
            }
            teacherDB.remove(teacher);
        }
    }

}
