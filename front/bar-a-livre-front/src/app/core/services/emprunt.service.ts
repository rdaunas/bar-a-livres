import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {EmpruntModel} from '../models/emprunt.model';
import {Observable} from 'rxjs';
import {AuthService} from './auth-service';

@Injectable({
  providedIn: 'root',
})
export class EmpruntService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/loans';
  private readonly auth = inject(AuthService);
  findAll(page: number = 0, size: number = 20): Observable<EmpruntModel[]> {

    return this.http.get<EmpruntModel[]>(this.apiUrl,{headers : this.auth.addToken() });
  }
  retournerLivre(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/return`,{},{headers : this.auth.addToken() });
  }
}
