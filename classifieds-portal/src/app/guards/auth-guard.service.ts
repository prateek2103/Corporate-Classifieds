import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, Route } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenResponse } from '../model/tokenResponse';
import { AuthService } from './auth.service';

@Injectable({
    providedIn: 'root'
})

export class AuthGuard implements CanActivate {
    constructor(private _authService: AuthService, private _router: Router) {
    }

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        let token: string | null = localStorage.getItem("token")

        return new Observable<boolean>(obs => {
            if (token != null)
                this._authService.isAuthenticated(token).subscribe(
                    data => {
                        if (!data.valid) {
                            console.log('fail');
                            this._router.navigateByUrl('/login');
                            obs.next(false);
                        }
                        else {
                            obs.next(true);
                        }
                    }
                );
            else{
                this._router.navigateByUrl("/login")
                obs.next(false);
            }
        });
    }
}