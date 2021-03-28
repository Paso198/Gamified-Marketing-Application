import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtService } from 'src/services/jwt.service';
import { environment } from 'src/environments/environment';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private jwtService:JwtService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if(this.jwtService.isLogged){
      let logged:String = this.jwtService.getLoggedJwt();
      let isApiUrl = request.url.startsWith(environment.apiBaseUrl);
      request = request.clone({
        setHeaders: {
            Authorization: `${logged}`
        }
    });
    }
    return next.handle(request);
  }
}
