import { Component } from '@angular/core';

import { ThisSession } from './shared/session';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private sess: ThisSession) {
    if (!this.sess.exists('email')) {
      this.sess.store('email', '');
      this.sess.store('valid', 'false');
    }
    if (!this.sess.exists('command'))
      this.sess.store('command', '');
  }

  title = 'Tuition Reimbursement Management System';
}
