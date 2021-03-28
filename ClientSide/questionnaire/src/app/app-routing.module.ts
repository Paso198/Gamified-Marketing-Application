import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { SignupComponent } from './signup/signup.component';


const routes: Routes = [
  {
    path:'', 
    redirectTo: '/login', 
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
    path:'**', 
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
