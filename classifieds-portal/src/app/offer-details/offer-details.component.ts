import { ThisReceiver, ThrowStmt } from '@angular/compiler';
import { ifStmt } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConfigService } from '../config/config.service';
import { Offer } from "../model/Offer";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.css']
})
export class OfferDetailsComponent implements OnInit {
  offer: Offer = new Offer(0, "offer name", "offer description", "category", new Date(), new Date(), new Date(), 0)
  isLiked: boolean = false
  id: number = 0
  token: string | null = ""
  pageError:String = ""
  constructor(private route: ActivatedRoute, private configService: ConfigService) { }

  ngOnInit(): void {
    this.token = localStorage.getItem("token")
    this.id = Number(this.route.snapshot.paramMap.get('id'))

    if (this.token != null && this.id != 0)
      this.configService.getOfferDetailsById(this.token, this.id).subscribe((data: Offer) => {
        this.offer = data
      })
  }

  likedIt() {
    this.isLiked = !this.isLiked
  }

  submitLike() {
    if (this.token != null)
      this.configService.saveLike(this.token, this.id).subscribe((data) => {
        this.pageError="your like is saved successfully"
        this.isLiked=false;
        this.offer.likes+=1
      })
  }
}
