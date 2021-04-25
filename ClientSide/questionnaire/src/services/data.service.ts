import { Injectable } from '@angular/core';
import { AdminQuestionnaire } from 'src/app/models/admin-questionnaire';
import { Product } from 'src/app/models/product';
import { Question } from 'src/app/models/question';
import { UserResponse } from 'src/app/models/user-response';

@Injectable()
export class DataService {
  constructor() { }

  public getQuestionnaireData():AdminQuestionnaire{
    return  JSON.parse(sessionStorage.getItem('questionnaire'));
  }

  public setQuestionnaireData(data:AdminQuestionnaire){
    return  sessionStorage.setItem("questionnaire", JSON.stringify(data));
  }

  public setUserData(data:UserResponse){
    return  sessionStorage.setItem("user", JSON.stringify(data));
  }

  public getUserData():UserResponse{
    return  JSON.parse(sessionStorage.getItem('user'));
  }

  public clean(){
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('questionnaire');
  }
}
