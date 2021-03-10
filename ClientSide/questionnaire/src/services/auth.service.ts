import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SignupRequest } from 'src/app/signup/signup-request';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient){}

  public addUser(user:SignupRequest):Observable<void>{
    return this.http.post<void>(`${this.apiServerUrl}/public/user/signup`, user);
  }
}
