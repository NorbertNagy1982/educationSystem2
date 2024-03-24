import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(private router : Router) { }

  ngOnInit() {
  }

  navigateToUsers():void{
  this.router.navigate(["/manage-users"]);
  }

  navigateToCourse():void{
    this.router.navigate(["/manage-courses"]);
  }

  navigateToForum():void{
    this.router.navigate(["/manage-forum"]);
  }




}
