import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './signup/signup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HomeComponent } from './home/home.component';
import { ProductDayComponent } from './product-day/product-day.component';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { FutureQComponent } from './future-q/future-q.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdministrationComponent } from './administration/administration.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { PastQComponent } from './past-q/past-q.component'
import { MaterialModule } from './material/material.module';
import { ProductsComponent } from './products/products.component';
import { ConfirmDeleteComponent } from './confirm-delete/confirm-delete.component';
import { AddProductComponent } from './add-product/add-product.component';
import { QuestionsComponent } from './questions/questions.component';
import { AddQuestionComponent } from './add-question/add-question.component';
import { AddQuestionnaireComponent } from './add-questionnaire/add-questionnaire.component';
import { DataService } from 'src/services/data.service';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { ModifyQuestionnaireComponent } from './modify-questionnaire/modify-questionnaire.component';
import { AddReviewComponent } from './add-review/add-review.component';
import { QuestionnaireComponent } from './questionnaire/questionnaire.component';
import { GreetingsComponent } from './greetings/greetings.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { InspectComponent } from './inspect/inspect.component';
import { SubmittersComponent } from './submitters/submitters.component';
import { CancellersComponent } from './cancellers/cancellers.component';
import { UserInspectComponent } from './user-inspect/user-inspect.component';



@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    PageNotFoundComponent,
    HomeComponent,
    ProductDayComponent,
    FutureQComponent,
    AdministrationComponent,
    PastQComponent,
    ProductsComponent,
    ConfirmDeleteComponent,
    AddProductComponent,
    QuestionsComponent,
    AddQuestionComponent,
    AddQuestionnaireComponent,
    ModifyQuestionnaireComponent,
    AddReviewComponent,
    QuestionnaireComponent,
    GreetingsComponent,
    LeaderboardComponent,
    InspectComponent,
    SubmittersComponent,
    CancellersComponent,
    UserInspectComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    MDBBootstrapModule.forRoot(),
    MaterialModule,
    ReactiveFormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    [DataService],
    MatDatepickerModule
  ],
  bootstrap: [AppComponent],
  entryComponents:[ConfirmDeleteComponent]
})
export class AppModule { }
