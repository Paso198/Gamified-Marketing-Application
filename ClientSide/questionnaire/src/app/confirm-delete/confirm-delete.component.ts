import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { JwtService } from 'src/services/jwt.service';
import { QuestionnaireService } from 'src/services/questionnaire.service';

@Component({
  selector: 'app-confirm-delete',
  templateUrl: './confirm-delete.component.html',
  styleUrls: ['./confirm-delete.component.css']
})
export class ConfirmDeleteComponent implements OnInit {
  
  constructor(
    public dialogRef: MatDialogRef<ConfirmDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    private questionnaireService:QuestionnaireService,
    private router: Router,
    private jwtService:JwtService,
  ) { }

  ngOnInit(): void {
  }

  closeDialog(){
    this.dialogRef.close(false);
  }

  deleteQuestionnaire(){

    this.questionnaireService.deleteQuestionnaire(this.data.id).subscribe(
      res=>{
        this.dialogRef.close(true);
      },
      error=>{
        if ([401, 403].indexOf(error.status) !== -1) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          this.jwtService.logout();
          this.router.navigate(['/login']);
        }
      this.dialogRef.close(false);
      }
    )

    
  }

}
