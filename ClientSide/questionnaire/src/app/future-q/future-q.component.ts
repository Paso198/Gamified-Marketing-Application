import { Component, Input, OnInit } from '@angular/core';
import { AdminQuestionnaire } from '../models/admin-questionnaire';

@Component({
  selector: 'app-future-q',
  templateUrl: './future-q.component.html',
  styleUrls: ['./future-q.component.css']
})
export class FutureQComponent implements OnInit {

  @Input('questionnaires') questionnaires:AdminQuestionnaire[]
  constructor() { }

  ngOnInit(): void {
  }

}
