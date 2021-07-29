import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Offer } from '../model/Offer';
import { Validators } from '@angular/forms';
import { ConfigService } from '../config/config.service';
import { ActivatedRoute } from '@angular/router';
import { messageResponse } from '../model/messageResponse';

@Component({
  selector: 'app-offer-edit',
  templateUrl: './offer-edit.component.html',
  styleUrls: ['./offer-edit.component.css']
})
export class OfferEditComponent implements OnInit {
  offer: Offer = new Offer(0, '', '', '', new Date, new Date, new Date, 0)
  pageError:string = ""
  offerForm: FormGroup = new FormGroup({})
  token:string|null=""
  id:number = 0
  constructor(private configService: ConfigService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    //get the token
    this.token = localStorage.getItem("token")
    
    //get the offer id
    this.id = Number(this.route.snapshot.paramMap.get('id'))
    
    //get the offer details
    if (this.token != null) {
      this.configService.getOfferDetailsById(this.token, this.id).subscribe((data: Offer) => {
        this.offer = data
        console.log(this.offer.name)

        //prefill the form with old details
        this.offerForm = new FormGroup({
          description: new FormControl(this.offer.description, [
            Validators.required,
            Validators.minLength(10)
          ]),
          name: new FormControl(this.offer.name, [
            Validators.required,
            Validators.minLength(3)
          ]),
          category: new FormControl(this.offer.category, [
            Validators.required
          ])
        })
      },error=>{
        console.log(error)
        this.pageError = "There was some error, Please try again later"
      })
    } 
  }

  get name() { return this.offerForm.get('name') }
  get description() { return this.offerForm.get('description') }
  get category(){ return this.offerForm.get('category')}

  //on submit function
  onSubmit(){
    this.offer.name = this.offerForm.value.name
    this.offer.description = this.offerForm.value.description
    this.offer.category = this.offerForm.value.category

    //update the offer details
    if(this.token!=null)
    this.configService.updateOffer(this.token,this.offer).subscribe((data:messageResponse)=>{
      this.pageError = data.message
    },error=>{
      if(error.status = 401){
        this.pageError = "Offer editing is denied"
      }
    })
    
  }

}
