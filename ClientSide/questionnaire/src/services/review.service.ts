import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ReviewRequest } from 'src/app/models/review-request';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public addReview(review:ReviewRequest):Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/user/products/reviews`,review);
  }
}
