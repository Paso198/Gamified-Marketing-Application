import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { Jwt } from '../models/jwt';
import { Role } from '../models/role';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  role:Role;

  constructor(
    private router: Router,
    private jwtService :JwtService) { }


  ngOnInit(): void {
    this.role=this.jwtService.getLoggedAuthorities();
    
  }

}
