import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, RouterOutlet } from '@angular/router';
import { ConfigService } from '../config/config.service';
import { AuthResponse } from '../model/authResponse';
import { User } from '../model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  constructor(private configService: ConfigService,private router:Router) { }

  user: User = { username: "", password: "" }
  userForm: FormGroup = new FormGroup({})
  formError = ""
  ngOnInit(): void {
    this.userForm = new FormGroup({
      username: new FormControl(this.user.username, [
        Validators.required,
        Validators.minLength(3)
      ]),
      password: new FormControl(this.user.password, [
        Validators.required,
      ])
    })
  }

  get username() { return this.userForm.get('username') }
  get password() { return this.userForm.get('password') }

  onSubmit() {
    console.log(this.userForm.value)
    let userDetails = { "empUsername": this.userForm.value.username, "empPassword": this.userForm.value.password, "empid": 0 }
    
    //retrive the data from the authmicroservice
    this.configService.getUserToken(userDetails).subscribe((data:AuthResponse)=>{
      localStorage.setItem("token",data["authToken"])
      localStorage.setItem("userId",data['empid'])
      console.log(data['authToken'])
      this.router.navigate(['main'])
    },
    error =>{
      this.formError = "Credentials are incorrect"
      console.log(error)
    });

  }
}
