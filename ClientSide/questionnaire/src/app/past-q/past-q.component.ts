import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { DataService } from 'src/services/data.service';
import { JwtService } from 'src/services/jwt.service';
import { QuestionnaireService } from 'src/services/questionnaire.service';
import { ConfirmDeleteComponent } from '../confirm-delete/confirm-delete.component';
import { AdminQuestionnaire } from '../models/admin-questionnaire';

@Component({
  selector: 'app-past-q',
  templateUrl: './past-q.component.html',
  styleUrls: ['./past-q.component.css']
})
export class PastQComponent implements OnInit {

  @ViewChild(MatSort) sort:MatSort;
  @ViewChild(MatPaginator) paginator:MatPaginator;

  displayedColumns: string[] = ['id', 'title', 'date', 'product', 'inspect', 'delete'];
  dataSource = new MatTableDataSource([]);
  available:boolean;

  constructor(private data:DataService,
    private questionnaireService:QuestionnaireService,
    private jwtService:JwtService,
    private router: Router,
    private dialog:MatDialog) { }

  ngOnInit(): void {
    this.getPastQuestionnaires();
    this.dataSource.sort= this.sort;
    this.dataSource.paginator= this.paginator;
    this.available=false;
  }

  public applyFilter(text:String){
    this.dataSource.filter=text.trim().toLowerCase();
  }

  getPastQuestionnaires():void {
    this.questionnaireService.getPastQuestionnaires().subscribe(
      res=>{this.dataSource=new MatTableDataSource(res);
        this.dataSource.filterPredicate = function(data, filter: string): boolean {
          return data.title.toLowerCase().includes(filter) 
          || data.id.toString().toLowerCase().includes(filter) 
          || data.product.name.toLowerCase().includes(filter) 
          || data.date.toString().toLowerCase().includes(filter) 
        };
        this.dataSource.sort= this.sort;
        this.dataSource.paginator= this.paginator;
        if(res.length>0)
           this.available=true;
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

  onInspect(questionnaire:AdminQuestionnaire){
    this.data.setQuestionnaireData(questionnaire);
    this.router.navigate(['/inspect']);
  }

  onDelete(questionnaire:AdminQuestionnaire){
    this.dialog.open(ConfirmDeleteComponent,
      {
        width:'40%',
        panelClass:"confirm-dialog-container",
        data:{
          id:questionnaire.id
        }
      }).afterClosed()
      .subscribe(
        res=>{
          if(res)
            this.getPastQuestionnaires();
        }
      )
  }

}
