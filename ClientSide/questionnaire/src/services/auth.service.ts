import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginModel } from 'src/app/models/login-model';
import { SignupRequest } from 'src/app/models/signup-request';
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

  public login(user: LoginModel): Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/login`, user,  {observe:'response'});
  }
}
