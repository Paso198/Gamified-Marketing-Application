import { DayProduct } from "./day-product";
import { Question } from "./question";

export class Questionnaire {
    constructor(
        public id:number,
        public title:string,
        public product: DayProduct,
        public questions:Question[]
    ){}
}