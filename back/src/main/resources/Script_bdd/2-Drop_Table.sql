-- =====================
-- DROP TABLES
-- =====================

IF OBJECT_ID('feedback', 'U') IS NOT NULL
   DROP TABLE feedback;
IF OBJECT_ID('emprunt', 'U') IS NOT NULL
   DROP TABLE emprunt;
IF OBJECT_ID('livre_categorie', 'U') IS NOT NULL
   DROP TABLE livre_categorie;
IF OBJECT_ID('users', 'U') IS NOT NULL
   DROP TABLE users;
IF OBJECT_ID('livre', 'U') IS NOT NULL
   DROP TABLE livre;
IF OBJECT_ID('categorie', 'U') IS NOT NULL
   DROP TABLE categorie;
IF OBJECT_ID('statut', 'U') IS NOT NULL
   DROP TABLE statut;
IF OBJECT_ID('role', 'U') IS NOT NULL
   DROP TABLE role;