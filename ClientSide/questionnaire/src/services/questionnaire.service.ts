import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdminQuestionnaire } from 'src/app/models/admin-questionnaire';
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
}
