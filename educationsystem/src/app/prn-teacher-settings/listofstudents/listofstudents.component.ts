import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Course } from 'src/app/services/course';
import { UserDto } from 'src/app/services/users';

@Component({
  selector: 'app-listofstudents',
  templateUrl: './listofstudents.component.html',
  styleUrls: ['./listofstudents.component.css']
})
export class ListofstudentsComponent implements OnInit {

  studentList : UserDto[] = [];
  courseData : Course = {};

  constructor(private httpClient :HttpClient) { }

  ngOnInit() {
    let transferredData = localStorage.getItem("course")
    if (transferredData){
      this.courseData = JSON.parse(transferredData);
    }
    this.getStudentList();
  }

  getStudentList():void{
    let courseId = this.courseData.dtoId;
    this.httpClient.get<UserDto[]>(`http://localhost:8083/course/student?courseid=${courseId}`)
    .subscribe(  
    data =>{
      this.studentList = data;
      console.log(this.studentList)
    }
    )
    }
}
