import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdminQuestionnaire } from 'src/app/models/admin-questionnaire';
import { LeaderboardUser } from 'src/app/models/leaderboard-user';
import { Question } from 'src/app/models/question';
import { Questionnaire } from 'src/app/models/questionnaire';
import { QuestionnaireRequest } from 'src/app/models/questionnaire-request';
import { Response } from 'src/app/models/response';
import { UserResponse } from 'src/app/models/user-response';
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
  public updateQuestionnaire(id: number, request:QuestionnaireRequest):Observable<any>{
    return this.http.put<any>(`${this.apiServerUrl}/admin/questionnaires/`+id,request);
  }

  public getQuestionnaireQuestions(id:number):Observable<Question[]>{
    return this.http.get<Question[]>(`${this.apiServerUrl}/admin/questionnaires/`+id+'/questions');
  }

  public getQuestionnaireOfTheDay():Observable<Questionnaire>{
        return this.http.get<Questionnaire>(`${this.apiServerUrl}/user/questionnaires`);
  }

  public cancelQuestionnaire():Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/user/questionnaires/cancel`,null);
  }

  public sendQuestionnaire(response:Response):Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/user/responses`,response);
  }

  public getLeaderboard():Observable<LeaderboardUser[]>{
    return this.http.get<LeaderboardUser[]>(`${this.apiServerUrl}/user/questionnaires/leaderboard`);
  }

  public getSubmitters(id):Observable<UserResponse[]>{
    return this.http.get<UserResponse[]>(`${this.apiServerUrl}/admin/questionnaires/`+id+'/users/sent');
  }

  public getCancellers(id):Observable<UserResponse[]>{
    return this.http.get<UserResponse[]>(`${this.apiServerUrl}/admin/questionnaires/`+id+'/users/cancelled');
  }
}
