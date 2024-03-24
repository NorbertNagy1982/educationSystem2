import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Course } from './course';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CourseService {

constructor(private httpClient : HttpClient) { }


searchCourseByName(nameExcerpt: string): Observable<Course[]> {
  return this.httpClient.get<Course[]>(`http://localhost:8083/course/findcoursebyexcerpt?course=${nameExcerpt}`);
}


}
