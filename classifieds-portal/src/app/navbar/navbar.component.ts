import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../guards/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  
  local:any
  constructor(private authService:AuthService,private route:Router) { }
  ngOnInit(): void {
    this.local = localStorage
  }

  logout(){
    this.authService.logout();
    this.route.navigate(['/login']);
  }
}
