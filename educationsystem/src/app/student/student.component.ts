import { Component, OnInit } from '@angular/core';
import { Course } from '../services/course';
import { HttpClient } from '@angular/common/http';
import { UserDto } from '../services/users';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {

courseList:Course[] = [];
signedInUser: UserDto = {};
isButtonDisabled:boolean = false;
course: Course = {};

  constructor(private httpClient:HttpClient, private router:Router) { }

  ngOnInit() {
    this.getUserData();
    this.getCourseList();
 
  }

getCourseList():void{
this.httpClient.get<Course[]>(`http://localhost:8083/user/allcourseofuser?userid=${this.signedInUser.dtoId}`)
.subscribe(  
data =>{
  this.courseList = data;
}
)
}

getUserData():void{
  let transferredData = localStorage.getItem("user")
  if (transferredData){
    this.signedInUser = JSON.parse(transferredData);
  }
}

registerToCourse(courseId:any):void{

  const userId = this.signedInUser.dtoId;
  this.httpClient.get<Course>(`http://localhost:8083/course/register?userid=${userId}&courseid=${courseId}`)
.subscribe(
  data => {
    for(let i=0;i<this.courseList.length;i++){
      if (this.courseList[i].dtoId === courseId){
        this.courseList[i] = data;
        this.isButtonDisabled = true;
      }
    }
  }
)
  

}

showDetails(courseId:any):void{
  this.httpClient.get<Course>(`http://localhost:8083/course/id?courseid=${courseId}`)
  .subscribe(
    data => {
          this.course = data;
          localStorage.setItem("course", JSON.stringify(this.course));
          this.router.navigate(["/course"])
    }
  )
 
}


refresh():void{
  window.location.reload();
}




}
