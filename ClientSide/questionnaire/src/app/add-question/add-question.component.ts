import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { QuestionService } from 'src/services/question.service';
import { AddQuestion } from '../models/add-question';

@Component({
  selector: 'app-add-question',
  templateUrl: './add-question.component.html',
  styleUrls: ['./add-question.component.css']
})
export class AddQuestionComponent implements OnInit {

  model:AddQuestion;
  serverSuccess:boolean;
  serverError:boolean;
  serverMessage:string;

  constructor(
    public dialogRef: MatDialogRef<AddQuestionComponent>,
    private questionService:QuestionService,
    private router: Router,
    private jwtService:JwtService,) { }

  ngOnInit(): void {
    this.serverSuccess=false;
    this.serverError=false;
    this.serverMessage="";
    this.model = new AddQuestion("");
  }

  closeDialog(){
    this.dialogRef.close(false);
  }

  onSubmit(){
    this.questionService.addQuestion(this.model).subscribe(
      (res)=>{
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
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
        }
      this.dialogRef.close(false);
      }
    )

    
  }

}
