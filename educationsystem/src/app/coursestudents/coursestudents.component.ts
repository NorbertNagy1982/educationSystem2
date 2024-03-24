import { Component, OnInit } from '@angular/core';
import { UserDto } from '../services/users';
import { Route, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserCourse } from '../services/user_course';
import { Course } from '../services/course';

@Component({
  selector: 'app-coursestudents',
  templateUrl: './coursestudents.component.html',
  styleUrls: ['./coursestudents.component.css']
})
export class CoursestudentsComponent implements OnInit {

allRegisteredStudents : UserDto[] = [];
actualCourseId : number = 0;
actualCourse : Course = {};
allUserCourse : UserCourse[] = [];
selectedGrade : number = 0;

  constructor(private router : Router, private httpClient : HttpClient) { }

  ngOnInit() {
 

    let courseId = localStorage.getItem("actualcourse")
    if (courseId){
      this.actualCourseId = JSON.parse(courseId);
    }
    this.getAllStudentUserCourses();
    this.getGrade();
    this.findCourseById();
  }


  openSetGrade(userId:any):void{
    localStorage.setItem("actualuser", JSON.stringify(userId))
    this.router.navigate(["/grade"])
  }

  getAllUserCourses():void{
  this.httpClient.get<UserCourse[]>(`http://localhost:8083/user/allusercourse`)
  .subscribe(
    data => {
      this.allUserCourse = data;
    }
  )
  }

getAllStudentUserCourses():void{
    this.httpClient.get<UserCourse[]>(`http://localhost:8083/user/allstudentusercourse?courseid=${this.actualCourseId}`)
    .subscribe(
      data => {
            this.allUserCourse= data;
      }
    )
   
  }




  getGrade():number{
    let userId = 0;
 let  userIdTransferred = localStorage.getItem("useridtograding")
 if (userIdTransferred){
  userId = JSON.parse(userIdTransferred);
 }
   let grade =  this.httpClient.get<number>(`http://localhost:8083/user/getgrade?courseid=${this.actualCourseId}&userid=${userId}`)
   .subscribe(
    data => {
      return data;
    }
   )
   return 0;
  }

  navigateToForum():void{
    this.router.navigate(["/forum"]);
  }

findCourseById():void{
  this.httpClient.get<Course>(`http://localhost:8083/course/id?courseid=${this.actualCourseId}`)
  .subscribe(
    data =>{
      this.actualCourse = data;
      localStorage.setItem("course", JSON.stringify(this.actualCourse))
    }
  )
}

getBack():void{
  this.router.navigate(["/teacher"])
}

}
