import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LivreDTO, PageResponse } from '../models/book.model';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/books';
  private readonly apiUrlloans = 'http://localhost:8080/api/loans';

  findAll(page: number = 0, size: number = 20): Observable<PageResponse<LivreDTO>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<LivreDTO>>(this.apiUrl, { params });
  }

  searchBooks(titre: string, page: number, size: number, categorieIds: number[] = [] ) {
    let params: any = {
      titre,
      page,
      size
    };

    if (categorieIds.length > 0) {
      params.categorieIds = categorieIds;
    }

    return this.http.get<any>('http://localhost:8080/api/books/search', { params });
  }

  findByIsbn(isbn: string): Observable<LivreDTO> {
    return this.http.get<LivreDTO>(`${this.apiUrl}/${isbn}`);
  }

  isAvailable(isbn: string) {
    return this.http.get<boolean>(`/api/books/${isbn}/available`);
  }

  emprunter(isbn: string) {
    return this.http.post<LivreDTO>(`${this.apiUrlloans}`, {
      livreIsbn: isbn
    }, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    });
  }
}
