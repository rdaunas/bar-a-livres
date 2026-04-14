import { Component, inject, signal, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { BookService } from '../../core/services/book.service';
import { LivreDTO } from '../../core/models/book.model';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-book-detail',
  standalone: true,
  imports: [DatePipe, MatButtonModule, MatProgressSpinnerModule],
  templateUrl: './book-detail.html',
  styleUrl: './book-detail.css'
})
export class BookDetail implements OnInit {
  private route = inject(ActivatedRoute);
  private bookService = inject(BookService);

  book = signal<LivreDTO | null>(null);
  isLoading = signal(false);

  ngOnInit(): void {
    const isbn = this.route.snapshot.paramMap.get('isbn');
    if (isbn) {
      this.isLoading.set(true);
      this.bookService.findByIsbn(isbn).subscribe({
        next: (b) => { this.book.set(b); this.isLoading.set(false); },
        error: () => { this.isLoading.set(false); }
      });
    }
  }

  // getStars(): boolean[] {
  //   const note = this.book()?.note ?? 0;
  //   return Array.from({ length: 5 }, (_, i) => i < Math.round(note));
  // }

  emprunter(): void {}
  reserver(): void {}
  annulerReservation(): void {}

  canBorrow = signal<boolean>(false);

  checkAvailability(isbn: string) {
    this.bookService.isAvailable(isbn).subscribe(res => {
      this.canBorrow.set(res);
    });
  }
}
