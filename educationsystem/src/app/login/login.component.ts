import { Component, OnInit, ɵbypassSanitizationTrustResourceUrl } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserDto } from '../services/users';

import { identifierName } from '@angular/compiler';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { AuthserviceService } from '../services/authservice.service';
import { Authentication } from '../services/authentication';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

username:string = "";
password:string = "";
isUserValid:boolean = false;
userType:string = "";
userIdentified : UserDto = {};
authentication : Authentication = {};
signedInUser : UserDto = {};


  constructor(private httpClient: HttpClient, private router : Router, private userService : UserService,
    private autentication : AuthserviceService) { }

  ngOnInit() {
  }

  login():void{
    this.userIdentified = {
      username: this.username,
      password: this.password
    } as Authentication;
  
    const url = 'http://localhost:8083/user/login';
  
    this.httpClient.post<UserDto>(url,this.userIdentified)
      .subscribe(data => {
        if(data != null){
        this.signedInUser = data;
        if (this.signedInUser.activated === false){
          alert("Inaktivált felhasználó nem jelentkezhet be!")
        } else {
           this.userType = this.signedInUser.userType || "";
          localStorage.setItem('user', JSON.stringify(this.signedInUser));
          this.identifyUserType();
          this.userService.setSignedInUser(this.signedInUser);
        }
      
         
        } else alert("Helytelen felhasználónév vagy jelszó!")
      });
  }


identifyUserType(){
  if (this.userType == "STUDENT"){
    this.autentication.setStudent("STUDENT");
    this.router.navigate(['/student']);

  }
  else if(this.userType == "TEACHER"){
    this.autentication.setStudent("TEACHER");
    this.router.navigate(['/teacher']);
    
  }else if(this.userType == "PRINCIPAL_TEACHER"){
    this.autentication.setStudent("PRINCIPAL_TEACHER");
    this.router.navigate(['principal'])

  }else if (this.userType=="ADMIN"){
    this.autentication.setStudent("ADMIN");
    this.router.navigate(["/admin"])

  }
}




}
