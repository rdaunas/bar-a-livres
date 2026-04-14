import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {EmpruntModel} from '../models/emprunt.model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EmpruntService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/loans';

  findAll(page: number = 0, size: number = 20): Observable<EmpruntModel[]> {

    return this.http.get<EmpruntModel[]>(this.apiUrl);
  }
}
