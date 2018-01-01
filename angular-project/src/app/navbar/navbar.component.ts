import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  public user = { email: '', password: ''};

  constructor() { }

  ngOnInit() {

  }

  login() {
    console.log(`Tried to login: ${this.user.email}, ${this.user.password}`);

  }
}
