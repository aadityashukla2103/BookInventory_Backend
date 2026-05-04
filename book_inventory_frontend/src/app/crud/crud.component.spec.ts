import { ComponentFixture, TestBed } from '@angular/core/testing';

import { appTestProviders } from '../testing/app-test-providers';
import { CrudPageComponent } from './crud.component';

describe('CrudPageComponent', () => {
  let component: CrudPageComponent;
  let fixture: ComponentFixture<CrudPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrudPageComponent],
      providers: appTestProviders
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CrudPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
