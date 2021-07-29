import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ConfigService } from '../config/config.service';
import { Offer } from '../model/Offer';

@Component({
  selector: 'app-my-offers',
  templateUrl: './my-offers.component.html',
  styleUrls: ['./my-offers.component.css']
})
export class MyOffersComponent implements OnInit {

  offers:Offer[] = []
  constructor(private configService:ConfigService) { }

  ngOnInit(): void {
    let token = localStorage.getItem("token")
    let id = Number(localStorage.getItem('userId'))
    if(token!=null){
      this.configService.getMyOffers(token,id).subscribe((data:Offer[])=>{
        this.offers = data;
      })
    }
  }

}
