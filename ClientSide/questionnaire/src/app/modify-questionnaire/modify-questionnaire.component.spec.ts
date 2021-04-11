import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyQuestionnaireComponent } from './modify-questionnaire.component';

describe('ModifyQuestionnaireComponent', () => {
  let component: ModifyQuestionnaireComponent;
  let fixture: ComponentFixture<ModifyQuestionnaireComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModifyQuestionnaireComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifyQuestionnaireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
