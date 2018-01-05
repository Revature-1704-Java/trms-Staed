import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup } from '@angular/forms/src/model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { ThisSession } from '../shared/session';

@Component({
  selector: 'app-submit',
  templateUrl: './submit.component.html',
  styleUrls: ['./submit.component.css']
})
export class SubmitComponent implements OnInit {
  public submission = { eventType: '', gradingFormat: '', state: '', cost: '', eventDate: '', workMissed: '', lastReview: ''};
  @ViewChild('modalClose') modalClose: ElementRef;
  public counter = Array;

  constructor(private sess: ThisSession, private httpClient: HttpClient) {
  }

  ngOnInit() {
  }

  days(n: number): Array<number> {
    return new Array(n);
  }

  submit(): void {
    const eType: string = this.submission.eventType;
    const format: string = this.submission.gradingFormat;
    const state: string = this.submission.state;
    const cost: string = this.submission.cost;

    let eMonth: string = this.submission.eventDate['month'].toString();
    if (eMonth.length < 2) {
      eMonth = '0' + eMonth;
    }
    let eDay: string = this.submission.eventDate['day'].toString();
    if (eDay.length < 2) {
      eDay = '0' + eDay;
    }
    const eDate: string = this.submission.eventDate['year'] + '-' + eMonth + '-' + eDay;
    console.log(eDate);

    const missed: string = 'P' + this.submission.workMissed + 'D';

    let lMonth: string = this.submission.lastReview['month'].toString();
    if (lMonth.length < 2) {
      lMonth = '0' + lMonth;
    }
    let lDay: string = this.submission.lastReview['day'].toString();
    if (lDay.length < 2) {
      lDay = '0' + lDay;
    }
    const last: string = this.submission.lastReview['year'] + '-' + lMonth + '-' + lDay;
    console.log(last);

    if (this.sess.retrieve('email').length < 1) {
      console.log('Tried to submit reimbursement without logging in.');
      return;
    }


    const body = new HttpParams()
      .set('email', this.sess.retrieve('email'))
      .set('event type', eType)
      .set('grading format', format)
      .set('state', state)
      .set('cost', cost)
      .set('event date', eDate)
      .set('work time missed', missed)
      .set('last reviewed date', last);

    const header = new HttpHeaders()
      .set('Content-Type', 'application/x-www-form-urlencoded');

    this.httpClient.post('/api/submit', body, { headers: header })
      .subscribe(res => {
        this.sess.store('submit', res['success']);
        console.log('Submit: ' + this.sess.retrieve('valid'));
        this.sess.store('submit', null);
        this.modalClose.nativeElement.click();
      }, err => console.log(err));
  }
}
