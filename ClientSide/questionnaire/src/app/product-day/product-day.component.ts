import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { ProductService } from 'src/services/product.service';
import { DayProduct } from '../models/day-product';

@Component({
  selector: 'app-product-day',
  templateUrl: './product-day.component.html',
  styleUrls: ['./product-day.component.css']
})
export class ProductDayComponent implements OnInit {

  product:DayProduct;
  available:Boolean;
  errorMessage:String;

  constructor(
    private router: Router,
    private productService:ProductService,
    private jwtService:JwtService) { }

  ngOnInit(): void {
    this.errorMessage="";
    this.initializeProduct();
  }

  initializeProduct():void{
    this.productService.getProductOfTheDay().subscribe(
      res=>this.product=res,
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
      }
      else if([404].indexOf(error.status) !== -1){
        this.available = false;
        for (let prop in error.error) {
          this.errorMessage = this.errorMessage+"\n"+ prop+": "+error.error[prop];
          }
      }
      }
    )
  }
}
