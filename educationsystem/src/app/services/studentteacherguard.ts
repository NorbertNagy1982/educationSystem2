import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthserviceService } from './authservice.service';
import { Injectable } from '@angular/core';



@Injectable({
  providedIn: 'root'
})
export class StudentTeacherGuard implements CanActivate {
  constructor(private authService: AuthserviceService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.isTeacher() || this.authService.isStudent() || this.authService.isPrincipalTeacher()) {
      return true;
    } else {
      alert("Forbidden")
      return this.router.parseUrl('/forbidden');
    }
  }
}


