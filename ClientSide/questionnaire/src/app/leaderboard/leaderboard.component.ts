import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { QuestionnaireService } from 'src/services/questionnaire.service';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {

  @ViewChild(MatSort) sort:MatSort;
  @ViewChild(MatPaginator) paginator:MatPaginator;

  displayedColumns: string[] = ['username', 'dailyPoints', 'totalPoints'];
  dataSource = new MatTableDataSource([]);
  available:boolean;

  constructor(private questionnaireService:QuestionnaireService,
    private jwtService:JwtService,
    private router: Router) { }

  ngOnInit(): void {
    this.getLeaderboard();
    this.dataSource.sort= this.sort;
    this.dataSource.paginator= this.paginator;
    this.available=false;
  }

  public applyFilter(text:String){
    this.dataSource.filter=text.trim().toLowerCase();
  }

  getLeaderboard():void {
    this.questionnaireService.getLeaderboard().subscribe(
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

  onLogout():void{
    this.jwtService.logout();
    this.router.navigate(['/login']);
  }

  onHome():void{
    this.router.navigate(['/home']);
  }

}
