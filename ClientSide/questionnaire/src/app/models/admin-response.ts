import { AdminAnswer } from "./admin-answer";

export class AdminResponse {
    constructor(
        public id:number,
        public answers:AdminAnswer[],
        public age:number,
        public gender:string,
        public expertiseLevel:string
    ){}
}