import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/filter';


import { Reimb } from './reimb';
import { ThisSession } from './session';

@Injectable()
export class ReimbService {
    private _postsURL = 'https://jsonplaceholder.typicode.com/posts';
    private relativeURL = '/api/requests';

    constructor(private sess: ThisSession, private http: Http) { }

    getReimbs(): Observable<Reimb> {
        this.sess.store('command', 'getRequests');

        return this.http
            .get(this.relativeURL)
            .map(res => res.json());
    }
}

/*const reimbs = [
    {
        'id': 1,
        'email': 'dig@gmail.com',
        'description': 'Heheh',
        'cost': 592.6
    },
    {
        'id': 2,
        'email': 'hjkf@gmail.com',
        'description': 'KimPopo',
        'cost': 30457
    }
];*/