import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginModel } from 'src/app/login/login-model';
import { SignupRequest } from 'src/app/signup/signup-request';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient){}

  public addUser(user:SignupRequest):Observable<void>{
    return this.http.post<void>(`${this.apiServerUrl}/public/signup`, user);
  }

  public login(user: LoginModel): Observable<void>{
    return this.http.post<void>(`${this.apiServerUrl}/login`, user);
  }
}
