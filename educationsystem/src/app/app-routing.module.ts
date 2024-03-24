import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { StudentComponent } from './student/student.component';
import { CourseComponent } from './course/course.component';
import { ForumComponent } from './forum/forum.component';
import { PersonalDataComponent } from './personalData/personalData.component';
import { HeaderComponent } from './header/header.component';
import { TeacherComponent } from './teacher/teacher.component';
import { CoursestudentsComponent } from './coursestudents/coursestudents.component';
import { SetgradeComponent } from './setgrade/setgrade.component';
import { PrnTeacherSettingsComponent } from './prn-teacher-settings/prn-teacher-settings.component';
import { NewCourseComponent } from './prn-teacher-settings/new-course/new-course.component';
import { ModifyCourseComponent } from './prn-teacher-settings/modify-course/modify-course.component';
import { ManageStudentsComponent } from './prn-teacher-settings/manage-students/manage-students.component';
import { ManageTeacherComponent } from './prn-teacher-settings/manage-teacher/manage-teacher.component';
import { AdminComponent } from './admin/admin.component';
import { ManageUsersComponent } from './admin/manage-users/manage-users.component';
import { ManageCoursesComponent } from './admin/manage-courses/manage-courses.component';
import { InactivateCourseComponent } from './admin/inactivate-course/inactivate-course.component';
import { ManageForumComponent } from './admin/manage-forum/manage-forum.component';
import {AdminGuard} from './services/adminguard';
import { StudentGuard } from './services/studentguard';
import { TeacherGuard } from './services/teacherguard';
import { PrincipalTeacherGuard } from './services/principalteacherguard';
import { StudentTeacherGuard } from './services/studentteacherguard';
import { ListofstudentsComponent } from './prn-teacher-settings/listofstudents/listofstudents.component';



const routes: Routes = [

{
  path: "",
  component:LoginComponent
}, 

{
  path:"student",
  component:StudentComponent,
  canActivate: [StudentGuard]
  
},

{
  path:"course",
  component: CourseComponent,
  canActivate:[StudentGuard]
},

{
  path:"forum",
  component: ForumComponent,
 canActivate:[StudentTeacherGuard]

},

{
  path:"personal",
  component: PersonalDataComponent
},

{
  path:"header",
  component:HeaderComponent

},

{
  path:"teacher",
  component:TeacherComponent,
  canActivate:[TeacherGuard]
},

{
  path:"coursestudents",
  component:CoursestudentsComponent,
  canActivate:[TeacherGuard]
},

{
  path:"grade",
  component: SetgradeComponent,
  canActivate:[TeacherGuard]
}, 

{
  path:"principal",
  component:PrnTeacherSettingsComponent,
  canActivate:[PrincipalTeacherGuard]
},

{
path:"new-course",
component:NewCourseComponent,
canActivate:[PrincipalTeacherGuard]
},

{
  path:"modify-course",
  component:ModifyCourseComponent,
  canActivate:[PrincipalTeacherGuard]
},

{
  path:"manage-students",
  component:ManageStudentsComponent,
  canActivate:[PrincipalTeacherGuard]
},
{
  path:"manage-teachers",
  component: ManageTeacherComponent,
  canActivate:[PrincipalTeacherGuard]
},
{
  path:"student-list",
  component:ListofstudentsComponent,
  canActivate:[PrincipalTeacherGuard]
},
{
  path:"admin",
  component:AdminComponent,
  canActivate: [AdminGuard]
},
{
  path: "manage-users",
  component:ManageUsersComponent,
  canActivate:[AdminGuard]
},
{
  path:"manage-courses",
  component:ManageCoursesComponent,
  canActivate:[AdminGuard]
},
{
  path:"inactivate-course",
  component: InactivateCourseComponent,
  canActivate:[AdminGuard]
},
{
  path:"manage-forum",
  component:ManageForumComponent,
  canActivate:[AdminGuard]
}





];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
