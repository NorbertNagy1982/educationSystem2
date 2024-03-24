import { Component, OnInit } from '@angular/core';
import { Course } from '../services/course';
import { UserDto } from '../services/users';
import { HttpClient } from '@angular/common/http';
import { UserCourse } from '../services/user_course';
import { Forum } from '../services/forum';
import { Router } from '@angular/router';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  courseData : Course = {};
  studentList : UserDto[] = [];
  signedInUser : UserDto = {};
  forumList : Forum[] = [];
 

  constructor(private httpClient : HttpClient, private router :Router) { }

  ngOnInit() {
    let transferredData = localStorage.getItem("course")
    if (transferredData){
      this.courseData = JSON.parse(transferredData);
    }

    let transferredDataUser = localStorage.getItem("user")
    if (transferredDataUser){
      this.signedInUser = JSON.parse(transferredDataUser);
    }
    
    this.getStudentList();
  }


  getStudentList():void{
    let courseId = this.courseData.dtoId;
    this.httpClient.get<UserDto[]>(`http://localhost:8083/course/student?courseid=${courseId}`)
    .subscribe(  
    data =>{
      this.studentList = data;
    }
    )
    }

    getUserData():void{
      let transferredData = localStorage.getItem("user")
      if (transferredData){
        this.signedInUser = JSON.parse(transferredData);
      }
    }

    registerToCourse():void{
      const courseId = this.courseData.dtoId;
      const userId = this.signedInUser.dtoId;
         this.httpClient.put<UserCourse>(`http://localhost:8083/course/register?userid=${userId}&courseid=${courseId}`, null)
    .subscribe(
      data => {
      }
    )
    location.reload();   
    }

    deleteRegistryFromCourse():void{
      const courseId = this.courseData.dtoId;
      const userId = this.signedInUser.dtoId;
         this.httpClient.delete<UserCourse>(`http://localhost:8083/course/delreg?userid=${userId}&courseid=${courseId}`)
    .subscribe(
      data => {
      }
    )
    location.reload();   
    }


    openForum():void{
      const courseId = this.courseData.dtoId
      this.httpClient.get<Forum[]>(`http://localhost:8083/forum/all?courseid=${courseId}`)
      .subscribe(
        data => {
              this.forumList = data;
              localStorage.setItem("forum", JSON.stringify(this.forumList));
              this.router.navigate(["/forum"])
              console.log("success")
        }
      )
    }

backToCourseList():void{
 this.router.navigate(["/student"])

}
}