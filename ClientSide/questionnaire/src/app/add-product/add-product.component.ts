import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { ProductService } from 'src/services/product.service';
import { ProductRequest } from '../models/product-request';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  model:ProductRequest;
  serverSuccess:boolean;
  serverError:boolean;
  serverMessage:string;

  constructor(private productService:ProductService,
    private router: Router,
    public dialogRef: MatDialogRef<AddProductComponent>,
    private jwtService:JwtService) { }

  ngOnInit(): void {
    this.serverSuccess=false;
    this.serverError=false;
    this.serverMessage="";
    this.model = new ProductRequest("",null);
  }

  onSubmit():void{
    const formData = new FormData();
    formData.append("name", this.model.name);
    formData.append("image", this.model.image);
    this.productService.addProduct(formData).subscribe(
      (response:void)=>{
        this.dialogRef.close(true);
      },
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
        }
        else{
          this.serverSuccess=false;
          this.serverError=true;
          this.serverMessage=error.message;
        }
      }
    )
  }

  closeDialog(){
    this.dialogRef.close(false);
  }

  
  onFileSelected(event){
    const file=event.target.files[0];
    let mimeType = event.target.files[0].type;
    if(mimeType.match(/image\/*/)==null){
        this.serverSuccess=false;
          this.serverError=true;
          this.serverMessage="Only images are supported";
    }
    else{
      this.serverSuccess=false;
      this.serverError=false;
      this.serverMessage="";
    }
    console.log(file);
    this.model.image= file;
    console.log(this.model);
  }

}
