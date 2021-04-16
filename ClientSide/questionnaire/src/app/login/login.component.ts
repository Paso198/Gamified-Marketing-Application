import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';
import { JwtService } from '../../services/jwt.service';
import { LoginModel } from '../models/login-model';

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
    private router: Router,
    private jwtService: JwtService) { }

  ngOnInit(): void {
    this.serverSuccess=false;
    this.serverError=false;
    this.serverMessage="";
    this.model = new LoginModel("","");
  }

  onSubmit():void{
     this.authService.login(this.model).subscribe(
       res=>{
        this.serverError=false;
        this.serverSuccess=true;
        this.jwtService.createSession(res.headers.get("Authorization"))
        this.serverMessage="Login was successful";
        this.router.navigate(['/home']);
       },
       (error:HttpErrorResponse)=>{
        this.serverSuccess=false;
        this.serverError=true;
        this.serverMessage=error.error["exception"];
       }
     ) 
  }

}
