import {Component, inject, signal, OnInit, Output, EventEmitter} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DatePipe, NgIf} from '@angular/common';
import { BookService } from '../../core/services/book.service';
import { LivreDTO } from '../../core/models/book.model';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-book-detail',
  standalone: true,
  imports: [DatePipe, MatButtonModule, MatProgressSpinnerModule, NgIf],
  templateUrl: './book-detail.html',
  styleUrl: './book-detail.css'
})
export class BookDetail implements OnInit {
  private route = inject(ActivatedRoute);
  private bookService = inject(BookService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);

  @Output() valider = new EventEmitter <[number, string]>();

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

  emprunter(): void {}
  // getStars(): boolean[] {
  //   const note = this.book()?.note ?? 0;
  //   return Array.from({ length: 5 }, (_, i) => i < Math.round(note));
  // }

  emprunter(isbn: string) {
    this.effectuerEmprunt(isbn);
  }

  effectuerEmprunt(isbn: string): void {
    this.bookService.emprunter(isbn).subscribe({
      next: ()=>{
        this.snackBar.open('Livre emprunté avec succès', 'OK', {
          duration: 3000,
          panelClass: ['snackbar-success']
        });
        this.checkAvailability(isbn);
        this.router.navigate(['/catalogue']);
      },
      error: () => {
        this.snackBar.open('Impossible d’emprunter ce livre', 'Fermer', {
          duration: 4000,
          panelClass: ['snackbar-error']
        });
      }
    })

  }
  reserver(): void {}
  annulerReservation(): void {}

  canBorrow = signal<boolean>(false);

  checkAvailability(isbn: string) {
    this.bookService.isAvailable(isbn).subscribe(res => {
      this.canBorrow.set(res);
    });
  }

}
