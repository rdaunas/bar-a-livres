import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';
import {BookSearch} from './page/book-search/book-search';

export const routes: Routes = [

  { path: '', redirectTo: 'catalogue', pathMatch: 'full' },

  {
    path: 'connexion',
    loadComponent: () => import('./ui/connexion/connexion').then(m => m.Connexion),
  },
  {
    path: 'inscription',
    loadComponent: () =>
      import('./ui/inscription/inscription').then(m => m.Inscription)
  },
  {
    path: 'catalogue',
    loadComponent: () => import('./page/book-list/book-list').then(m => m.BookList),
  },
  {
    path: 'catalogue/:isbn',
    canActivate: [authGuard],
    loadComponent: () => import('./page/book-detail/book-detail').then(m => m.BookDetail),
  },
  {
    path: 'recherche',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['user', 'librarian', 'admin'] },
    loadComponent: () => import('./page/book-search/book-search').then(m => m.BookSearch),
  },
  {
    path: 'profil',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['user', 'librarian', 'admin'] },
    loadComponent: () => import('./ui/connexion/connexion').then(m => m.Connexion),
    //TODO import('./page/profil/profil')
  },
  {
    path: 'emprunts',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['librarian', 'admin'] },
    loadComponent: () => import('./page/emprunt/emprunt').then(m => m.PageEmprunt),
  },
  {
    path: 'livres/ajouter',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['librarian', 'admin'] },
    loadComponent: () => import('./ui/connexion/connexion').then(m => m.Connexion),
    //TODO import('./page/livre-form/livre-form')
  },
  {
    path: 'statistiques',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['LIBRARIAN', 'ADMIN'] },
    loadComponent: () => import('./ui/connexion/connexion').then(m => m.Connexion),
    //TODO import('./page/statistiques/statistiques')
  },
  {
    path: 'utilisateurs',
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ADMIN'] },
    loadComponent: () => import('./ui/connexion/connexion').then(m => m.Connexion),
    //TODO import('./page/utilisateurs/utilisateurs')
  },
  {
    path: '403',
    loadComponent: () => import('./page/forbidden/forbidden').then(m => m.Forbidden),
  },
  { path: '**', redirectTo: 'catalogue' },
];
