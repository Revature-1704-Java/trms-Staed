import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ReimbService } from '../shared/reimb.service';
import { Reimb } from '../shared/reimb';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  reimbs: Reimb[];

  constructor(private reimbService: ReimbService) { }

  ngOnInit() {
    this.reimbs = this.reimbService.getReimbs();
  }

}
