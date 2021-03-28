import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';
import { SignupModel } from '../models/signup-model';
import { SignupRequest } from '../models/signup-request';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  model:SignupModel;
  serverSuccess:boolean;
  serverError:boolean;
  serverMessage:string;

  constructor(private authService:AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.serverSuccess=false;
    this.serverError=false;
    this.serverMessage="";
    this.model = new SignupModel("","","","");
  }

  get diagnostic() { return JSON.stringify(this.model); }

  onSubmit():void{
    let request:SignupRequest= new SignupRequest(this.model.username, this.model.email, this.model.password);
    this.authService.addUser(request).subscribe(
      (response:void)=>{
        this.serverError=false;
        this.serverSuccess=true;
        this.serverMessage="Signup was successful";
        this.router.navigate(['/login']);
      },
      (error:HttpErrorResponse)=>{
        this.serverSuccess=false;
        this.serverError=true;
        this.serverMessage=error.message;
      }
    )
  }

}
