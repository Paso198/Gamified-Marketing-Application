import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Data, Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { ProductService } from 'src/services/product.service';
import { QuestionService } from 'src/services/question.service';
import { QuestionnaireService } from 'src/services/questionnaire.service';
import { Product } from '../models/product';
import { Question } from '../models/question';
import { CdkDragDrop, moveItemInArray } from "@angular/cdk/drag-drop";
import { QuestionnaireRequest } from '../models/questionnaire-request';
import * as moment from 'moment';

@Component({
  selector: 'app-add-questionnaire',
  templateUrl: './add-questionnaire.component.html',
  styleUrls: ['./add-questionnaire.component.css']
})
export class AddQuestionnaireComponent implements OnInit {

  @ViewChild(MatSort) sort:MatSort;
  @ViewChild(MatPaginator) paginator:MatPaginator;
  eDisplayedColumns: string[] = ['id', 'text', 'include'];
  excluded= new MatTableDataSource([]);;

  serverSuccess:boolean;
  serverError:boolean;
  serverMessage:string;
  
  iDisplayedColumns: string[] = ['id', 'text', 'exclude'];
  included=new MatTableDataSource([]);
  
  minDate= new Date(new Date(). getTime() + 24 * 60 * 60 * 1000);

  test = [new Question(10,"blabla")];

  qForm: FormGroup = new FormGroup({
    $key: new FormControl(null),
    title : new FormControl('', Validators.required),
    product: new FormControl(0, Validators.required),
    date: new FormControl(''),
  })
  products:Product[];
  
  

  constructor(
    private questionnaireService:QuestionnaireService,
    private questionService:QuestionService,
    private productService:ProductService,
    private jwtService:JwtService,
    private router: Router) { }

  ngOnInit(): void {
    this.serverSuccess=false;
    this.serverError=false;
    this.serverMessage="";
    this.excluded.sort= this.sort;
    this.excluded.paginator= this.paginator;
    this.products=[];
    this.getProducts();
    this.getQuestions();
  }

  getProducts():void{
    this.productService.getProducts().subscribe(
      res=>{this.products=res;
      },
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
      }
      }
    )
  }
  getQuestions():void {
    this.questionService.getAllQuestions().subscribe(
      res=>{this.excluded=new MatTableDataSource(res);
        this.excluded.sort= this.sort;
        this.excluded.paginator= this.paginator;
        
      },
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
      }
      }
    )
  }

  public applyFilter(text:String){
    this.excluded.filter=text.trim().toLowerCase();
  }

  onExclude(element):void{
    let eProvv = this.excluded.data;
    eProvv.push(element);
    this.excluded.data=eProvv;

    let iProvv:Question[] = this.included.data;
    iProvv = iProvv.filter((i)=>i.id!=element.id);
    this.included.data=iProvv;
  }

  onInclude(element):void{
    let iProvv = this.included.data;
    iProvv.push(element);
    this.included.data=iProvv;

    let eProvv:Question[] = this.excluded.data;
    eProvv = eProvv.filter((i)=>i.id!=element.id);
    this.excluded.data=eProvv;
  }

  onDrop(event: CdkDragDrop<Question[]>) {
    let iProvv = this.included.data;
    moveItemInArray(iProvv, event.previousIndex, event.currentIndex);
    this.included.data=iProvv;
  }

  onSubmit():void{
    this.serverMessage="";
    let request = new QuestionnaireRequest(this.qForm.value.title,
      this.qForm.value.product,
      moment(this.qForm.value.date).format("yyyy-MM-DD"),
      this.included.data.map(i=>i.id));
    console.log(request);
    this.questionnaireService.addQuestionnaire(request).subscribe(
      (res)=>{
        this.serverMessage="";
        if(res){
          this.serverError=true;
          this.serverSuccess=false;
          for(var key in res)
          {
             this.serverMessage= this.serverMessage + res[key]+ '\n';
          }
        }
        else{
          this.serverError=false;
          this.serverSuccess=true;
          this.serverMessage="Questionnaire created successfully";
        }
      },
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
        }
        else{
          this.serverError=true;
          this.serverSuccess=false;
          for(var key in error.message)
          {
             this.serverMessage= this.serverMessage + error.message[key]+ '\n';
          }
        }
      }
    )
  }

  onBack():void{
    this.router.navigate(['/home']);
  }


}
