import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manage-courses',
  templateUrl: './manage-courses.component.html',
  styleUrls: ['./manage-courses.component.css']
})
export class ManageCoursesComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }

  navigateToNewCourse():void{
    this.router.navigate(["/new-course"])
  }

  navigateToModifyCourse():void{
    this.router.navigate(["/modify-course"])
  }

  navigateToManageStudents():void{
    this.router.navigate(["/manage-students"])
  }

  navigateToInactivateCourse():void{
    this.router.navigate(["/inactivate-course"]);
  }

  getBack():void{
    this.router.navigate(["admin"])
  }

}
