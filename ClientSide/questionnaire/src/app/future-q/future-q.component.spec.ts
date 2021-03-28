import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FutureQComponent } from './future-q.component';

describe('FutureQComponent', () => {
  let component: FutureQComponent;
  let fixture: ComponentFixture<FutureQComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FutureQComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FutureQComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
