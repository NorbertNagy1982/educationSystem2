import { Component, OnInit } from '@angular/core';
import { Course } from '../services/course';
import { UserCourse } from '../services/user_course';
import { UserDto } from '../services/users';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-prn-teacher-settings',
  templateUrl: './prn-teacher-settings.component.html',
  styleUrls: ['./prn-teacher-settings.component.css']
})
export class PrnTeacherSettingsComponent implements OnInit {

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


  createNewCourse():void{
    this.router.navigate(["/new-course"])
  }

  modifyCourse():void{
    this.router.navigate(["/modify-course"]);
  }

  manageStudents():void{
    this.router.navigate(["/manage-students"])
  }

  manageTeachers():void{
    this.router.navigate(["/manage-teachers"])
  }

  getCourseData(course:Course){
    localStorage.setItem("course", JSON.stringify(course));
    this.router.navigate(["/student-list"]);
  }

  getCourseDataToForum(course:Course){
    localStorage.setItem("course", JSON.stringify(course));
    this.router.navigate(["/forum"]);
  }

}
