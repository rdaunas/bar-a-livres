import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import {
  MatCard,
  MatCardContent,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { BookService } from '../../core/services/book.service';
import { LivreDTO } from '../../core/models/book.model';
import {Router} from '@angular/router';
import {MatFormField, MatInputModule, MatLabel} from '@angular/material/input';
import {FormsModule} from '@angular/forms';
import {MatIcon, MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [
    CommonModule,
    MatCard,
    MatCardHeader,
    MatCardTitle,
    MatCardSubtitle,
    MatCardContent,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    MatSnackBarModule,
    MatInputModule,
    MatFormField,
    MatLabel,
    FormsModule,
    MatIconModule
  ],
  templateUrl: './book-list.html',
  styleUrl: './book-list.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BookList implements OnInit {

  private readonly bookService = inject(BookService);
  private readonly snackBar = inject(MatSnackBar);

  readonly books = signal<LivreDTO[]>([]);
  readonly isLoading = signal(false);
  readonly totalElements = signal(0);
  readonly availabilityMap = signal<Record<string, boolean>>({});

  pageIndex = 0;
  pageSize = 20;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.loadLivres();
  }

  loadLivres(): void {
    this.isLoading.set(true);

    this.bookService.findAll(this.pageIndex, this.pageSize).subscribe({
      next: (page) => {
        this.books.set(page.content);
        this.totalElements.set(page.totalElements);
        this.isLoading.set(false);

        page.content.forEach(book => {
          this.checkAvailability(book.isbn);
        });
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement des livres.', 'Fermer', { duration: 3000 });
        this.isLoading.set(false);
      }
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadLivres();
  }

  goToDetail(isbn: string) {
    this.router.navigate(['/catalogue', isbn]);
  }

  checkAvailability(isbn: string) {
    this.bookService.isAvailable(isbn).subscribe(res => {
      this.availabilityMap.update(map => ({
        ...map,
        [isbn]: res
      }));

      console.log(isbn, 'available:', res);
    });
  }

  canBorrow(isbn: string): boolean {
    return this.availabilityMap()[isbn] ?? false;
  }

  value = '';
  categorieIds: number[] = [];

  onSearch(): void {
    const titre = this.value?.trim();

    if (!titre || titre.length < 2) {
      this.snackBar.open('Veuillez saisir au moins 2 caractères', 'OK', {
        duration: 2000
      });
      return;
    }

    this.router.navigate(['/recherche'], {
      queryParams: {
        titre: this.value,
        categorieIds: this.categorieIds.length ? this.categorieIds : null,

        page: 0,
        size: this.pageSize
      },
      queryParamsHandling: 'merge'
    });
  }

}
