export interface EmpruntModel {
  id: number;
  userId: bigint;
  livreIsbn: string;
  typeStatus: string;
  dateDemande: Date;
  dateEmprunt: Date;
  dateRetourPrevisionnel: Date;
}
