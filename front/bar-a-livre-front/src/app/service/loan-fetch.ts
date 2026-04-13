import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoanFetch {

  public getUserLoans() {
    return fetch(`http://localhost:8080/api/users/`, {})
  }
  public getAllLoans() {
    return fetch('http://localhost:8080/api/books/loans', {})
  }
  public postLoan(bookId: string) {
    return fetch(`http://localhost:8080/api/books/${bookId}`, {})
  }
  public modifyLoan(loanId: number) {
    return fetch(`http://localhost:8080/api/books/${loanId}`, {})
  }

}
