INSERT INTO role (label) VALUES
    ('USER'),
    ('LIBRARIAN'),
    ('ADMIN');

INSERT INTO categorie (nom) VALUES
    ('Roman'),
    ('Science-Fiction'),
    ('Fantasy'),
    ('Policier'),
    ('Thriller'),
    ('Développement personnel'),
    ('Histoire'),
    ('Biographie'),
    ('Informatique'),
    ('Jeunesse');

INSERT INTO statut (nom) VALUES
    ('En attente'),
    ('En cours'),
    ('Terminé'),
    ('Annulé');

INSERT INTO users (nom, prenom, email, password, role_id) VALUES
    ('Dupont', 'Alice', 'alice@mail.com', 'pass123', 1),
    ('Martin', 'Lucas', 'lucas@mail.com', 'pass123', 1),
    ('Bernard', 'Emma', 'emma@mail.com', 'pass123', 1),
    ('Durand', 'Paul', 'paul@mail.com', 'pass123', 2),
    ('Petit', 'Sophie', 'sophie@mail.com', 'pass123', 2),
    ('Robert', 'Julien', 'julien@mail.com', 'pass123', 3),
    ('Richard', 'Nina', 'nina@mail.com', 'pass123', 1),
    ('Moreau', 'Leo', 'leo@mail.com', 'pass123', 1),
    ('Simon', 'Chloé', 'chloe@mail.com', 'pass123', 1),
    ('Laurent', 'Hugo', 'hugo@mail.com', 'pass123', 1);

INSERT INTO livre (isbn, couverture, titre, auteur, description, nb_exemplaires, date_ajout, is_active) VALUES
('9780007117116','https://covers.openlibrary.org/b/isbn/9780007117116-L.jpg','Eragon','Christopher Paolini','Dragon et magie',1,'2025-01-10',1),
('9780061120084','https://covers.openlibrary.org/b/isbn/9780061120084-L.jpg','American Gods','Neil Gaiman','Fantasy moderne',2,'2025-01-10',1),
('9780062073488','https://covers.openlibrary.org/b/isbn/9780062073488-L.jpg','And Then There Were None','Agatha Christie','Mystère',1,'2025-01-10',1),
('9780064404990','https://covers.openlibrary.org/b/isbn/9780064404990-L.jpg','Chroniques de Narnia','C.S. Lewis','Fantasy jeunesse',1,'2025-01-10',1),
('9780131103627','https://covers.openlibrary.org/b/isbn/9780131103627-L.jpg','The C Programming Language','Kernighan & Ritchie','Langage C',1,'2025-01-10',1),
('9780132350884','https://covers.openlibrary.org/b/isbn/9780132350884-L.jpg','Clean Architecture','Robert C. Martin','Architecture logicielle',2,'2025-01-10',1),
('9780140449136','https://covers.openlibrary.org/b/isbn/9780140449136-L.jpg','Germinal','Émile Zola','Roman social',1,'2025-01-10',1),
('9780307743657','https://covers.openlibrary.org/b/isbn/9780307743657-L.jpg','The Shining','Stephen King','Horreur psychologique',2,'2025-01-10',1),
('9780345339683','https://covers.openlibrary.org/b/isbn/9780345339683-L.jpg','Le Hobbit','J.R.R. Tolkien','Fantasy épique',1,'2025-01-10',1),
('9780553293357','https://covers.openlibrary.org/b/isbn/9780553293357-L.jpg','Ender’s Game','Orson Scott Card','SF militaire',1,'2025-01-10',1),
('9780553573404','https://covers.openlibrary.org/b/isbn/9780553573404-L.jpg','A Game of Thrones','George R.R. Martin','Fantasy politique',1,'2025-01-10',1),
('9780747532743','https://covers.openlibrary.org/b/isbn/9780747532743-L.jpg','Harry Potter à l’école des sorciers','J.K. Rowling','Magie et aventure',2,'2025-01-10',1),
('9781491957660','https://covers.openlibrary.org/b/isbn/9781491957660-L.jpg','Python Crash Course','Eric Matthes','Python',2,'2025-01-10',1),
('9781492051138','https://covers.openlibrary.org/b/isbn/9781492051138-L.jpg','Learning JavaScript','Ethan Brown','JavaScript',1,'2025-01-10',1),
('9782020251554','https://covers.openlibrary.org/b/isbn/9782020251554-L.jpg','Les origines des espèces','Charles Darwin','Évolution',1,'2025-01-10',1),
('9782021086834','https://covers.openlibrary.org/b/isbn/9782021086834-L.jpg','Nelson Mandela','Nelson Mandela','Autobiographie',1,'2025-01-10',1),
('9782070360024','https://covers.openlibrary.org/b/isbn/9782070360024-L.jpg','Madame Bovary','Gustave Flaubert','Roman réaliste',2,'2025-01-10',1),
('9782070368228','https://covers.openlibrary.org/b/isbn/9782070368228-L.jpg','L’Étranger','Albert Camus','Roman existentiel',1,'2025-01-10',1),
('9782070396337','https://covers.openlibrary.org/b/isbn/9782070396337-L.jpg','Seconde Guerre mondiale','Winston Churchill','Guerre mondiale',2,'2025-01-10',1),
('9782070408504','https://covers.openlibrary.org/b/isbn/9782070408504-L.jpg','Le Crime de l’Orient-Express','Agatha Christie','Enquête policière',1,'2025-01-10',1),
('9782070412136','https://covers.openlibrary.org/b/isbn/9782070412136-L.jpg','Le Journal d’Anne Frank','Anne Frank','Témoignage',2,'2025-01-10',1),
('9782070413119','https://covers.openlibrary.org/b/isbn/9782070413119-L.jpg','Le Rouge et le Noir','Stendhal','Roman psychologique',1,'2025-01-10',1),
('9782070415747','https://covers.openlibrary.org/b/isbn/9782070415747-L.jpg','Fondation','Isaac Asimov','Empire galactique',1,'2025-01-10',1),
('9782070421053','https://covers.openlibrary.org/b/isbn/9782070421053-L.jpg','Maigret tend un piège','Georges Simenon','Policier classique',2,'2025-01-10',1),
('9782070452439','https://covers.openlibrary.org/b/isbn/9782070452439-L.jpg','Sapiens','Yuval Noah Harari','Histoire de l’humanité',1,'2025-01-10',1),
('9782070543696','https://covers.openlibrary.org/b/isbn/9782070543696-L.jpg','Le Livre de la jungle','Rudyard Kipling','Aventure',1,'2025-01-10',1),
('9782070612361','https://covers.openlibrary.org/b/isbn/9782070612361-L.jpg','Matilda','Roald Dahl','Jeunesse',1,'2025-01-10',1),
('9782070612750','https://covers.openlibrary.org/b/isbn/9782070612750-L.jpg','Le Petit Prince','Antoine de Saint-Exupéry','Conte philosophique',1,'2025-01-10',1),
('9782070615369','https://covers.openlibrary.org/b/isbn/9782070615369-L.jpg','Le Seigneur des Anneaux','J.R.R. Tolkien','Fantasy épique',2,'2025-01-10',1),
('9782070643020','https://covers.openlibrary.org/b/isbn/9782070643020-L.jpg','Le Petit Nicolas','René Goscinny','Humour',1,'2025-01-10',1),
('9782081275680','https://covers.openlibrary.org/b/isbn/9782081275680-L.jpg','Napoléon','Max Gallo','Biographie historique',1,'2025-01-10',1),
('9782212673290','https://covers.openlibrary.org/b/isbn/9782212673290-L.jpg','Clean Code','Robert C. Martin','Programmation',2,'2025-01-10',1),
('9782253004226','https://covers.openlibrary.org/b/isbn/9782253004226-L.jpg','Les Misérables','Victor Hugo','Roman classique',2,'2025-01-10',1),
('9782253057623','https://covers.openlibrary.org/b/isbn/9782253057623-L.jpg','Steve Jobs','Walter Isaacson','Biographie entrepreneur',1,'2025-01-10',1),
('9782253087316','https://covers.openlibrary.org/b/isbn/9782253087316-L.jpg','Einstein','Walter Isaacson','Physicien',1,'2025-01-10',1),
('9782253096344','https://covers.openlibrary.org/b/isbn/9782253096344-L.jpg','Le Silence des agneaux','Thomas Harris','Thriller criminel',2,'2025-01-10',1),
('9782253101451','https://covers.openlibrary.org/b/isbn/9782253101451-L.jpg','Dune','Frank Herbert','Science-fiction culte',1,'2025-01-10',1),
('9782253112174','https://covers.openlibrary.org/b/isbn/9782253112174-L.jpg','Misery','Stephen King','Thriller huis clos',1,'2025-01-10',1),
('9782253160304','https://covers.openlibrary.org/b/isbn/9782253160304-L.jpg','Le Nom de la Rose','Umberto Eco','Enquête médiévale',1,'2025-01-10',1),
('9782253177000','https://covers.openlibrary.org/b/isbn/9782253177000-L.jpg','Elon Musk','Ashlee Vance','Technologie',1,'2025-01-10',1),
('9782253238824','https://covers.openlibrary.org/b/isbn/9782253238824-L.jpg','Pouvoir du moment présent','Eckhart Tolle','Spiritualité',2,'2025-01-10',1),
('9782262070333','https://covers.openlibrary.org/b/isbn/9782262070333-L.jpg','Histoire de France','Jules Michelet','Histoire nationale',2,'2025-01-10',1),
('9782266093264','https://covers.openlibrary.org/b/isbn/9782266093264-L.jpg','Shutter Island','Dennis Lehane','Thriller psychologique',2,'2025-01-10',1),
('9782266152602','https://covers.openlibrary.org/b/isbn/9782266152602-L.jpg','Da Vinci Code','Dan Brown','Thriller ésotérique',2,'2025-01-10',1),
('9782266201959','https://covers.openlibrary.org/b/isbn/9782266201959-L.jpg','Anges et Démons','Dan Brown','Thriller religieux',2,'2025-01-10',1),
('9782266282378','https://covers.openlibrary.org/b/isbn/9782266282378-L.jpg','1984','George Orwell','Dystopie',2,'2025-01-10',1),
('9782290135285','https://covers.openlibrary.org/b/isbn/9782290135285-L.jpg','Le Secret','Rhonda Byrne','Loi de l’attraction',2,'2025-01-10',1),
('9782290140968','https://covers.openlibrary.org/b/isbn/9782290140968-L.jpg','L’art subtil de s’en foutre','Mark Manson','Développement personnel',1,'2025-01-10',1),
('9782290159755','https://covers.openlibrary.org/b/isbn/9782290159755-L.jpg','Les 4 accords toltèques','Don Miguel Ruiz','Sagesse personnelle',1,'2025-01-10',1),
('9782813218730','https://covers.openlibrary.org/b/isbn/9782813218730-L.jpg','Atomic Habits','James Clear','Habitudes',2,'2025-01-10',1);

-- =====================
-- LIVRE_CATEGORIE (MANY TO MANY)
-- =====================

INSERT INTO livre_categorie (livre_isbn, categorie_id) VALUES

-- =====================
-- ROMAN (categorie_id = 1)
-- =====================
('9782070368228', 1),
('9782253004226', 1),
('9780140449136', 1),
('9782070413119', 1),
('9782070360024', 1),

-- =====================
-- SCIENCE-FICTION (categorie_id = 2)
-- =====================
('9782253101451', 2),
('9782070415747', 2),
('9782266282378', 2),
('9780345339683', 2),
('9780553293357', 2),

-- =====================
-- FANTASY (categorie_id = 3)
-- =====================
('9780747532743', 3),
('9782070615369', 3),
('9780553573404', 3),
('9780061120084', 3),
('9780007117116', 3),

-- =====================
-- POLICIER (categorie_id = 4)
-- =====================
('9782070408504', 4),
('9780062073488', 4),
('9782266093264', 4),
('9782253160304', 4),
('9782070421053', 4),

-- =====================
-- THRILLER (categorie_id = 5)
-- =====================
('9780307743657', 5),
('9782266152602', 5),
('9782266201959', 5),
('9782253112174', 5),
('9782253096344', 5),

-- =====================
-- DÉVELOPPEMENT PERSONNEL (categorie_id = 6)
-- =====================
('9782290159755', 6),
('9782290140968', 6),
('9782253238824', 6),
('9782813218730', 6),
('9782290135285', 6),

-- =====================
-- HISTOIRE (categorie_id = 7)
-- =====================
('9782070452439', 7),
('9782262070333', 7),
('9782070396337', 7),
('9782081275680', 7),
('9782020251554', 7),

-- =====================
-- BIOGRAPHIE (categorie_id = 8)
-- =====================
('9782253057623', 8),
('9782253087316', 8),
('9782021086834', 8),
('9782070412136', 8),
('9782253177000', 8),

-- =====================
-- INFORMATIQUE (categorie_id = 9)
-- =====================
('9782212673290', 9),
('9781491957660', 9),
('9780132350884', 9),
('9781492051138', 9),
('9780131103627', 9),

-- =====================
-- JEUNESSE (categorie_id = 10)
-- =====================
('9782070612750', 10),
('9782070643020', 10),
('9782070543696', 10),
('9780064404990', 10),
('9782070612361', 10);

-- =====================
-- EXEMPLES MANY-TO-MANY (un livre dans plusieurs catégories)
-- =====================

-- Le Hobbit (Fantasy + Science-Fiction)
INSERT INTO livre_categorie (livre_isbn, categorie_id) VALUES
('9780345339683', 3);

-- Sapiens (Histoire + Développement personnel)
INSERT INTO livre_categorie (livre_isbn, categorie_id) VALUES
('9782070452439', 6);

-- Clean Code (Informatique + Développement personnel)
INSERT INTO livre_categorie (livre_isbn, categorie_id) VALUES
('9782212673290', 6);