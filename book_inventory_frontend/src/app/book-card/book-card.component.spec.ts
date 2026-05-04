import { ComponentFixture, TestBed } from '@angular/core/testing';

import { appTestProviders } from '../testing/app-test-providers';
import { BookCardComponent } from './book-card.component';

describe('BookCardComponent', () => {
  let component: BookCardComponent;
  let fixture: ComponentFixture<BookCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookCardComponent],
      providers: appTestProviders
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BookCardComponent);
    component = fixture.componentInstance;
    component.book = {
      isbn: '9780000000000',
      title: 'Sample Book',
      authorNames: [],
      categoryName: 'Reference',
      publisherName: 'Demo Press',
      description: 'Test description',
      availableCopies: 1,
      priceLabel: '$10.00'
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
