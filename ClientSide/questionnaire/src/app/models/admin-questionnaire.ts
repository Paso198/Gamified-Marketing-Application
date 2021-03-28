import { Product } from "./product";

export class AdminQuestionnaire {
    constructor(
        public id:number,
        public title:String,
        public date:Date,
        public product:Product
    ){}
}