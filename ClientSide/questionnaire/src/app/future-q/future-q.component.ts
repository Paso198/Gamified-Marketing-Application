import { ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { QuestionnaireService } from 'src/services/questionnaire.service';
import { AdminQuestionnaire } from '../models/admin-questionnaire';
import { Product } from '../models/product';

@Component({
  selector: 'app-future-q',
  templateUrl: './future-q.component.html',
  styleUrls: ['./future-q.component.css']
})
export class FutureQComponent implements OnInit {

  questionnaires:AdminQuestionnaire[];

  constructor(private questionnaireService:QuestionnaireService,
    private jwtService:JwtService,
    private router: Router) { }

  get diagnostic() { return JSON.stringify(this.questionnaires); }

  ngOnInit(): void {
    this.questionnaires=[];
    this.getFutureQuestionnaires();
  }

  getFutureQuestionnaires():void {
    this.questionnaireService.getFutureQuestionnaires().subscribe(
      res=>this.questionnaires=res,
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
      }
      }
    )
  }

}
