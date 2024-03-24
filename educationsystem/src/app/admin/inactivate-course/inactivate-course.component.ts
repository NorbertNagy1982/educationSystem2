import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthserviceService } from 'src/app/services/authservice.service';
import { Course } from 'src/app/services/course';
import { UserDto } from 'src/app/services/users';

@Component({
  selector: 'app-inactivate-course',
  templateUrl: './inactivate-course.component.html',
  styleUrls: ['./inactivate-course.component.css']
})
export class InactivateCourseComponent implements OnInit {


  allCourses : Course[] = [];
  allInactivatedCourses : Course[] = [];
  signedInUser : UserDto = {};
  courseCodeList : string[] = [];
  inactivatedCourseCodeList : string[] = [];
  selectedCode : string = "";

  title?:string;
  courseType?:string;
  registrationStart?:Date;
  registrationEnd?:Date;
  courseDescription?:string;
  limitOfRegisteredStudents?:number;
  examType?:string;
  grade?:number;

  constructor(private httpClient : HttpClient, private router : Router, private authentication : AuthserviceService) { }

  ngOnInit() {
    this.signedInUser = this.authentication.getCurrentUser();
    this.getAllCourses();
    this.getAllInactivatedCourses();
    this.getAllInactivatedCourses();
    this.getCourseCodes();
    this.getInactivatedCourseCodes();
  }


  getAllCourses():void{
    this.httpClient.get<Course[]>(`http://localhost:8083/course/all`)
    .subscribe(
      data => {
        this.allCourses = data;
      }
    )
  }

  getAllInactivatedCourses():void{
    this.httpClient.get<Course[]>(`http://localhost:8083/course/allinactivated`)
    .subscribe(
      data => {
        this.allInactivatedCourses = data;
      }
    )
  }

  getCourseCodes():void{
    this.httpClient.get<string[]>(`http://localhost:8083/course/code`)
    .subscribe(
      data => {
        this.courseCodeList = data;
      }
    )
  }

  getInactivatedCourseCodes():void{
    this.httpClient.get<string[]>(`http://localhost:8083/course/inactivatedcode`)
    .subscribe(
      data => {
        this.inactivatedCourseCodeList = data;
      }
    )
  }

  modifyCourse():void{
    let modifiedCourse = {title : this.title, courseType: this.courseType,registrationStart:this.registrationStart,
  registrationEnd:this.registrationEnd, courseDescription:this.courseDescription,activated:true,limitOfRegisteredStudents:this.limitOfRegisteredStudents,
examType:this.examType} as Course;
    this.httpClient.put<Course>(`http://localhost:8083/course/modifycourse?coursecode=${this.selectedCode}`, modifiedCourse)
    .subscribe(
      data =>{
        console.log(data)
        location.reload();
      }
    )
  }

  inactivateCourse():void{
    this.httpClient.put<Course>(`http://localhost:8083/course/inactivatecourse?coursecode=${this.selectedCode}`, null)
    .subscribe(
      data => {
        if(data){
          alert("Kurzus inaktiválva")
          location.reload();
        }
      }
    )
  }

  reactivateCourse():void{
    this.httpClient.put<Course>(`http://localhost:8083/course/reactivatecourse?coursecode=${this.selectedCode}`, null)
    .subscribe(
      data => {
        if(data){
          alert("Kurzus reaktiválva")
          location.reload();
        }
      }
    )
  }

  goBack() {
    window.history.back();
  }


}
