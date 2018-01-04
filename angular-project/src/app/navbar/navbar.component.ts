import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms/src/model';

import { ThisSession } from '../shared/session';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  public user = { email: '', password: ''};
  public myform: FormGroup = this.myform;

  constructor(private sess: ThisSession, private http: HttpClient) { }

  ngOnInit() {
  }

  login(): void {
    const uEmail: string = this.user.email;
    const uPass: string = this.user.password;
    console.log(`Tried to login: ${uEmail}, ${uPass}`);

    this.http.post('TRMS/login', {
      email: uEmail,
      password: uPass
    }).subscribe(res => {
      this.sess.store('valid', res['valid']);
      this.sess.store('email', uEmail);
      console.log(res);
    }, err => console.log(err));
  }

  logout(): void {
    this.sess.clear();
  }

  userLoggedIn(): boolean {
    return this.sess.checkEqual('valid', 'true');
  }
}
