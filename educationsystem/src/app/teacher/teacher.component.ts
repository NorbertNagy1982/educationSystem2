import { Component, OnInit } from '@angular/core';
import { Course } from '../services/course';
import { UserDto } from '../services/users';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserCourse } from '../services/user_course';

@Component({
  selector: 'app-teacher',
  templateUrl: './teacher.component.html',
  styleUrls: ['./teacher.component.css']
})
export class TeacherComponent implements OnInit {

  signedInUser : UserDto = {};
  allRegisteredCoursesList : Course[] = [];
  allRegisteredStudents : UserDto[] = [];
  allStudentUserCourse : UserCourse[] = [];


  constructor(private httpClient : HttpClient, private router : Router) { }

  ngOnInit() {
    let transferredDataUser = localStorage.getItem("user")
    if (transferredDataUser){
      this.signedInUser = JSON.parse(transferredDataUser);
    }

    this.getAllRegisteredCourses();
  }

  getAllRegisteredCourses(){
    const userId = this.signedInUser.dtoId;
    this.httpClient.get<Course[]>(`http://localhost:8083/user/allcourse?userid=${userId}`)
    .subscribe(
      data => {
            this.allRegisteredCoursesList = data;
  }
    )
  }



  showDetails(courseId:any):void{
    this.httpClient.get<UserCourse[]>(`http://localhost:8083/user/allstudentusercourse?courseid=${courseId}`)
    .subscribe(
      data => {
            this.allStudentUserCourse= data;
            localStorage.setItem("coursestudent", JSON.stringify(this.allStudentUserCourse));
            localStorage.setItem("actualcourse", JSON.stringify(courseId));
            this.router.navigate(["/coursestudents"])
      }
    )
   
  }


}
