import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ConfigService } from '../config/config.service';
import { Offer } from "../model/Offer";

@Component({
  selector: 'app-mainpage',
  templateUrl: './mainpage.component.html',
  styleUrls: ['./mainpage.component.css']
})
export class MainpageComponent implements OnInit {
  @ViewChild("category") category: ElementRef = new ElementRef("");
  @ViewChild("postedDate") postedDate: ElementRef = new ElementRef("");
  pageError: string = ""

  token: string | null = ""
  constructor(private configService: ConfigService) { }
  offers: Offer[] = []
  ngOnInit(): void {
    this.token = localStorage.getItem('token');
    if (this.token != null) {
      this.configService.getOffers(this.token, "electronics").subscribe((data: Offer[]) => {
        console.log(data);
        this.offers = data;
      },
        error => {
          console.log(error);
        });
    }
  }

  onCategoryChange() {
    let category = this.category.nativeElement.value
    if (this.token != null)
      this.configService.getOffers(this.token, category).subscribe((data: Offer[]) => {
        console.log(data);
        this.offers = data;
      },
        error => {
          this.pageError = "We encountered some error please try again later"
          console.log(error);
        });
  }

  filterByTopLikes() {
    if (this.token != null)
      this.configService.getOffersByTopLikes(this.token).subscribe((data: Offer[]) => {
        console.log(data);
        this.offers = data;
      },
        error => {
          this.pageError = "We encountered some error please try again later"
          console.log(error);
        });
  }

  filterByPostedDate() {
    let postedDate = this.postedDate.nativeElement.value
    if (this.token != null)
      this.configService.getOffersByPostedDate(this.token, postedDate).subscribe((data: Offer[]) => {
        console.log(data);
        this.offers = data;
      },
        error => {
          console.log(error)
          if (error.status == 400)
            this.pageError = "please enter a valid date"

          if (error.status == 404)
            this.pageError = "no offers found"
          console.log(error);
        });
  }

  showRecentlyLiked() {
    if (this.token != null)
      this.configService.getRecentlyLiked(this.token).subscribe((data: Offer[]) => {
        this.offers = data
      }, error => {
        console.log(error)
      })
  }
}
