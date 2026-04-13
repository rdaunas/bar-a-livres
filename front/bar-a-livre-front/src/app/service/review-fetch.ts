import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ReviewFetch {

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
