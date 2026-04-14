import { Component } from '@angular/core';
import {MatTableModule} from '@angular/material/table';
import {EmpruntModel} from '../../core/models/emprunt.model';


const ELEMENT_DATA: EmpruntModel[] = [
  {
    userId: 1002n,
    isbn: '978-2746098465',
    status: 'EMPRUNTE',
    dateDemande: new Date('2026-02-25'),
    dateEmprunt: new Date('2026-02-26'),
    dateRetourPrevisionnel: new Date('2026-03-26')
  },
  {
    userId: 1003n,
    isbn: '978-2212671076',
    status: 'RETARD',
    dateDemande: new Date('2026-01-10'),
    dateEmprunt: new Date('2026-01-11'),
    dateRetourPrevisionnel: new Date('2026-02-11')
  },
  {
    userId: 1004n,
    isbn: '978-2266292014',
    status: 'RETOURNE',
    dateDemande: new Date('2026-02-01'),
    dateEmprunt: new Date('2026-02-02'),
    dateRetourPrevisionnel: new Date('2026-03-02')
  },
  {
    userId: 1005n,
    isbn: '978-2092589545',
    status: 'EMPRUNTE',
    dateDemande: new Date('2026-03-10'),
    dateEmprunt: new Date('2026-03-11'),
    dateRetourPrevisionnel: new Date('2026-04-11')
  },
  {
    userId: 1007n,
    isbn: '978-2408015440',
    status: 'EMPRUNTE',
    dateDemande: new Date('2026-02-15'),
    dateEmprunt: new Date('2026-02-16'),
    dateRetourPrevisionnel: new Date('2026-03-16')
  },
  {
    userId: 1008n,
    isbn: '978-2253107627',
    status: 'RETARD',
    dateDemande: new Date('2026-01-20'),
    dateEmprunt: new Date('2026-01-21'),
    dateRetourPrevisionnel: new Date('2026-02-21')
  },
  {
    userId: 1009n,
    isbn: '978-2290364568',
    status: 'RETOURNE',
    dateDemande: new Date('2026-02-10'),
    dateEmprunt: new Date('2026-02-11'),
    dateRetourPrevisionnel: new Date('2026-03-11')
  }
];

@Component({
  selector: 'app-tableau',
  imports: [MatTableModule],
  templateUrl: './tableau.emprunts.html',
  styleUrl: './tableau.emprunts.css',
})
export class TableauEmprunts {
  displayedColumns: string[] = ['userId', 'isbn', 'status', 'dateDemande', 'dateEmprunt', 'dateRetourPrevisionnel'];
  dataSource = ELEMENT_DATA;
}
