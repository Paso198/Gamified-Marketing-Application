import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';
import { LoginModel } from './login-model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model:LoginModel;
  serverSuccess:boolean;
  serverError:boolean;
  serverMessage:string;

  constructor(private authService:AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.serverSuccess=false;
    this.serverError=false;
    this.serverMessage="";
    this.model = new LoginModel("","");
  }

  onSubmit():void{
     this.authService.login(this.model).subscribe(
       (response:void)=>{
        this.serverError=false;
        this.serverSuccess=true;
        this.serverMessage="Login was successful";
        this.router.navigate(['/home']);
       },
       (error:HttpErrorResponse)=>{
        this.serverSuccess=false;
        this.serverError=true;
        this.serverMessage="Username or password are incorrect";
       }
     ) 
  }

}
