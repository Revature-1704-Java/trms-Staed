import { Component, OnInit } from '@angular/core';
import { Reimbursement } from '../shared/reimbursement';
import { ReimbursementService } from '../shared/reimbursement.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  reimbursements: Reimbursement[] = [];

  constructor(private reimbursementService: ReimbursementService) { }

  ngOnInit() {
    this.reimbursements = this.reimbursementService.getReimbursements();
  }

}
