import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Course } from 'src/app/services/course';
import { UserDto } from 'src/app/services/users';

@Component({
  selector: 'app-new-course',
  templateUrl: './new-course.component.html',
  styleUrls: ['./new-course.component.css']
})
export class NewCourseComponent implements OnInit {



  title?:string;
  courseType?:string;
  registrationStart?:Date;
  registrationEnd?:Date;
  courseDescription?:string;
  limitOfRegisteredStudents?:number;
  examType?:string;
  grade?:number;

  signedInUser:UserDto = {};
  allCourses : Course[] =[];
  courseTypeSelected : string = "";
  examTypeSelected : string = "";

  
  constructor(private httpClient : HttpClient, private router : Router) { }

  ngOnInit() {
    let transferredDataUser = localStorage.getItem("user")
    if (transferredDataUser){
      this.signedInUser = JSON.parse(transferredDataUser);
    }

    this.getAllCourses();
  }

  createCourse():void{
  //  this.setCourseType();
 //   this.setExamType();
 console.log(this.courseType)
    let newCourse = {title:this.title, courseType : this.courseType, registrationStart : this.registrationStart, registrationEnd : 
    this.registrationEnd, courseDescription: this.courseDescription, limitOfRegisteredStudents : this.limitOfRegisteredStudents,
  examType : this.examType} as Course
  this.httpClient.post<Course>(`http://localhost:8083/course/create?userid=${this.signedInUser.dtoId}`, newCourse)
  .subscribe(
    data => {
      if (data){
        alert("Sikeres mentés!")
        this.getAllCourses();
      }
    }
  )
  }

  setCourseType():void{
    if(this.courseTypeSelected === "Előadás"){
      this.courseType = "LECTURE";
    }else if (this.courseTypeSelected ==="Gyakorlat"){
      this.courseType = "PRACTICE";
    } else this.courseType = "SEMINAR";
  }

  setExamType():void{
    if(this.examTypeSelected === "Félévi vizsga"){
      this.examType = "FINAL_EXAM";
    }else if (this.examTypeSelected ==="Nincs"){
      this.examType = "NO_EXAM";
    } else this.examType = "PRACTICE";
  }

  getAllCourses():void{
    this.httpClient.get<Course[]>(`http://localhost:8083/course/all`)
    .subscribe(
      data => {
        this.allCourses = data;
      }
    )
  }

  getBack():void{
    if(this.signedInUser.userType === "PRINCIPAL_TEACHER"){
    this.router.navigate(["/principal"])
    }else  this.router.navigate(["manage-courses"])
  }

}
