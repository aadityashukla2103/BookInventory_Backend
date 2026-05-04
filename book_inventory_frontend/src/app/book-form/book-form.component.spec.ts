import { ComponentFixture, TestBed } from '@angular/core/testing';

import { appTestProviders } from '../testing/app-test-providers';
import { BookFormComponent } from './book-form.component';

describe('BookFormComponent', () => {
  let component: BookFormComponent;
  let fixture: ComponentFixture<BookFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookFormComponent],
      providers: appTestProviders
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BookFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
