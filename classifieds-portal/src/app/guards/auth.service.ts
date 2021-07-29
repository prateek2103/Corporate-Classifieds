import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { TokenResponse } from "../model/tokenResponse";

@Injectable({
    providedIn:'root'
})

export class AuthService{
    authserviceurl = "http://localhost:8080/authapp"
    constructor(private http:HttpClient){}

    isAuthenticated(token:string){
        let options={
            headers:{"Authorization":"Bearer "+token}
        }
        return this.http.get<TokenResponse>(this.authserviceurl + "/validate",options);
    }

    logout(){
        localStorage.clear()
    }
}