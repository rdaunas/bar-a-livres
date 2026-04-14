import {inject, Injectable} from '@angular/core';
import {AuthService} from './auth-service';

@Injectable({
  providedIn: 'root',
})
export class ReviewFetch {

  private auth = inject(AuthService);

  public getReviews(bookId: string) {
    return  fetch(`http://localhost:8080/api/books/${bookId}/reviews`, {})
  }
  public modifyReviews(id: number) {
    return fetch(`http://localhost:8080/api/books/${id}`, {})
  }
  public deleteReview(id: number) {
    return fetch(`http://localhost:8080/api/books/${id}`, {})
  }
  public postReviews(bookId: string,note: number,commentaire: string) {
    return fetch(`http://localhost:8080/api/books/${bookId}/reviews`, {})
  }
}
