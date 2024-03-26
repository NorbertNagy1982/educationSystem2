import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrnTeacherSettingsComponent } from './prn-teacher-settings.component';
import { NewCourseComponent } from './new-course/new-course.component';
import { ModifyCourseComponent } from './modify-course/modify-course.component';
import { FormsModule } from '@angular/forms';
import { ManageStudentsComponent } from './manage-students/manage-students.component';
import { ManageTeacherComponent } from './manage-teacher/manage-teacher.component';
import { ListofstudentsComponent } from './listofstudents/listofstudents.component';
import { HeaderComponent } from '../header/header.component';




@NgModule({
  imports: [
    CommonModule,
    FormsModule,
  
  ],
  declarations: [PrnTeacherSettingsComponent,
    NewCourseComponent,
    ModifyCourseComponent,
    ManageStudentsComponent,
    ManageTeacherComponent,
    ListofstudentsComponent,

    
   
 
  ],

 
})
export class PrnTeacherSettingsModule { }
