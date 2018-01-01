import { Component, Input } from '@angular/core';

import { Reimb } from '../shared/reimb';

@Component({
  selector: 'app-reimb-item',
  templateUrl: './reimb-item.component.html',
  styleUrls: ['./reimb-item.component.css']
})
export class ReimbItemComponent {
  @Input() reimb: Reimb;
}
