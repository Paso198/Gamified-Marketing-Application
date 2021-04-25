import { Question } from "./question";

export class AdminAnswer {
    constructor(
        public id:number,
        public question:Question,
        public text:string
    ){}
}