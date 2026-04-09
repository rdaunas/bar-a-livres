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

INSERT INTO livre (isbn, couverture, titre, auteur, description, nb_exemplaires, date_ajout, is_active, categorie_id) VALUES
-- Romans
('ISBN001', 'cover1.jpg', 'Le souffle des mots', 'Jean Dupont', 'Un roman poignant', 5, GETDATE(), 1, 1),
('ISBN002', 'cover2.jpg', 'L''ombre du vent', 'Carlos Ruiz Zafón', 'Mystère et littérature', 3, GETDATE(), 1, 1),
('ISBN003', 'cover3.jpg', 'Les jours heureux', 'Claire Martin', 'Une histoire touchante', 4, GETDATE(), 1, 1),
('ISBN004', 'cover4.jpg', 'Amour et destin', 'Sophie Laurent', 'Romance moderne', 6, GETDATE(), 1, 1),
('ISBN005', 'cover5.jpg', 'La vie en pause', 'Marc Petit', 'Réflexion sur la vie', 2, GETDATE(), 1, 1),

-- Science-Fiction
('ISBN006', 'cover6.jpg', 'Galaxie perdue', 'Isaac Nova', 'Voyage spatial', 7, GETDATE(), 1, 2),
('ISBN007', 'cover7.jpg', 'Planète rouge', 'Elon Mars', 'Colonisation de Mars', 5, GETDATE(), 1, 2),
('ISBN008', 'cover8.jpg', 'Cyber monde', 'Neo Smith', 'Futur numérique', 6, GETDATE(), 1, 2),
('ISBN009', 'cover9.jpg', 'IA 2099', 'Alan Turing Jr', 'Intelligence artificielle', 3, GETDATE(), 1, 2),
('ISBN010', 'cover10.jpg', 'Les étoiles noires', 'Luna Sky', 'Exploration spatiale', 4, GETDATE(), 1, 2),

-- Fantasy
('ISBN011', 'cover11.jpg', 'Le royaume oublié', 'J.R.R. Martin', 'Monde fantastique', 8, GETDATE(), 1, 3),
('ISBN012', 'cover12.jpg', 'Dragons et légendes', 'Anne Dragon', 'Créatures mythiques', 5, GETDATE(), 1, 3),
('ISBN013', 'cover13.jpg', 'L''épée sacrée', 'Arthur King', 'Quête héroïque', 6, GETDATE(), 1, 3),
('ISBN014', 'cover14.jpg', 'Magie noire', 'Sabrina Witch', 'Pouvoirs occultes', 4, GETDATE(), 1, 3),
('ISBN015', 'cover15.jpg', 'Le dernier mage', 'Gandalf Grey', 'Fin d''une ère', 3, GETDATE(), 1, 3),

-- Policier
('ISBN016', 'cover16.jpg', 'Enquête à Paris', 'Michel Blanc', 'Crime mystérieux', 6, GETDATE(), 1, 4),
('ISBN017', 'cover17.jpg', 'Le suspect parfait', 'Julie Noir', 'Suspense total', 5, GETDATE(), 1, 4),
('ISBN018', 'cover18.jpg', 'Crime en silence', 'Paul Verne', 'Enquête sombre', 4, GETDATE(), 1, 4),
('ISBN019', 'cover19.jpg', 'Dossier classé', 'Agent X', 'Affaire secrète', 7, GETDATE(), 1, 4),
('ISBN020', 'cover20.jpg', 'La dernière preuve', 'Clara Justice', 'Vérité cachée', 3, GETDATE(), 1, 4),

-- Thriller
('ISBN021', 'cover21.jpg', 'Course contre la mort', 'Tom Rush', 'Action intense', 6, GETDATE(), 1, 5),
('ISBN022', 'cover22.jpg', 'Danger immédiat', 'Jack Ryan', 'Menace mondiale', 5, GETDATE(), 1, 5),
('ISBN023', 'cover23.jpg', 'Le code secret', 'Dan Brown Jr', 'Mystère religieux', 4, GETDATE(), 1, 5),
('ISBN024', 'cover24.jpg', 'Mission fatale', 'Ethan Hunt', 'Espionnage', 6, GETDATE(), 1, 5),
('ISBN025', 'cover25.jpg', 'Traque nocturne', 'Night Hunter', 'Chasse à l''homme', 3, GETDATE(), 1, 5),

-- Développement personnel
('ISBN026', 'cover26.jpg', 'Réussir sa vie', 'Anthony Success', 'Motivation', 10, GETDATE(), 1, 6),
('ISBN027', 'cover27.jpg', 'Confiance en soi', 'Marie Forte', 'Développement personnel', 8, GETDATE(), 1, 6),
('ISBN028', 'cover28.jpg', 'Objectifs atteints', 'Goal Master', 'Atteindre ses rêves', 7, GETDATE(), 1, 6),
('ISBN029', 'cover29.jpg', 'Changer ses habitudes', 'Habit Guru', 'Transformation', 6, GETDATE(), 1, 6),
('ISBN030', 'cover30.jpg', 'Mindset gagnant', 'Winner Mind', 'Psychologie du succès', 9, GETDATE(), 1, 6),

-- Histoire
('ISBN031', 'cover31.jpg', 'La guerre mondiale', 'Historien Pro', 'WW2', 5, GETDATE(), 1, 7),
('ISBN032', 'cover32.jpg', 'Empire romain', 'Jules Historicus', 'Rome antique', 4, GETDATE(), 1, 7),
('ISBN033', 'cover33.jpg', 'Révolution française', 'Napoléon Expert', '1789', 6, GETDATE(), 1, 7),
('ISBN034', 'cover34.jpg', 'Égypte ancienne', 'Pharaon King', 'Civilisation antique', 3, GETDATE(), 1, 7),
('ISBN035', 'cover35.jpg', 'Moyen Âge', 'Knight Writer', 'Période médiévale', 5, GETDATE(), 1, 7),

-- Biographie
('ISBN036', 'cover36.jpg', 'Steve Jobs', 'Walter Isaacson', 'Vie d''un génie', 4, GETDATE(), 1, 8),
('ISBN037', 'cover37.jpg', 'Elon Musk', 'Ashlee Vance', 'Visionnaire', 5, GETDATE(), 1, 8),
('ISBN038', 'cover38.jpg', 'Napoléon', 'Historien X', 'Empereur', 3, GETDATE(), 1, 8),
('ISBN039', 'cover39.jpg', 'Marie Curie', 'Science Woman', 'Scientifique', 4, GETDATE(), 1, 8),
('ISBN040', 'cover40.jpg', 'Mandela', 'Freedom Writer', 'Liberté', 6, GETDATE(), 1, 8),

-- Informatique
('ISBN041', 'cover41.jpg', 'Apprendre PHP', 'Dev Expert', 'Programmation PHP', 10, GETDATE(), 1, 9),
('ISBN042', 'cover42.jpg', 'Maîtriser JavaScript', 'JS Ninja', 'Frontend avancé', 9, GETDATE(), 1, 9),
('ISBN043', 'cover43.jpg', 'Symfony 7', 'Framework Master', 'Backend moderne', 8, GETDATE(), 1, 9),
('ISBN044', 'cover44.jpg', 'Angular 17', 'Frontend Pro', 'SPA moderne', 7, GETDATE(), 1, 9),
('ISBN045', 'cover45.jpg', 'React 18', 'UI Expert', 'Composants React', 6, GETDATE(), 1, 9),

-- Jeunesse
('ISBN046', 'cover46.jpg', 'Petit dragon', 'Kids Author', 'Conte enfant', 5, GETDATE(), 1, 10),
('ISBN047', 'cover47.jpg', 'Aventure magique', 'Fairy Tale', 'Histoire magique', 6, GETDATE(), 1, 10),
('ISBN048', 'cover48.jpg', 'Le monde des animaux', 'Nature Kid', 'Découverte', 7, GETDATE(), 1, 10),
('ISBN049', 'cover49.jpg', 'Super héros', 'Comic Maker', 'Héros enfants', 8, GETDATE(), 1, 10),
('ISBN050', 'cover50.jpg', 'Voyage scolaire', 'School Story', 'Aventure scolaire', 5, GETDATE(), 1, 10);