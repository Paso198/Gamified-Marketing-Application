import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';

@Component({
  selector: 'app-greetings',
  templateUrl: './greetings.component.html',
  styleUrls: ['./greetings.component.css']
})
export class GreetingsComponent implements OnInit {

  constructor(private router: Router,
    private jwtService:JwtService) { }

  ngOnInit(): void {
  }

  onLogout():void{
    this.jwtService.logout();
    this.router.navigate(['/login']);
  }

  onHome():void{
    this.router.navigate(['/home']);
  }

  onLeaderboard():void{
    this.router.navigate(['/leaderboard']);
  }

}
