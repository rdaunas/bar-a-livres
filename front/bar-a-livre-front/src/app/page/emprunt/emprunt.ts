import {Component, inject, OnInit, signal} from '@angular/core';
import {TableauEmprunts} from '../../ui/tableau.emprunts/tableau.emprunts';
import {EmpruntService} from '../../core/services/emprunt.service';
import {EmpruntModel} from '../../core/models/emprunt.model';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';


@Component({
  selector: 'app-page.emprunt',
  imports: [TableauEmprunts],
  templateUrl: './emprunt.html',
  styleUrl: './emprunt.css',
})
export class PageEmprunt implements OnInit{
  private readonly emprunts = inject (EmpruntService);
  private readonly snackBar = inject(MatSnackBar);
  private router = inject(Router);
  readonly Emprunts = signal(<EmpruntModel[]>([]))
  readonly isLoading = signal(false);
  readonly totalElements = signal(0);

  pageIndex = 0;
  pageSize = 20;

  ngOnInit(): void {
    this.loadEmprunts()
  }
  retournerLivre(id: number): void {
    this.emprunts.retournerLivre(id).subscribe({
      next: () => {
        this.snackBar.open('Livre retourné avec succès', 'OK', {
          duration: 3000,
          panelClass: ['snackbar-success']
        });
        this.loadEmprunts();
      }
    })
  }

  loadEmprunts(): void {
    this.isLoading.set(true);

    this.emprunts.findAll(this.pageIndex, this.pageSize).subscribe({
      next: (page) => {
        const data = page.map(e => ({
          id: e.id,
          userId: e.userId,
          livreIsbn: e.livreIsbn,
          typeStatus: e.typeStatus,
          dateDemande: e.dateDemande,
          dateEmprunt: e.dateEmprunt,
          dateRetourPrevisionnel: e.dateRetourPrevisionnel
        }));

        this.Emprunts.set(data);
        this.totalElements.set(page.length);
        this.isLoading.set(false);
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement des emprunts.', 'Fermer', { duration: 3000 });
        this.isLoading.set(false);
      }
    });
  }
}
