import { Component, OnInit } from '@angular/core';
import { ThisSession } from '../shared/session';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  constructor(private sess: ThisSession) { }

  ngOnInit() {
  }

  logout(): void {
    this.sess.store('valid', 'false');
    this.sess.store('email', '');
    location.reload();
  }

  userLoggedIn(): boolean {
    return this.sess.checkEqual('valid', 'true');
  }
}
