package com.example.emre.a14011009_firsthw_mpg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.LinkedList;

class Lesson{
    public String lessonName;
    public int studentCount;
    public float averageGrade;
}

public class ListLessonsActivity extends AppCompatActivity {

    ArrayList<Lesson> lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lessons);

        lessons = new ArrayList<>();
        SetLessons();
    }

    public void SetLessons(){
        Lesson lesson = new Lesson();
        lesson.lessonName = "MobilProg";
        lesson.studentCount = 75;
        lesson.averageGrade = 45.3f;
        lessons.add(lesson);
        lesson.lessonName = "YapayZeka";
        lesson.studentCount = 60;
        lesson.averageGrade = 55.6f;
        lessons.add(lesson);
        lesson.lessonName = "OyunGelistirme";
        lesson.studentCount = 50;
        lesson.averageGrade = 65.8f;
        lessons.add(lesson);
    }
}
