import { Component, OnInit } from '@angular/core';
import { UserDto } from '../services/users';
import { Course } from '../services/course';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserCourse } from '../services/user_course';

@Component({
  selector: 'app-personalData',
  templateUrl: './personalData.component.html',
  styleUrls: ['./personalData.component.css']
})
export class PersonalDataComponent implements OnInit {

  signedInUser : UserDto = {};
  allRegisteredCoursesList : Course[] = [];
  isStudent : boolean = false;
  coursesOfAStudent : UserCourse[]=[];

  constructor(private httpClient : HttpClient, private router : Router) { }

  ngOnInit() {
    let transferredDataUser = localStorage.getItem("user")
    if (transferredDataUser){
      this.signedInUser = JSON.parse(transferredDataUser);
      if (this.signedInUser.userType === "STUDENT"){
        this.isStudent = true;
      }
    }

    this.getAllRegisteredCoursesByUserCourse();
   
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

getAllRegisteredCoursesByUserCourse():void{
  const userId = this.signedInUser.dtoId;
  this.httpClient.get<Course[]>(`http://localhost:8083/user/allusercourseofuser?userid=${userId}`)
  .subscribe(
    data => {
      this.coursesOfAStudent = data;
    }
  )
}

getBackToMain(){
  if (this.signedInUser.userType === "TEACHER"){
    this.router.navigate(["/teacher"])
  } else if (this.signedInUser.userType === "STUDENT"){
    this.router.navigate(["/student"]);
  }else if (this.signedInUser.userType === "PRINCIPAL_TEACHER"){
    this.router.navigate(["/principal"])
  }
  else {
    this.router.navigate(["/admin"])
  }
}


}
