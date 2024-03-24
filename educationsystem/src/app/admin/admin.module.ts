import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminComponent } from './admin.component';
import { FormsModule } from '@angular/forms';
import { ManageUsersComponent } from './manage-users/manage-users.component';
import { ManageCoursesComponent } from './manage-courses/manage-courses.component';
import { InactivateCourseComponent } from './inactivate-course/inactivate-course.component';
import { ManageForumComponent } from './manage-forum/manage-forum.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [AdminComponent,
    ManageUsersComponent,
    ManageCoursesComponent,
    InactivateCourseComponent,
    ManageForumComponent
  ]
})
export class AdminModule {




 }
