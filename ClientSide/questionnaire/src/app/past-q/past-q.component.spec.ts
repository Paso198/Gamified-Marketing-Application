import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PastQComponent } from './past-q.component';

describe('PastQComponent', () => {
  let component: PastQComponent;
  let fixture: ComponentFixture<PastQComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PastQComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PastQComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
