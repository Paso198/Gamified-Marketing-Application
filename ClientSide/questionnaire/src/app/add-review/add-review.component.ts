import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { ReviewService } from 'src/services/review.service';
import { ReviewRequest } from '../models/review-request';

@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.css']
})
export class AddReviewComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AddReviewComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    private reviewService:ReviewService,
    private router: Router,
    private jwtService:JwtService,
  ) { }

    model:ReviewRequest;
    serverSuccess:boolean;
    serverError:boolean;
    serverMessage:string;
  ngOnInit(): void {
    this.model=new ReviewRequest(this.data.id, "");
  }

  closeDialog(){
    this.dialogRef.close(false);
  }

  onSubmit(){

    this.reviewService.addReview(this.model).subscribe(
      res=>{
        this.serverMessage="";
        if(res){
          this.serverError=true;
          this.serverSuccess=false;
          for(var key in res)
          {
             this.serverMessage= this.serverMessage + res[key]+ '\n';
          }
        }
        else{
          this.serverError=false;
          this.serverSuccess=true;
          this.dialogRef.close(true);
        }
      },
      error=>{
        this.serverMessage="";
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
        }
        this.serverError=true;
        this.serverSuccess=false;
        for(var key in error)
          {
             this.serverMessage= this.serverMessage + error[key]+ '\n';
          }
      }
    )

    
  }

}
