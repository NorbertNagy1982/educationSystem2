import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthserviceService } from 'src/app/services/authservice.service';
import { Course } from 'src/app/services/course';
import { UserDto } from 'src/app/services/users';

@Component({
  selector: 'app-modify-course',
  templateUrl: './modify-course.component.html',
  styleUrls: ['./modify-course.component.css']
})
export class ModifyCourseComponent implements OnInit {

  allCourses : Course[] = [];
  signedInUser : UserDto = {};
  courseCodeList : string[] = [];
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
    this.getCourseCodes();
  }


  getAllCourses():void{
    this.httpClient.get<Course[]>(`http://localhost:8083/course/all`)
    .subscribe(
      data => {
        this.allCourses = data;
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


  getBack():void{
    if(this.signedInUser.userType==="PRINCIPAL_TEACHER"){
    this.router.navigate(["/principal"])
    } else this.router.navigate(["manage-courses"])
  }

}
