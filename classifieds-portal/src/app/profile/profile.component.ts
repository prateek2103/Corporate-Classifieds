import { Component, ComponentFactoryResolver, OnInit } from '@angular/core';
import { ConfigService } from '../config/config.service';
import { Employee } from "../model/Employee"
import { messageResponse } from '../model/messageResponse';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  token: String | null = ""
  id: number = 0
  pageError: string = ""
  employee: Employee = new Employee(0, "", "", "", 0, 0, "", 0)

  constructor(private configService: ConfigService) { }

  ngOnInit(): void {
    this.token = localStorage.getItem('token')
    this.id = Number(localStorage.getItem('userId'))
    if (this.token != null) {
      this.configService.getProfile(this.token, this.id).subscribe((data: Employee) => {
        this.employee = data
        console.log(data)
      }, error => {
        console.log(error)
      })
    }
  }

  updatePoints() {
    if (this.token != null)
      this.configService.updatePoints(this.token, this.id).subscribe((data:messageResponse) => {
        console.log(data.message)
        this.employee.pointsGained = Number(data.message.split(" ")[3])
      }, error => {
        console.log(error)
      })
  }
}
