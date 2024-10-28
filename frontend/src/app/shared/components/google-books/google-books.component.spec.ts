import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GoogleBooksComponent } from './google-books.component';

describe('GoogleBooksComponent', () => {
  let component: GoogleBooksComponent;
  let fixture: ComponentFixture<GoogleBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GoogleBooksComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GoogleBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
