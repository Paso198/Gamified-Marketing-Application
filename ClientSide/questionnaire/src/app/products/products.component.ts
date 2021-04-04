import { Component, OnInit, ViewChild } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import {MatTableDataSource} from '@angular/material/table';
import { JwtService } from 'src/services/jwt.service';
import { Router } from '@angular/router';
import { ProductService } from 'src/services/product.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';



@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {

  constructor(private sanitizer:DomSanitizer,
    private productService:ProductService,
    private jwtService:JwtService,
    private router: Router) { }
  
  @ViewChild(MatSort) sort:MatSort;
  @ViewChild(MatPaginator) paginator:MatPaginator;

  displayedColumns: string[] = ['id', 'name', 'thumb'];
  dataSource = new MatTableDataSource([]);
  available:boolean;
  

  ngOnInit(): void {
    this.getProducts();
    this.dataSource.sort= this.sort;
    this.dataSource.paginator= this.paginator;
    this.available=false;
  }

  public getSrc(raw){
     return ('data:image/png;base64,' 
                 + raw);
  }

  public applyFilter(text:String){
    this.dataSource.filter=text.trim().toLowerCase();
  }


  getProducts():void {
    this.productService.getProducts().subscribe(
      res=>{this.dataSource=new MatTableDataSource(res);
        this.dataSource.filterPredicate = function(data, filter: string): boolean {
          return data.name.toLowerCase().includes(filter) || data.id.toString().toLowerCase().includes(filter) 
      };
        this.dataSource.sort= this.sort;
        this.dataSource.paginator= this.paginator;
        if(res.length>0)
           this.available=true;
      },
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
      }
      }
    )
  }

  
  

}
