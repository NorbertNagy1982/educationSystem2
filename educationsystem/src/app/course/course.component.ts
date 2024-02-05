import { Component, OnInit } from '@angular/core';
import { Course } from '../services/course';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  courseData : Course = {};

  constructor() { }

  ngOnInit() {
    let transferredData = localStorage.getItem("course")
    if (transferredData){
      this.courseData = JSON.parse(transferredData);
    }
  }

}
