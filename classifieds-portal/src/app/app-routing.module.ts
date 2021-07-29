import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddOfferComponent } from './add-offer/add-offer.component';
import { AuthGuard } from './guards/auth-guard.service';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { MainpageComponent } from './mainpage/mainpage.component';
import { MyOffersComponent } from './my-offers/my-offers.component';
import { OfferDetailsComponent } from './offer-details/offer-details.component';
import { OfferEditComponent } from './offer-edit/offer-edit.component';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'homepage', component: HomepageComponent },
  { path: 'main', component: MainpageComponent, canActivate:[AuthGuard] },
  { path: 'offerDetails/:id', component: OfferDetailsComponent,canActivate:[AuthGuard] },
  { path: 'profile', component: ProfileComponent,canActivate:[AuthGuard] },
  { path: 'myOffers', component: MyOffersComponent ,canActivate:[AuthGuard]},
  { path: 'editOffer/:id', component: OfferEditComponent ,canActivate:[AuthGuard]},
  { path: 'addOffer', component: AddOfferComponent,canActivate:[AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
