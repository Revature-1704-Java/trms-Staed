import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/filter';


import { Reimb } from './reimb';
import { ThisSession } from './session';

@Injectable()
export class ReimbService {
    private _postsURL = 'https://jsonplaceholder.typicode.com/posts';
    private relativeURL = '/api/requests';

    constructor(private sess: ThisSession, private httpClient: HttpClient) { }

    getReimbs(): Reimb[] {
        const reimbs: Reimb[] = [];

        this.httpClient.get<any>('/api/requests')
            .toPromise()
            .then(obs => {
                JSON.parse(obs).forEach(json => {
                    const date = json.evtDate.month + '/' + json.evtDate.day + '/' + json.evtDate.year;
                    const last = json.lastReviewed.month + '/' + json.lastReviewed.day + '/' + json.lastReviewed.year;
                    reimbs.push(new Reimb(json.id, json.employeeEmail, json.state, json.cost, date, last));
                });
            });

        return reimbs;
    }
}
