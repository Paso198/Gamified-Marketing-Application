import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MdbTableDirective } from 'angular-bootstrap-md';
import { JwtService } from 'src/services/jwt.service';
import { QuestionnaireService } from 'src/services/questionnaire.service';
import { AdminQuestionnaire } from '../models/admin-questionnaire';

@Component({
  selector: 'app-past-q',
  templateUrl: './past-q.component.html',
  styleUrls: ['./past-q.component.css']
})
export class PastQComponent implements OnInit {
  @ViewChild(MdbTableDirective, {static: false}) mdbTable:MdbTableDirective;
  questionnaires:AdminQuestionnaire[];
  searchText: string = '';
  previous: string;
  available:boolean;

  constructor(private questionnaireService:QuestionnaireService,
    private jwtService:JwtService,
    private router: Router) { }

  get diagnostic() { return JSON.stringify(this.questionnaires); }

  ngOnInit(): void {
    this.questionnaires=[];
    this.available=false;
    this.getPastQuestionnaires();
  }

  getPastQuestionnaires():void {
    this.questionnaireService.getPastQuestionnaires().subscribe(
      res=>{this.questionnaires=res;
        if(this.questionnaires.length>0)
          this.available=true;
        this.mdbTable.setDataSource(this.questionnaires);
        this.previous = this.mdbTable.getDataSource();},
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
      }
      }
    )
  }

  @HostListener('input') oninput() {
    this.searchItems();
}

  searchItems() {
    const prev = this.mdbTable.getDataSource();
    if (!this.searchText) {
        this.mdbTable.setDataSource(this.previous);
        this.questionnaires = this.mdbTable.getDataSource();
    }
    if (this.searchText) {
        this.questionnaires = this.mdbTable.searchLocalDataByMultipleFields(this.searchText, ['title','date']);
        this.mdbTable.setDataSource(prev);
    }
}

}
