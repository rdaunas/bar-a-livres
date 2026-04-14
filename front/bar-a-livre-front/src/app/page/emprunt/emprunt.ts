import { Component } from '@angular/core';
import {TableauEmprunts} from '../../ui/tableau.emprunts/tableau.emprunts';

@Component({
  selector: 'app-page.emprunt',
  imports: [TableauEmprunts],
  templateUrl: './emprunt.html',
  styleUrl: './emprunt.css',
})
export class PageEmprunt {}
