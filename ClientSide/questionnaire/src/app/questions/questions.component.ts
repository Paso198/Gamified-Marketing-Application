import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { QuestionService } from '../../services/question.service';
import { AddQuestionComponent } from '../add-question/add-question.component';

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css']
})
export class QuestionsComponent implements OnInit {

  @ViewChild(MatSort) sort:MatSort;
  @ViewChild(MatPaginator) paginator:MatPaginator;

  displayedColumns: string[] = ['id', 'text'];
  dataSource = new MatTableDataSource([]);
  available:boolean;

  constructor(private questionService:QuestionService,
    private jwtService:JwtService,
    private router: Router,
    private dialog:MatDialog) { }

    
  public applyFilter(text:String){
    this.dataSource.filter=text.trim().toLowerCase();
  }

  ngOnInit(): void {
    this.getQuestions();
    this.dataSource.sort= this.sort;
    this.dataSource.paginator= this.paginator;
    this.available=false;
  }

  getQuestions():void {
    this.questionService.getAllQuestions().subscribe(
      res=>{this.dataSource=new MatTableDataSource(res);
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

  onAdd(){
    this.dialog.open(AddQuestionComponent,
      {
        width:'40%',
        panelClass:"confirm-dialog-container",
      }).afterClosed()
      .subscribe(
        res=>{
          if(res)
            this.getQuestions();
        }
      )
  }

}
