export interface EmpruntModel {

  userId: bigint;
  isbn: string;
  status: string;
  dateDemande: Date;
  dateEmprunt: Date;
  dateRetourPrevisionnel: Date;

}
