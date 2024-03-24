import { Component, OnInit } from '@angular/core';
import { UserDto } from '../services/users';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

signedInUser : UserDto = {};
userRole : string = "";
currentDate = new Date();
currentTime?: Date;



  constructor(private router : Router, private userService : UserService) { }

  ngOnInit() {
    let transferredDataUser = localStorage.getItem("user")
    if (transferredDataUser){
      this.signedInUser = JSON.parse(transferredDataUser);
    }
    this.getUserRole();

    this.updateTime();
    
    setInterval(() => {
      this.updateTime();
    }, 1000);
  }


getUserRole():string{
  if (this.signedInUser.userType === "STUDENT"){
    return this.userRole = "Hallgató";
  } else if(this.signedInUser.userType === "TEACHER"){
    return this.userRole = "Tanár";
  }else if(this.signedInUser.userType === "PRINCIPAL_TEACHER")
  return this.userRole = "Vezető tanár";
  else {
    return this.userRole = "Admin"
  }
}


logout():void{
  this.router.navigate(['']);
  this.signedInUser = {};
  this.userRole = "";
}

showPersonalData():void{
  this.router.navigate(['personal']);
  
 
}


updateTime(): void {
  this.currentTime = new Date();
}

}
