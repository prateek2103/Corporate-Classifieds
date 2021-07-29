import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { AuthResponse } from "../model/authResponse";
import { Offer } from "../model/Offer";
import { Employee } from "../model/Employee";
import { messageResponse } from '../model/messageResponse';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  loginserviceUrl = "http://localhost:8080/authapp"
  offerserviceUrl = "http://localhost:8000/offer"
  employeeserviceUrl = "http://localhost:8070/employee"
  pointsserviceUrl = "http://localhost:8090/points"

  constructor(private http: HttpClient) { }

  getUserToken(empDetails: any) {
    return this.http.post<AuthResponse>(this.loginserviceUrl + "/login", empDetails);
  }

  getOffers(token: string, category: string) {
    let options = {
      headers: { "Authorization": "Bearer " + token, "Access-Control-Allow-Origin": "*" }
    }
    return this.http.get<Offer[]>(this.offerserviceUrl + "/getOfferByCategory/" + category, options)
  }

  getOffersByTopLikes(token: string) {
    let options = {
      headers: { "Authorization": "Bearer " + token }
    }
    return this.http.get<Offer[]>(this.offerserviceUrl + "/getOfferByTopLikes", options)
  }

  getOffersByPostedDate(token: string, postedDate: string) {
    let options = {
      headers: { "Authorization": "Bearer " + token }
    }
    return this.http.get<Offer[]>(this.offerserviceUrl + "/getOfferByPostedDate/" + postedDate, options)
  }

  getOfferDetailsById(token: string, id: number) {
    let options = {
      headers: { "Authorization": "Bearer " + token }
    }
    return this.http.get<Offer>(this.offerserviceUrl + "/getOfferDetails/" + id, options)
  }

  saveLike(token: string, id: number) {
    let options = {
      headers: { "Authorization": "Bearer " + token }
    }
    return this.http.post(this.employeeserviceUrl + "/likeOffer/" + id, null, options)
  }

  getProfile(token: String, id: number) {
    let options = {
      headers: { "Authorization": "Bearer " + token }
    }
    return this.http.get<Employee>(this.employeeserviceUrl + "/viewProfile/" + id, options)
  }

  updatePoints(token: String, id: number) {
    let options = {
      headers: { "Authorization": "Bearer " + token }
    }
    return this.http.post<messageResponse>(this.pointsserviceUrl + "/refreshpointsbyemp/" + id, null, options)
  }

  getMyOffers(token: String, id: number) {
    let options = {
      headers: { "Authorization": "Bearer " + token }
    }
    return this.http.get<Offer[]>(this.employeeserviceUrl + "/viewEmployeeOffers/" + id, options)
  }

  updateOffer(token:String,offer:Offer){
    let options = {
      headers: { "Authorization": "Bearer " + token }
    }
    return this.http.post<messageResponse>(this.offerserviceUrl + '/editOffer',offer,options)
  }

  addOffer(token:String,offer:Offer){
    let options = {
      headers: { "Authorization": "Bearer " + token }
    }
    return this.http.post<messageResponse>(this.offerserviceUrl + "/addOffer",offer,options)
  }

  engageOffer(token:String,offerId:number,empId:number){
    let options = {
      headers: { "Authorization": "Bearer " + token },
      params: {"offerId":offerId,"employeeId":empId}
    }
    return this.http.post<messageResponse>(this.offerserviceUrl + "/engageOffer",null,options)
  }

  getRecentlyLiked(token:string){
    let options = {
      headers: { "Authorization": "Bearer " + token },
    }
    return this.http.get<Offer[]>(this.employeeserviceUrl + "/recentlyLiked",options)
  }
}