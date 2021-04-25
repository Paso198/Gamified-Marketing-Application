import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { DataService } from 'src/services/data.service';
import { JwtService } from 'src/services/jwt.service';
import { QuestionnaireService } from 'src/services/questionnaire.service';
import { UserResponse } from '../models/user-response';
import { UserInspectComponent } from '../user-inspect/user-inspect.component';

@Component({
  selector: 'app-submitters',
  templateUrl: './submitters.component.html',
  styleUrls: ['./submitters.component.css']
})
export class SubmittersComponent implements OnInit {

  @Input() id;
  @ViewChild(MatSort) sort:MatSort;
  @ViewChild(MatPaginator) paginator:MatPaginator;

  displayedColumns: string[] = ['id', 'username', 'email', 'see'];
  dataSource = new MatTableDataSource([]);
  available:boolean;

  constructor(private questionnaireService:QuestionnaireService,
    private jwtService:JwtService,
    private router: Router,
    private data:DataService) { }

  ngOnInit(): void {
    this.getSubmitters();
    this.dataSource.sort= this.sort;
    this.dataSource.paginator= this.paginator;
    this.available=false;
  }

  public applyFilter(text:String){
    this.dataSource.filter=text.trim().toLowerCase();
  }

  getSubmitters():void {
    this.questionnaireService.getSubmitters(this.id).subscribe(
      res=>{
        this.dataSource=new MatTableDataSource(res);
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
  onAnswers(element){
    let u:UserResponse = new UserResponse(element.id, element.username, element.email);
    this.data.setUserData(u);
    this.router.navigate(['/user-inspect']);
  }


}
