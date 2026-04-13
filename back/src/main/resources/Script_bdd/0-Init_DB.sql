-- =====================
-- TABLE USERS
-- =====================
CREATE TABLE role (
    id INT IDENTITY(1,1) PRIMARY KEY,
    label VARCHAR(20)
)

-- =====================
-- TABLE USERS
-- =====================
CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL
    FOREIGN KEY (role_id) REFERENCES role(id)
);

-- =====================
-- TABLE CATEGORIE
-- =====================
CREATE TABLE categorie (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
);

-- =====================
-- TABLE LIVRE
-- =====================
CREATE TABLE livre (
    isbn VARCHAR(20) PRIMARY KEY,
    couverture VARCHAR(255),
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255),
    description TEXT,
    nb_exemplaires INT DEFAULT 0,
    date_ajout DATE,
    is_active BIT DEFAULT 1,
    categorie_id INT,
    );

-- =====================
-- TABLE STATUT
-- =====================
CREATE TABLE statut (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
);

-- =====================
-- TABLE EMPRUNT
-- =====================
CREATE TABLE emprunt (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    livre_isbn VARCHAR(20) NOT NULL,
    statut_id INT,
    date_demande DATE,
    date_emprunt DATE,
    date_retour_previsionnel DATE,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (livre_isbn) REFERENCES livre(isbn),
    FOREIGN KEY (statut_id) REFERENCES statut(id)
);

-- =====================
-- TABLE FEEDBACK
-- =====================
CREATE TABLE feedback (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    livre_isbn VARCHAR(20) NOT NULL,
    note INT CHECK (note BETWEEN 1 AND 5),
    commentaire TEXT,
    date_publication DATE,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (livre_isbn) REFERENCES livre(isbn)
);

-- =====================
-- TABLE MANY TO MANY LIVRE_CATEGORIE
-- =====================
CREATE TABLE livre_categorie (
    livre_isbn VARCHAR(20),
    categorie_id INT,
    PRIMARY KEY (livre_isbn, categorie_id),
    FOREIGN KEY (livre_isbn) REFERENCES livre(isbn),
    FOREIGN KEY (categorie_id) REFERENCES categorie(id)
);