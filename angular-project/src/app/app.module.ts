import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ReimbursementDetailComponent } from './reimbursement-detail/reimbursement-detail.component';
import { ReimbursementListComponent } from './reimbursement-list/reimbursement-list.component';
import { ReimbursementService } from './shared/reimbursement.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooterComponent,
    NavbarComponent,
    ReimbursementDetailComponent,
    ReimbursementListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot([
      {path: '', component: HomeComponent},
      {path: 'reimbursements/:reimbursementId', component: ReimbursementListComponent}
    ])
  ],
  providers: [ReimbursementService],
  bootstrap: [AppComponent]
})
export class AppModule { }
