export interface EmpruntModel {
  id: number;
  userId: number;
  livreIsbn: string;
  typeStatus: string;
  dateDemande: Date;
  dateEmprunt: Date;
  dateRetourPrevisionnel: Date;
}
