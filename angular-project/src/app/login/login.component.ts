import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup } from '@angular/forms/src/model';

import { ThisSession } from '../shared/session';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public user = { email: '', password: ''};
  public myform: FormGroup = this.myform;
  @ViewChild('loginClose') loginClose: ElementRef;

  constructor(private sess: ThisSession, private httpClient: HttpClient) { }

  ngOnInit() {
  }

  login(): void {
    const uEmail: string = this.user.email;
    const uPass: string = this.user.password;
    console.log(`Tried to login: ${uEmail}, ${uPass}`);

    const body = new HttpParams()
      .set('email', uEmail)
      .set('password', uPass);

    const header = new HttpHeaders()
      .set('Content-Type', 'application/x-www-form-urlencoded');

    this.httpClient.post('/api/login', body, { headers: header })
      .subscribe(res => {
         this.sess.store('valid', res['success']);
         this.sess.store('email', uEmail);
        console.log('Valid: ' + this.sess.retrieve('valid'));
        this.loginClose.nativeElement.click();
      }, err => console.log(err));

    location.reload();
  }
}
