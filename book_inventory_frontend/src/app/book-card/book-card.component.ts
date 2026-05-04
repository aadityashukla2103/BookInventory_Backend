import { Component, Input, Output, EventEmitter } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-book-card',
  standalone: true,
  imports: [RouterLink, NgIf],
  templateUrl: './book-card.component.html'
})
export class BookCardComponent {
  @Input() book: any;
  @Input() loading = false;

  @Output() add = new EventEmitter<any>();
}
