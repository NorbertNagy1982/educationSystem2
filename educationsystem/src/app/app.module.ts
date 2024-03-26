import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { StudentComponent } from './student/student.component';
import { CourseComponent } from './course/course.component';
import { ForumComponent } from './forum/forum.component';
import { HeaderComponent } from './header/header.component';
import { PersonalDataComponent } from './personalData/personalData.component';
import { TeacherComponent } from './teacher/teacher.component';
import { CoursestudentsComponent } from './coursestudents/coursestudents.component';
import { SetgradeComponent } from './setgrade/setgrade.component';
import { PrnTeacherSettingsModule } from './prn-teacher-settings/prn-teacher-settings.module';
import { AdminModule } from './admin/admin.module';
import { AuthserviceService } from './services/authservice.service';
import { PrnTeacherSettingsComponent } from './prn-teacher-settings/prn-teacher-settings.component';
import { NewCourseComponent } from './prn-teacher-settings/new-course/new-course.component';



@NgModule({
  declarations: [										
    AppComponent,
      LoginComponent,
      StudentComponent,
      CourseComponent,
      ForumComponent,
      HeaderComponent,
      PersonalDataComponent,
      TeacherComponent,
      CoursestudentsComponent,
      SetgradeComponent,

    
      
     
   ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    PrnTeacherSettingsModule,
    AdminModule
    
  ],
  providers: [],
  bootstrap: [AppComponent],
  exports:[HeaderComponent]
})
export class AppModule { }
