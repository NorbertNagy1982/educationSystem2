import { Injectable } from '@angular/core';
import { UserDto } from './users';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {



  signedInUser : UserDto = {};

constructor(private httpClient : HttpClient) { }


setSignedInUser(user : UserDto){
  this.signedInUser = user;
}


getSignedInUser(): UserDto{
  return this.signedInUser;
}


searchUsersByName(nameExcerpt: string): Observable<UserDto[]> {
  return this.httpClient.get<UserDto[]>(`http://localhost:8083/user/finduser?name=${nameExcerpt}`);
}

searchTeachersByName(nameExcerpt: string): Observable<UserDto[]> {
  return this.httpClient.get<UserDto[]>(`http://localhost:8083/user/findteacher?name=${nameExcerpt}`);
}

searchAllUsersByName(nameExcerpt: string): Observable<UserDto[]> {
  return this.httpClient.get<UserDto[]>(`http://localhost:8083/user/finduserbyexcerpt?name=${nameExcerpt}`);
}

}
