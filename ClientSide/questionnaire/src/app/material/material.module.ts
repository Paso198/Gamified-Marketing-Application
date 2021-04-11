import { NgModule } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatCardModule} from '@angular/material/card';
import {MatTableModule} from '@angular/material/table';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSortModule} from '@angular/material/sort';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatDialogModule} from '@angular/material/dialog';
import {MatSelectModule} from '@angular/material/select';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {DragDropModule} from '@angular/cdk/drag-drop';






const MaterialComponwnts = [
MatIconModule,
MatButtonModule,
MatExpansionModule,
MatCardModule,
MatTableModule,
MatFormFieldModule,
MatInputModule,
MatSortModule,
MatPaginatorModule,
MatDialogModule,
MatSelectModule,
MatDatepickerModule,
MatNativeDateModule,
DragDropModule
];


@NgModule({
  declarations: [],
  imports: [
    MaterialComponwnts
  ],
  exports: [
    MaterialComponwnts
  ]
})
export class MaterialModule { }
