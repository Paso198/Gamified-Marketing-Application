import { Injectable } from '@angular/core';
import { AdminQuestionnaire } from 'src/app/models/admin-questionnaire';
import { Product } from 'src/app/models/product';
import { Question } from 'src/app/models/question';

@Injectable()
export class DataService {
  constructor() { }

  public getQuestionnaireData():AdminQuestionnaire{
    return  JSON.parse(sessionStorage.getItem('questionnaire'));
  }

  public setQuestionnaireData(data:AdminQuestionnaire){
    return  sessionStorage.setItem("questionnaire", JSON.stringify(data));
  }
}
