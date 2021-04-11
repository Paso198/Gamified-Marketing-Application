import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddQuestion } from 'src/app/models/add-question';
import { Question } from 'src/app/models/question';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getAllQuestions():Observable<Question[]>{
    return this.http.get<Question[]>(`${this.apiServerUrl}/admin/questions`);
  }

  public addQuestion(question:AddQuestion):Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/admin/questions`,question);
  }
}
