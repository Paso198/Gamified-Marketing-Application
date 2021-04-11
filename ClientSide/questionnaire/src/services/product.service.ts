import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DayProduct } from 'src/app/models/day-product';
import { Product } from 'src/app/models/product';
import { ProductRequest } from 'src/app/models/product-request';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getProductOfTheDay():Observable<DayProduct>{
    return this.http.get<DayProduct>(`${this.apiServerUrl}/user/products`);
  }
  
  public getProducts():Observable<Product[]>{
    return this.http.get<Product[]>(`${this.apiServerUrl}/admin/products`);
  }

  public addProduct(request:FormData):Observable<void>{
    return this.http.post<void>(`${this.apiServerUrl}/admin/products`, request);
  }
}
