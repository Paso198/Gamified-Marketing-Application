import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from 'src/services/data.service';
import { JwtService } from 'src/services/jwt.service';
import { QuestionnaireService } from 'src/services/questionnaire.service';
import { AdminResponse } from '../models/admin-response';

@Component({
  selector: 'app-user-inspect',
  templateUrl: './user-inspect.component.html',
  styleUrls: ['./user-inspect.component.css']
})
export class UserInspectComponent implements OnInit {

  constructor(public data:DataService,
    private jwtService:JwtService,
    private router: Router,
    private questionnaireService: QuestionnaireService) { }

    available:Boolean;
    errorMessage:String;
    response:AdminResponse;

  ngOnInit(): void {
    this.errorMessage="";
    this.available=false;
    this.response = new AdminResponse(0,[],0,"","");
    this.getResponse();
  }

  getResponse():void{
    this.questionnaireService.getResponse(this.data.getQuestionnaireData(), this.data.getUserData()).subscribe(
      res=>{
        this.available=true;
        this.response=res;
       },
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.data.clean();
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

  onLogout():void{
    this.jwtService.logout();
    this.data.clean();
    this.router.navigate(['/login']);
  }

  onBack():void{
    this.data.clean();
    this.router.navigate(['/inspect']);
  }

}
