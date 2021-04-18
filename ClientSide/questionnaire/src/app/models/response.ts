import { Answer } from "./answer";

export class Response {
    constructor(
        public questionnaireId:number,
        public answers:Answer[],
        public age:number,
        public gender:string,
        public expertiseLevel:string
    ){}
}