import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { SubmitComponent } from './submit/submit.component';

import { ReimbItemComponent } from './reimb-item/reimb-item.component';

import { ReimbService } from './shared/reimb.service';
import { ThisSession } from './shared/session';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooterComponent,
    NavbarComponent,
    LoginComponent,
    SubmitComponent,
    ReimbItemComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([
      { path: '', component: HomeComponent }
    ]),
    NgbModule.forRoot()
  ],
  providers: [ReimbService, ThisSession],
  bootstrap: [AppComponent]
})
export class AppModule { }
