import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserDto } from '../services/users';
import { identifierName } from '@angular/compiler';
import { Router } from '@angular/router';

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


  constructor(private httpClient: HttpClient, private router : Router) { }

  ngOnInit() {
  }

  login():void{
    const userCredentialsPost = {
      username: this.username,
      password: this.password
    } as UserDto;
  
    const url = 'http://localhost:8083/user/login';
  
    this.httpClient.post<UserDto>(url, userCredentialsPost)
      .subscribe(data => {
        if(data != null){
        const userIdentified = data;
        this.username = userIdentified.username || "";
        this.password = userIdentified.password || "";
   
          this.isUserValid = true;
          this.userType = userIdentified.userType || "";
          localStorage.setItem('user', JSON.stringify(userIdentified));
          this.identifyUserType();
        } else alert("Helytelen felhasználónév vagy jelszó!")
      });
   
  }


identifyUserType(){
  if (this.userType == "STUDENT"){
    this.router.navigate(['/student']);
  }
}




}
