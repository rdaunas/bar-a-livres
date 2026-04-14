import {inject, Injectable} from '@angular/core';
import {AuthService} from './auth-service';

@Injectable({
  providedIn: 'root',
})
export class BookFetch {

  private auth = inject(AuthService);

  public getAllbooks() {
    return fetch('http://localhost:8080/api/books', {})
  }
  public searchBooks(search: string) {
    return fetch('http://localhost:8080/api/books/search', {})
  }
  public getBook(id: number) {
    return fetch(`http://localhost:8080/api/books/${id}`, {})
  }
  public addBook(titre: string, author: string,description: string,nbExemplaire: number,categories: string[]) {
    return fetch('http://localhost:8080/api/books', {})
  }
  public modifyBook(id: number) {
    return fetch(`http://localhost:8080/api/books/${id}`, {})
  }
  public deleteBook(id: number) {
    return fetch(`http://localhost:8080/api/books/${id}`, {})
  }
}
