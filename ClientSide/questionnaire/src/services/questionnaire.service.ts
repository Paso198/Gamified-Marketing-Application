import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdminQuestionnaire } from 'src/app/models/admin-questionnaire';
import { Question } from 'src/app/models/question';
import { QuestionnaireRequest } from 'src/app/models/questionnaire-request';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class QuestionnaireService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getFutureQuestionnaires():Observable<AdminQuestionnaire[]>{
    return this.http.get<AdminQuestionnaire[]>(`${this.apiServerUrl}/admin/questionnaires/future`);
  }

  public getPastQuestionnaires():Observable<AdminQuestionnaire[]>{
    return this.http.get<AdminQuestionnaire[]>(`${this.apiServerUrl}/admin/questionnaires/past`);
  }

  public deleteQuestionnaire(id:number):Observable<void>{
    return this.http.delete<void>(`${this.apiServerUrl}/admin/questionnaires/`+id);
  }

  public addQuestionnaire(request:QuestionnaireRequest):Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/admin/questionnaires/`,request);
  }

  public getQuestionnaireQuestions(id:number):Observable<Question[]>{
    return this.http.get<Question[]>(`${this.apiServerUrl}/admin/questionnaires/`+id+'/questions');
  }
}
