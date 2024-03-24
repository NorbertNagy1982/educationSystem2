import { HttpBackend, HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Course } from 'src/app/services/course';
import { CourseService } from 'src/app/services/course.service';
import { Forum } from 'src/app/services/forum';
import { UserService } from 'src/app/services/user.service';
import { UserDto } from 'src/app/services/users';

@Component({
  selector: 'app-manage-forum',
  templateUrl: './manage-forum.component.html',
  styleUrls: ['./manage-forum.component.css']
})
export class ManageForumComponent implements OnInit {

  signedInUser : UserDto = {};
  searchTerm: string = "";
  user: UserDto[] = [];
  userChoosen : UserDto = {};
  disableInactivateBtn : boolean = false;
showTable : boolean = false;
forumList : Forum[] = [];
showEmptyMessage : boolean = false;
emptyMessage : string = "Nincs megjeleníthető fórumüzenet!";
forumId? : number;
courseList : Course[] = [];
courseChoosen: Course = {};

showSearchByUser : boolean = false;
showSearchByCourse : boolean = false;
showInputField : boolean = false;

  constructor(private userService : UserService, private httpClient : HttpClient, private courseService : CourseService,
    private router :Router) { }

  ngOnInit() {
    let transferredDataUser = localStorage.getItem("user")
    if (transferredDataUser){
      this.signedInUser = JSON.parse(transferredDataUser);
    }
  }

  searchUsers(): void {
    this.userService.searchAllUsersByName(this.searchTerm)
      .subscribe(user => this.user = user);
  }

  searchCourse():void{
    this.courseService.searchCourseByName(this.searchTerm)
    .subscribe(course => this.courseList = course)
  }

  selectName(user: UserDto): void {
    this.userChoosen =user; 
    if(this.userChoosen.dtoId === this.signedInUser.dtoId){
      this.disableInactivateBtn = true;
    } else this.disableInactivateBtn = false;
    this.showTable = true;
    this.getAllForumEntryByUser();
    console.log(this.userChoosen.familyName)
  }

  selectCourse(course: Course): void {
    this.courseChoosen = course;
    this.getAllForumEntryByCourse();
  }

  getAllForumEntryByUser():void{
    this.httpClient.get<Forum[]>(`http://localhost:8083/forum/allbyuser?userid=${this.userChoosen.dtoId}`)
    .subscribe(
      data => {
      if(data.length>0){
        this.forumList = [];
        this.forumList = data;
        this.showEmptyMessage = false;
      }
    
      else {
        this.forumList = [];
        this.showEmptyMessage = true;
      }
  }
    )
  }

disableForumEntry():void{
    this.httpClient.put<Forum>(`http://localhost:8083/forum/disable?forumid=${this.forumId}&familyname=${this.userChoosen.familyName}`, null)
.subscribe(
  data => {
  if(data){
    alert("Sikeres fórumtörlés!")
    this.getAllForumEntryByUser();
  }
  else alert("Invalid id VAGY az adott forumbejegyzés nem a kiválasztott felhasználóhoz tartozik!")
  }
)
}

toggleUserSearch():void{
  this.showSearchByUser = true;
  this.showSearchByCourse = false;
  this.showInputField = true;
}

toggleCourseSearch():void{
  this.showSearchByCourse = true;
  this.showSearchByUser = false;
  this.showInputField = true;
}


getAllForumEntryByCourse():void{
  this.httpClient.get<Forum[]>(`http://localhost:8083/forum/all?courseid=${this.courseChoosen.dtoId}`)
  .subscribe(
    data => {
    if(data.length>0){
      this.forumList = [];
      this.forumList = data;
      this.showEmptyMessage = false;
      console.log(this.courseChoosen.title)
    }
    else {
      this.forumList = [];
      this.showEmptyMessage = true;
    }
}
  )
}


getBack():void{
  this.router.navigate(["admin"])
}

}
