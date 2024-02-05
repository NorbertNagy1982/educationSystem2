import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { StudentComponent } from './student/student.component';
import { CourseComponent } from './course/course.component';

const routes: Routes = [

{
  path: "",
  component:LoginComponent
}, 

{
  path:"student",
  component:StudentComponent
},

{
  path:"course",
  component: CourseComponent
}



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
