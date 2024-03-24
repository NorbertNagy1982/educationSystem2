import { Component, OnInit } from '@angular/core';
import { Course } from '../services/course';
import { HttpClient } from '@angular/common/http';
import { Forum } from '../services/forum';
import { Router } from '@angular/router';
import { UserDto } from '../services/users';


@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html',
  styleUrls: ['./forum.component.css']
})
export class ForumComponent implements OnInit {

courseData : Course = {};
forumList : Forum[] = [];
forumMessage? : string;
user: UserDto = {};

  constructor(private httpClient : HttpClient, private router : Router) { }

  ngOnInit() {
   

    

    let userTransferredData = localStorage.getItem("user")
    if (userTransferredData){
      this.user = JSON.parse(userTransferredData);
    }
    let courseTransferredData = localStorage.getItem("course")
    if (courseTransferredData){
      this.courseData = JSON.parse(courseTransferredData);
    }

    this.getAllForumEntry()

  }

 

  createForumEntry():void{
    console.log("user"+this.courseData.dtoId)
    const newForumEntry = {
      message: this.forumMessage,
      dateOfMessage: new Date(),
    } as  Forum;

    const url = `http://localhost:8083/course/forum?userid=${this.user.dtoId}&courseid=${this.courseData.dtoId}`;

    if (newForumEntry.message != null){
    this.httpClient.post<Forum>(url, newForumEntry)
    .subscribe(
      ()=>{
      location.reload()
    })
  }
  else alert("Üres fórumüzenet nem küldhető!")

  }

getAllForumEntry():void{
  const courseId = this.courseData.dtoId
  this.httpClient.get<Forum[]>(`http://localhost:8083/forum/all?courseid=${this.courseData.dtoId}`)
  .subscribe(
    data => {
          this.forumList = data;
}
  )
}


getBack():void{
  if(this.user.userType==="TEACHER"){
    this.router.navigate(["/coursestudents"])
  }
  else if(this.user.userType==="STUDENT"){
    this.router.navigate(["/course"])
  }
  else if(this.user.userType==="PRINCIPAL_TEACHER"){
    this.router.navigate(["/principal"])
  }

}

}