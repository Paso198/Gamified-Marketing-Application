import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DayProduct } from 'src/app/models/day-product';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getProductOfTheDay():Observable<DayProduct>{
    return this.http.get<DayProduct>(`${this.apiServerUrl}/user/products/`);
  }
}
