import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '',           redirectTo: 'connexion', pathMatch: 'full' },
  {
    path: 'connexion',
    loadComponent: () =>
      import('./ui/connexion/connexion').then(m => m.ConnexionComponent)
  },
  {
    path: 'inscription',
    loadComponent: () =>
      import('./ui/connexion/connexion').then(m => m.ConnexionComponent)
  },
  {
    path: 'catalogue',
    loadComponent: () =>
      import('./page/book-list/book-list').then(m => m.BookList)
  },
  {
    path: 'catalogue/:isbn',
    loadComponent: () =>
      import('./page/book-detail/book-detail').then(m => m.BookDetail)
  },
  {
    path: 'profil',
    loadComponent: () =>
      import('./ui/connexion/connexion').then(m => m.ConnexionComponent)
    // TODO Remplacer par : ProfilComponent
  },
  {
    path: 'emprunts',
    loadComponent: () =>
      import('./page/emprunt/emprunt').then(m => m.PageEmprunt)
  },
  {
    path: 'livres/ajouter',
    loadComponent: () =>
      import('./ui/connexion/connexion').then(m => m.ConnexionComponent)
    // TODO Remplacer par : AjouterLivreComponent
  },
  {
    path: 'statistiques',
    loadComponent: () =>
      import('./ui/connexion/connexion').then(m => m.ConnexionComponent)
    // TODO Remplacer par : StatistiquesComponent
  },
  {
    path: 'utilisateurs',
    loadComponent: () =>
      import('./ui/connexion/connexion').then(m => m.ConnexionComponent)
    // TODO Remplacer par : UtilisateursComponent
  },
  { path: '**', redirectTo: 'connexion' }
];
