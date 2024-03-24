import { Injectable } from '@angular/core';
import { UserDto } from './users';

@Injectable({
  providedIn: 'root'
})
export class AuthserviceService {

loginedUser: UserDto = {};


constructor() {
  this.getUserData();
 }


setCurrentUser(user: UserDto): void {
  this.loginedUser = user;
}

getUserData():void{
  let transferredData = localStorage.getItem("user")
  if (transferredData){
    this.loginedUser = JSON.parse(transferredData);
  }
}


getCurrentUser(): UserDto {
  return this.loginedUser;
}

setStudent(userType : string){
  this.loginedUser.userType = userType;
}

setTeacher(userType : string){
  this.loginedUser.userType = userType;
}

setPrincipalTeacher(userType : string){
  this.loginedUser.userType = userType;
}

setAdmin(userType : string){
  this.loginedUser.userType = userType;
}


isAdmin(): boolean {
  return this.loginedUser && this.loginedUser.userType === 'ADMIN';
}

isTeacher(): boolean {
  return this.loginedUser && this.loginedUser.userType === 'TEACHER';
}

isPrincipalTeacher(): boolean {
  return this.loginedUser && this.loginedUser.userType === 'PRINCIPAL_TEACHER';
}

isStudent(): boolean {
  return this.loginedUser && this.loginedUser.userType === 'STUDENT';
}

isAuthenticated(): boolean {
  // Implement authentication logic here
  return !!this.loginedUser; // Return true if user is set
}
}


