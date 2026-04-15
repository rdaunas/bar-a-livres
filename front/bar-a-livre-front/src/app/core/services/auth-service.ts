import { inject, Injectable, signal } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { UserRole } from '../auth/user-role.model';

interface AuthResponse {
  token: string;
  role: UserRole;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private http = inject(HttpClient);

  private readonly _role = signal<UserRole>(this.getStoredRole());
  readonly role = this._role.asReadonly();

  private readonly _isAuthenticated = signal<boolean>(!!this.getStoredToken());
  readonly isAuthenticated = this._isAuthenticated.asReadonly();

  addToken(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
  }

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>('http://localhost:8080/api/v1/auth/signin', {
        email: username,
        password
      })
      .pipe(
        tap(response => {
          localStorage.setItem('token', response.token);
          localStorage.setItem('role', response.role);
          this._role.set(response.role);
          this._isAuthenticated.set(true);
        })
      );
  }

  signUp(
    username: string,
    password: string,
    lastName: string,
    firstName: string
  ): Observable<any> {
    return this.http.post(
      'http://localhost:8080/api/v1/auth/signup',
      {
        email: username,
        password,
        nom: lastName,
        prenom: firstName
      }
    );
  }

  logout(): void {
    localStorage.clear();
    this._role.set('public');
    this._isAuthenticated.set(false);
  }

  hasRole(...roles: UserRole[]): boolean {
    return roles.includes(this._role());
  }

  private getStoredRole(): UserRole {
    return (localStorage.getItem('role') as UserRole) ?? 'public';
  }

  private getStoredToken(): string | null {
    return localStorage.getItem('token');
  }
}
