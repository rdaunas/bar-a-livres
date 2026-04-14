export interface EmpruntModel {

  userId: bigint;
  livreIsbn: string;
  typeStatus: string;
  dateDemande: Date;
  dateEmprunt: Date;
  dateRetourPrevisionnel: Date;
}
