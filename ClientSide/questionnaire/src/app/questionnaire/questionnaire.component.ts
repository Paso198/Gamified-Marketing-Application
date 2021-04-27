import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router'
import { JwtService } from 'src/services/jwt.service';
import { QuestionnaireService } from 'src/services/questionnaire.service';
import { Answer } from '../models/answer';
import { Response } from '../models/response';
import { Questionnaire } from '../models/questionnaire';

@Component({
  selector: 'app-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.css']
})
export class QuestionnaireComponent implements OnInit {
  questionnaire:Questionnaire;
  answers:Answer[];
  
  constructor(private questionnaireService:QuestionnaireService,
    private jwtService:JwtService,
    private router: Router,) { }

   serverSuccess:boolean;
  serverError:boolean;
  serverMessage:string;

  optionalForm : FormGroup = new FormGroup({
    $key: new FormControl(null),
    age : new FormControl('',Validators.min(18)),
    gender: new FormControl("NOT"),
    expertiseLevel: new FormControl("NOT"),
  })

  ngOnInit(): void {
    this.answers=[];
    this.serverSuccess=false;
    this.serverError=false;
    this.serverMessage="";
    this.questionnaire = new Questionnaire(1,"",null,[]);
    this.getQuestionnaire();
  }

  getQuestionnaire():void{
    this.questionnaireService.getQuestionnaireOfTheDay().subscribe(
      res=>{this.questionnaire=res;
        console.log(res);
        for(let i=0; i<this.questionnaire.questions.length;i++){
          this.answers.push(new Answer(this.questionnaire.questions[i].id, ""));
        }
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

  onCancel(){
    this.questionnaireService.cancelQuestionnaire().subscribe(
      
      res=>{this.questionnaire=res;
        this.router.navigate(['/home']);
      },
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
      }
      this.router.navigate(['/home']);

      })
    
  }

  onSubmit(){
    this.serverSuccess=false;
    this.serverError=false;
    this.serverMessage="";
    if(this.answers.some((a)=> !a.text)){
      this.serverSuccess=false;
      this.serverError=true;
      this.serverMessage="You must complete the marketing section";
    }
    else if(this.optionalForm.value.age && this.optionalForm.value.age<18){
        this.serverSuccess=false;
        this.serverError=true;
        this.serverMessage="You must be 18+";
      }
    else{
      let request:Response = new Response(this.questionnaire.id, 
        this.answers, 
        this.optionalForm.value.age, 
        this.optionalForm.value.gender!="NOT" ?  this.optionalForm.value.gender : null,
        this.optionalForm.value.expertiseLevel!="NOT" ?  this.optionalForm.value.expertiseLevel : null);

        this.questionnaireService.sendQuestionnaire(request).subscribe(
      
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
              this.serverMessage="Questionnaire answered successfully";
              this.router.navigate(['/greetings']);
            }
          },
          error=>{
            if ([401, 403, 400].indexOf(error.status) !== -1) {
              // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
              this.jwtService.logout();
              this.router.navigate(['/login']);
            }
            else{
              this.serverError=true;
              this.serverSuccess=false;
              for(var key in error.message)
              {
                 this.serverMessage= this.serverMessage + error.message[key]+ '\n';
              }
            }
          }
        )

    }
    

  }

}
