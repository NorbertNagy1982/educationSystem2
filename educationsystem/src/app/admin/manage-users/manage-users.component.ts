import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { UserDto } from 'src/app/services/users';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {

signedInUser : UserDto = {};

userType : string = "";
familyName : string = "";
middleName : string = "";
firstName : string = "";
userName : string = "";
password : string = "";
newUser : UserDto = {};

searchTerm: string = "";
user: UserDto[] = [];
userChoosen : UserDto = {};

userTypeMod? : string;
familyNameMod? : string;
middleNameMod? : string;
firstNameMod? : string;
userNameMod? : string;
passwordMod? : string;

disableInactivateBtn : boolean = false;
showTable : boolean = false;



  constructor(private httpClient:HttpClient, private userService:UserService, private router :Router) { }

  ngOnInit() {
    let transferredDataUser = localStorage.getItem("user")
    if (transferredDataUser){
      this.signedInUser = JSON.parse(transferredDataUser);
      console.log(this.signedInUser)
    }
  }

  createUser():void{
    this.newUser = {userType : this.userType, familyName: this.familyName, middleName: this.middleName, 
    firstname: this.firstName, username: this.userName, password: this.password} as UserDto;
    this.httpClient.post<UserDto>(`http://localhost:8083/user/save`, this.newUser)
    .subscribe(
      data =>{
         if (data){
          alert("Új felhasználó elmentve!")
         }
      }
    )
  }

  searchUsers(): void {
    this.userService.searchAllUsersByName(this.searchTerm)
      .subscribe(user => this.user = user);
  }


  selectName(user: UserDto): void {
    this.userChoosen =user; 
    if(this.userChoosen.dtoId === this.signedInUser.dtoId){
      this.disableInactivateBtn = true;
    } else this.disableInactivateBtn = false;
    this.showTable = true;
  }

  modifyUser():void{
    const modifiedUser = {userType : this.userTypeMod, familyName: this.familyNameMod, middleName: this.middleNameMod, 
      firstname: this.firstNameMod, username: this.userNameMod, password: this.passwordMod} as UserDto;
      this.httpClient.put<UserDto>(`http://localhost:8083/user/modifyuser?userid=${this.userChoosen.dtoId}`, modifiedUser)
      .subscribe(
        data => {
          if (data){
            alert("Sikeres módosítás")
            this.searchUsers();
          }
        }
      )
  }

  inactivateUser():void{
    this.httpClient.put<UserDto>(`http://localhost:8083/user/inactivateuser?userid=${this.userChoosen.dtoId}`, null)
    .subscribe(
      data => {
        if(data){
          alert("Felhasználó inaktiválva")
        }
      }
    )
  
  }

  getBack():void{
    this.router.navigate(["admin"])
  }

}
