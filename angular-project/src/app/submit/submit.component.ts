import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms/src/model';

import { ThisSession } from '../shared/session';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-submit',
  templateUrl: './submit.component.html',
  styleUrls: ['./submit.component.css']
})
export class SubmitComponent implements OnInit {
  public submission = { eventType: '', gradingFormat: '', state: '', cost: '', eventDate: '', workMissed: '', lastReview: ''};

  constructor(private sess: ThisSession, private httpClient: HttpClient) { }

  ngOnInit() {
  }
}
