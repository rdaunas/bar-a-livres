import { Injectable, signal, computed } from '@angular/core';

export type Role = 'user' | 'adherent' | 'bibliothecaire' | 'admin';

export interface CurrentUser {
  pseudo: string;
  role: Role;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Signal contenant l'utilisateur connecté (null = non connecté)
  private _currentUser = signal<CurrentUser | null>(null);

  // Accesseurs publics
  readonly currentUser = this._currentUser.asReadonly();
  readonly isLoggedIn = computed(() => this._currentUser() !== null);
  readonly role = computed(() => this._currentUser()?.role ?? 'user');
  readonly pseudo = computed(() => this._currentUser()?.pseudo ?? '');

  login(user: CurrentUser): void {
    this._currentUser.set(user);
  }

  logout(): void {
    this._currentUser.set(null);
  }

  hasRole(role: Role): boolean {
    return this._currentUser()?.role === role;
  }
}
