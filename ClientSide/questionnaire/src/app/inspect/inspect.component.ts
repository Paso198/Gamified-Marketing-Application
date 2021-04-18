import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from 'src/services/data.service';
import { JwtService } from 'src/services/jwt.service';

@Component({
  selector: 'app-inspect',
  templateUrl: './inspect.component.html',
  styleUrls: ['./inspect.component.css']
})
export class InspectComponent implements OnInit {

  constructor(public data:DataService,
    private jwtService:JwtService,
    private router: Router) { }
  


  ngOnInit(): void {
   
  }

  onLogout():void{
    this.jwtService.logout();
    this.router.navigate(['/login']);
  }

  onHome():void{
    this.router.navigate(['/home']);
  }

}
