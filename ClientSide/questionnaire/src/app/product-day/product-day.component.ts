import { ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { JwtService } from 'src/services/jwt.service';
import { ProductService } from 'src/services/product.service';
import { AddReviewComponent } from '../add-review/add-review.component';
import { DayProduct } from '../models/day-product';

@Component({
  selector: 'app-product-day',
  templateUrl: './product-day.component.html',
  styleUrls: ['./product-day.component.css']
})
export class ProductDayComponent implements OnInit, OnDestroy {

  product:DayProduct;
  available:Boolean;
  errorMessage:String;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  dataSource = new MatTableDataSource([]);
  obs: Observable<any>;



  constructor(
    private router: Router,
    private productService:ProductService,
    private jwtService:JwtService,
    private dialog:MatDialog,
    private changeDetectorRef: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.product=new DayProduct(0,"",null,[]);
    this.available=false;
    this.dataSource=new MatTableDataSource(this.product.reviews);
    this.obs = this.dataSource.connect();
    this.dataSource.paginator = this.paginator;
    this.initializeProduct();
    
    this.errorMessage="";
    this.changeDetectorRef.detectChanges();
    this.initializeProduct();
  }

  initializeProduct():void{
    this.productService.getProductOfTheDay().subscribe(
      res=>{
        this.available=true;
        this.product=res;
        this.dataSource=new MatTableDataSource(this.product.reviews);
        this.obs = this.dataSource.connect();
        this.dataSource.paginator = this.paginator;
       },
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
      }
      else if([404].indexOf(error.status) !== -1){
        this.errorMessage="";
        this.available = false;
        for (let prop in error.error) {
          this.errorMessage = this.errorMessage+"\n"+ prop+": "+error.error[prop];
          }
      }
      }
    )
  }
  public getSrc(raw){
    return ('data:image/png;base64,' 
                + raw);
 }

 public onReview():void{
    this.dialog.open(AddReviewComponent,
      {
        width:'60%',
        panelClass:"confirm-dialog-container",
        data:{
          id:this.product.id
        }
       }
      ).afterClosed()
      .subscribe(
        res=>{
          if(res)
            this.initializeProduct();
        }
      )
  }

  public onQuestionnaire():void{
    this.router.navigate(['/day-questionnaire']);
  }

  onLogout():void{
    this.jwtService.logout();
    this.router.navigate(['/login']);
  }

  onLeaderboard():void{
    this.router.navigate(['/leaderboard']);
  }

  ngOnDestroy() {
    if (this.dataSource) { 
      this.dataSource.disconnect(); 
    }
}
}
