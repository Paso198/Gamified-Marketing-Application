import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddQuestionnaireComponent } from './add-questionnaire/add-questionnaire.component';
import { AuthGuard } from './guards/auth.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { Role } from './models/role';
import { ModifyQuestionnaireComponent } from './modify-questionnaire/modify-questionnaire.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { SignupComponent } from './signup/signup.component';


const routes: Routes = [
  {
    path:'', 
    redirectTo: '/home', 
    pathMatch:'full'
  },
  {
    path:'login', 
    component: LoginComponent
  },
  {
    path:'signup', 
    component: SignupComponent
  },
  {
    path:'home', 
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  {
    path:'add-questionnaire', 
    component: AddQuestionnaireComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.Admin] }
  },
  {
    path:'modify-questionnaire', 
    component: ModifyQuestionnaireComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.Admin] }
  },
  {
    path:'**', 
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
