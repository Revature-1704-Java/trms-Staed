import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/filter';


import { Reimb } from './reimb';
import { ThisSession } from './session';
import { HttpHeaders } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';

@Injectable()
export class ReimbService {
    private relativeURL = '/api/requests';

    constructor(private sess: ThisSession, private httpClient: HttpClient) { }

    getReimbs(): Reimb[] {
      const reimbs: Reimb[] = [];

      const body = new HttpParams()
        .set('email', this.sess.retrieve('email'))
        .set('valid', this.sess.retrieve('valid'));

      const header = new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded');

      this.httpClient.post<any>('/api/requests', body, { headers: header })
        .toPromise()
        .then(obs => {
          if (obs != null) {
            JSON.parse(obs).forEach(json => {
              const date = json.evtDate.month + '/' + json.evtDate.day + '/' + json.evtDate.year;
              const last = json.lastReviewed.month + '/' + json.lastReviewed.day + '/' + json.lastReviewed.year;
              reimbs.push(new Reimb(json.id, json.employeeEmail, json.state, json.cost, date, last));
            });
          }
        });

      return reimbs;
    }
}
