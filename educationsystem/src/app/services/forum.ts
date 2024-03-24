import { Course } from "./course";
import { UserDto } from "./users";

export interface Forum{

    dtoId?:number;
    message?:string;
    dateOfMessage?:Date;
    userDto?:UserDto;
    enable?:boolean;
    courseDto?:Course;

}