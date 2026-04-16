import { ChangeDetectionStrategy, Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../../core/services/book.service';
import { LivreDTO } from '../../core/models/book.model';
import { MatButtonModule } from '@angular/material/button';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from '@angular/material/card';

@Component({
  selector: 'app-book-search',
  standalone: true,
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatCard,
    MatCardHeader,
    MatCardTitle,
    MatCardSubtitle,
    MatCardContent,
    MatButtonModule
  ],
  templateUrl: './book-search.html',
  styleUrl: './book-search.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BookSearch implements OnInit {

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private bookService = inject(BookService);
  private snackBar = inject(MatSnackBar);

  books = signal<LivreDTO[]>([]);
  isLoading = signal(false);
  totalElements = signal(0);

  value = '';
  categorieIds: number[] = [];

  pageIndex = 0;
  pageSize = 20;

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.value = params['titre'] || '';
      this.pageIndex = +params['page'] || 0;
      this.pageSize = +params['size'] || 20;

      this.categorieIds = params['categorieIds']
        ? [].concat(params['categorieIds']).map((id: any) => +id)
        : [];

      this.loadBooks();
    });
  }

  loadBooks(): void {
    if (!this.value && this.categorieIds.length === 0) {
      this.books.set([]);
      return;
    }

    this.isLoading.set(true);

    this.bookService
      .searchBooks(this.value, this.pageIndex, this.pageSize, this.categorieIds)
      .subscribe({
        next: res => {
          this.books.set(res.content);
          this.totalElements.set(res.totalElements);
          this.isLoading.set(false);
        },
        error: err => {
          if (err.status === 404) {
            this.books.set([]);
            this.totalElements.set(0);
          } else {
            this.snackBar.open('Erreur lors de la recherche', 'OK', { duration: 3000 });
          }
          this.isLoading.set(false);
        }
      });
  }

  onSearch(): void {
    const titre = this.value?.trim();

    if (!titre || titre.length < 2) {
      this.snackBar.open('Veuillez saisir au moins 2 caractères', 'OK', {
        duration: 2000
      });
      return;
    }

    this.router.navigate([], {
      queryParams: {
        titre,
        categorieIds: this.categorieIds.length ? this.categorieIds : null,
        page: 0,
        size: this.pageSize
      },
      queryParamsHandling: 'merge'
    });
  }

  onPageChange(event: PageEvent): void {
    this.router.navigate([], {
      queryParams: {
        titre: this.value,
        categorieIds: this.categorieIds,
        page: event.pageIndex,
        size: event.pageSize
      }
    });
  }

  goToDetail(isbn: string) {
    this.router.navigate(['/catalogue', isbn]);
  }
}
