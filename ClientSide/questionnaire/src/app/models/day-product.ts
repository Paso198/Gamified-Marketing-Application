import { Review } from "./Review";

export class DayProduct {
    constructor(
        public id:number,
        public name:String,
        public photo:Blob,
        public reviews:Review[]
    ){}
}