import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/services/auth.service';
import { SignupModel } from './signup-model';
import { SignupRequest } from './signup-request';

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

  constructor(private authService:AuthService) { }

  ngOnInit(): void {
    this.serverSuccess=false;
    this.serverError=false;
    this.serverMessage="";
    this.model = new SignupModel("","","","");
  }

  get diagnostic() { return JSON.stringify(this.model); }

  onSubmit():void{
    console.log("submit");
    let request:SignupRequest= new SignupRequest(this.model.username, this.model.email, this.model.password);
    this.authService.addUser(request).subscribe(
      (response:void)=>{
        console.log(response);
        this.serverError=false;
        this.serverSuccess=true;
        this.serverMessage="Signup was successful";
      },
      (error:HttpErrorResponse)=>{
        console.log(error.message);
        this.serverSuccess=false;
        this.serverError=true;
        this.serverMessage=error.message;
      }
    )
  }

}
