import { Component, Input } from '@angular/core';
import {MatTableModule} from '@angular/material/table';
import {EmpruntModel} from '../../core/models/emprunt.model';
import {MatButtonModule} from '@angular/material/button';
import {MatDividerModule} from '@angular/material/divider';
import {MatIconModule} from '@angular/material/icon';


@Component({
  selector: 'app-tableau',
  imports: [MatTableModule, MatButtonModule, MatDividerModule, MatIconModule],
  templateUrl: './tableau.emprunts.html',
  styleUrl: './tableau.emprunts.css',
})
export class TableauEmprunts {
  @Input() dataSource: EmpruntModel[] = [];
  displayedColumns: string[] = ['userId', 'isbn', 'status', 'dateDemande', 'dateEmprunt', 'dateRetourPrevisionnel', 'action'];
}
