import {inject, Injectable} from '@angular/core';
import {AuthService} from './auth-service';

@Injectable({
  providedIn: 'root',
})
export class LoanFetch {

  private auth = inject(AuthService);

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
