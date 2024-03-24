import { Component, OnInit } from '@angular/core';
import { UserDto } from '../services/users';
import { Course } from '../services/course';
import { HttpClient } from '@angular/common/http';
import { UserCourse } from '../services/user_course';
import { Router } from '@angular/router';

@Component({
  selector: 'app-setgrade',
  templateUrl: './setgrade.component.html',
  styleUrls: ['./setgrade.component.css']
})
export class SetgradeComponent implements OnInit {

actualUser : UserDto = {};
actualCourse : Course = {};
selectedGrade : number = 0;

courseId : number = 0;
userId : number = 0;


  constructor(private httpClient :HttpClient, private router : Router) { }

  ngOnInit() {
this.getActualUser();

this.getActualCourse();

console.log(this.actualUser + " " + this.actualCourse)
  }


  getActualUser():void{
    let userTransferred = localStorage.getItem("actualuser")
if (userTransferred){
  let userId= JSON.parse(userTransferred);
  this.userId = userId;
  this.httpClient.get<UserDto>(`http://localhost:8083/user/id?userid=${userId}`)
  .subscribe(
    data => {
      this.actualUser = data;
      localStorage.setItem("useridtograding", JSON.stringify(userId));
    }
  )
}
  }

  getActualCourse():void{
    let courseTransferred = localStorage.getItem("actualcourse")
if (courseTransferred){
  let courseId = JSON.parse(courseTransferred);
  this.courseId = courseId;
  this.httpClient.get<Course>(`http://localhost:8083/course/id?courseid=${courseId}`)
  .subscribe(
    data => {
      this.actualCourse = data;
      localStorage.setItem("courseidtograding", JSON.stringify(courseId));
    }
  )
}
  }

setGrade():void{
  this.httpClient.put<UserCourse>(`http://localhost:8083/user/grade?courseid=${this.courseId}&userid=${this.userId}&grade=${this.selectedGrade}`, null)
.subscribe(
  data => {
   this.router.navigate(["/coursestudents"]) 
  }
)
}




  }






