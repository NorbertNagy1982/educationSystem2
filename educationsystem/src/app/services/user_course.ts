import { Course } from "./course";
import { UserDto } from "./users";

export interface UserCourse {

dtoId?:number;
userDto?:UserDto;
courseDto?:Course;
grade?:number;


}