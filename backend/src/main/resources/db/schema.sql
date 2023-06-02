CREATE TABLE Parishes (
    id  SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    main_priest_id INTEGER NOT NULL
);

CREATE TABLE Churches (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    minimal_offering INTEGER NOT NULL,
    parish_id INTEGER NOT NULL
);

CREATE TABLE Holy_Masses (
    id  SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    available_intentions INTEGER NOT NULL,
    church_id INTEGER NOT NULL
);

CREATE TABLE Intentions (
    id  SERIAL PRIMARY KEY,
    content VARCHAR(255),
    is_paid  BOOLEAN DEFAULT false,
    holy_mass_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL
);

CREATE TABLE Users (
    id  SERIAL PRIMARY KEY,
    first_name   VARCHAR(50) NOT NULL,
    last_name    VARCHAR(50) NOT NULL,
    login   VARCHAR(50) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role    VARCHAR(50) NOT NULL
);

CREATE TABLE Roles (
    id  SERIAL PRIMARY KEY,
    name    VARCHAR(30) NOT NULL
);

CREATE TABLE Users_Roles (
    user_id INTEGER REFERENCES Users(id),
    role_id INTEGER REFERENCES Roles(id)
);

CREATE TABLE Donations (
    id  SERIAL PRIMARY KEY,
    parish_id INTEGER REFERENCES Parishes(id),
    user_id INTEGER REFERENCES Users(id),
    amount INTEGER NOT NULL
);

CREATE TABLE Goals (
    id  SERIAL PRIMARY KEY,
    parish_id INTEGER REFERENCES Parishes(id),
    gathered INTEGER NOT NULL,
    amount INTEGER NOT NULL,
    goal_title VARCHAR(30)
);

ALTER TABLE Churches ADD CONSTRAINT fk_churches_parish FOREIGN KEY (parish_id) REFERENCES Parishes(id);

ALTER TABLE Parishes ADD CONSTRAINT fk_parishes_main_priest FOREIGN KEY (main_priest_id) REFERENCES Users(id);

ALTER TABLE Holy_Masses ADD CONSTRAINT fk_holyMasses_church FOREIGN KEY (church_id) REFERENCES Churches(id);

ALTER TABLE Intentions ADD CONSTRAINT fk_intentions_holyMass FOREIGN KEY (holy_mass_id) REFERENCES Holy_Masses(id);
ALTER TABLE Intentions ADD CONSTRAINT fk_intentions_user FOREIGN KEY (user_id) REFERENCES Users(id);

INSERT INTO Parishes VALUES (1, 'Mariusza Pudzianowskiego', 1);

INSERT INTO Churches VALUES (1, 'Church nr 1', 'Wrocław', 50, 1);

INSERT INTO Holy_Masses VALUES (1, '2023-05-23', '12:00:00', 2, 1);

INSERT INTO Intentions VALUES (1, 'Na piwo', true, 1, 1);

INSERT INTO Donations VALUES (1, 1, 1, 123);

INSERT INTO Goals VALUES(1, 1, 0, 1000, 'Dobrobyt parafii')