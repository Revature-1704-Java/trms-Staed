import { Component, OnInit } from '@angular/core';
import { Reimbursement } from '../shared/reimbursement';
import { ReimbursementService } from '../shared/reimbursement.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reimbursement-detail',
  templateUrl: './reimbursement-detail.component.html',
  styleUrls: ['./reimbursement-detail.component.css']
})
export class ReimbursementDetailComponent implements OnInit {

  reimbursement: Reimbursement;

  constructor(private reimbursementService: ReimbursementService, private route: ActivatedRoute) { }

  ngOnInit() {
    let reimbId: number = parseInt(this.route.snapshot.params['reimbursementId']);
    this.reimbursement = this.reimbursementService.getReimbursementById(reimbId);
  }

}
