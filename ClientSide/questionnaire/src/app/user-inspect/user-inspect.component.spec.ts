import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserInspectComponent } from './user-inspect.component';

describe('UserInspectComponent', () => {
  let component: UserInspectComponent;
  let fixture: ComponentFixture<UserInspectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserInspectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserInspectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
