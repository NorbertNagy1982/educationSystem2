import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Course } from 'src/app/services/course';
import { UserService } from 'src/app/services/user.service';
import { UserCourse } from 'src/app/services/user_course';
import { UserDto } from 'src/app/services/users';

@Component({
  selector: 'app-manage-teacher',
  templateUrl: './manage-teacher.component.html',
  styleUrls: ['./manage-teacher.component.css']
})
export class ManageTeacherComponent implements OnInit {

  users: UserDto[] = [];
  searchTerm: string = '';
  studentChoosen : UserDto = {};
  courseList : Course[] = [];
 courseChoosen : Course = {};
 courseListRegistered : Course[]=[];
 delBtnDisabled:boolean = false;
 regBtnDisabled:boolean = false;

  constructor(private userService: UserService, private httpClient : HttpClient, private router : Router) { }

  ngOnInit() {
  }

  searchUsers(): void {
    this.userService.searchTeachersByName(this.searchTerm)
      .subscribe(users => this.users = users);
  }

  selectName(student: UserDto): void {
    this.studentChoosen =student; 
    this.getCourseList();
    this.getAllRegisteredCourses();
  }

  
  selectCourse(course: Course): void {
    this.courseChoosen =course; 
    this.delBtnDisabled = true;
    this.regBtnDisabled = false;
  }

  selectCourseToDelete(course: Course): void {
    this.courseChoosen =course; 
    this.regBtnDisabled = true;
    this.delBtnDisabled = false;
  }

  getCourseList():void{
    this.httpClient.get<Course[]>(`http://localhost:8083/user/allcourseofuser?userid=${this.studentChoosen.dtoId}`)
    .subscribe(  
    data =>{
      this.courseList = data;
    }
    )
    }

    setSelectedCourse(course : Course){
      this.courseChoosen = course;

    }


    registerToCourse():void{
      const courseId = this.courseChoosen.dtoId;
      const userId = this.studentChoosen.dtoId;
         this.httpClient.put<UserCourse>(`http://localhost:8083/course/register?userid=${userId}&courseid=${courseId}`, null)
    .subscribe(
      data => {
        if(data){
          alert("Sikeres kurzusfelvétel!")
          this.getCourseList();
          this.getAllRegisteredCourses();
        }
      }
    )
    }

    deleteRegisteredCourse():void{
      const courseId = this.courseChoosen.dtoId;
      const userId = this.studentChoosen.dtoId;
      this.httpClient.delete<Course>(`http://localhost:8083/course/delreg?userid=${userId}&courseid=${courseId}`)
      .subscribe(
        data =>{
          if (data){
            alert("Sikeres kurzustörlés")
            this.getCourseList();
            this.getAllRegisteredCourses();
          }
        }
      )
    }

  getAllRegisteredCourses():void{
    const userId = this.studentChoosen.dtoId;
    this.httpClient.get<Course[]>(`http://localhost:8083/user/allcourse?userid=${userId}`)
    .subscribe(
      data => {
        this.courseListRegistered = data;
      }
    )
  }



  getBack():void{
    this.router.navigate(["/principal"])
  }


}
