import { UserCourse } from "./user_course";

export interface Course{

    dtoId?:number;
    code?:string;
    title?:string;
    courseType?:string;
    registrationStart?:Date;
    registrationEnd?:Date;
    courseDescription?:string;
    activated?:boolean;
    limitOfRegisteredStudents?:number;
    numberOfRegisteredStudents?:number;
    examType?:string;
    userCourses?:UserCourse;
    grade?:number;

}