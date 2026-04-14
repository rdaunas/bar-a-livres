import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LivreDTO, PageResponse } from '../models/book.model';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/books';

  findAll(page: number = 0, size: number = 20): Observable<PageResponse<LivreDTO>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<LivreDTO>>(this.apiUrl, { params });
  }
}
