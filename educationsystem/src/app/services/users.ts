import { UserCourse } from "./user_course";

export interface UserDto{

    dtoId?:number;
   userCode?:string;
  userType?:string;
  familyName?:string;
    middleName?:string;
  firstname?:string;
  username?:string;
  password?:string;
 activated?:boolean;
 usercourses? : UserCourse;

}