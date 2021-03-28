import { NullTemplateVisitor } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { Jwt } from '../app/models/jwt';
import { Role } from '../app/models/role';


@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor() { }

  public createSession(header): void{
    sessionStorage.setItem('token', header);
  }

  public parseJwt(token): Jwt{
      var base64Url = token.split('.')[1];
      var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      var jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));
      let parsed = JSON.parse(jsonPayload);
      let jwt = new Jwt(token,parsed['sub'],parsed['authorities'][0]['authority']);
      return jwt;
  }

  public logout(){
    sessionStorage.removeItem('token');
  }

  public parseLoggedJwt():Jwt{
    if(sessionStorage.getItem('token'))
     return this.parseJwt(sessionStorage.getItem('token'))
     else
     return null;
  }

  public getLoggedAuthorities():Role{
    return this.parseLoggedJwt().role;
  }

  public getLoggedUsername():String{
    return this.parseLoggedJwt().username;
  }

  public getLoggedJwt():String{
    return sessionStorage.getItem('token');
  }

  public isLogged():Boolean{
    let jwt = sessionStorage.getItem('token');
    if(jwt)
      return true;
    else
    return false;
  }

}
