import { Component, Input} from '@angular/core';
import { Reimbursement } from '../shared/reimbursement';

@Component({
  selector: 'app-reimbursement-list',
  templateUrl: './reimbursement-list.component.html',
  styleUrls: ['./reimbursement-list.component.css']
})
export class ReimbursementListComponent {

  @Input() reimbursement: Reimbursement;

}
