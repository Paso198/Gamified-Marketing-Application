import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';


@Component({
  selector: 'app-administration',
  templateUrl: './administration.component.html',
  styleUrls: ['./administration.component.css'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class AdministrationComponent implements OnInit {

  constructor(private jwtService:JwtService,
    private router: Router) { }

  ngOnInit(): void {

  }

  onLogout():void{
    this.jwtService.logout();
    this.router.navigate(['/login']);
  }

 

}
