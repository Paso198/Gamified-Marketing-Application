import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { QuestionnaireService } from 'src/services/questionnaire.service';
import { AdminQuestionnaire } from '../models/admin-questionnaire';

@Component({
  selector: 'app-administration',
  templateUrl: './administration.component.html',
  styleUrls: ['./administration.component.css'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class AdministrationComponent implements OnInit {



  constructor() { }

  ngOnInit(): void {

  }

 

}
