export class QuestionnaireRequest {
    constructor(
        public title:string,
        public productId:number,
        public date:string,
        public questionsIds:number[]
    ){}
}