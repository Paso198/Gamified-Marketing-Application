import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CancellersComponent } from './cancellers.component';

describe('CancellersComponent', () => {
  let component: CancellersComponent;
  let fixture: ComponentFixture<CancellersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CancellersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CancellersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
