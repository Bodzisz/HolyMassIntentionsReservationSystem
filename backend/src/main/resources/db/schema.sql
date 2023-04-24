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

CREATE TABLE HolyMasses (
    id  SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    availableIntentions INTEGER NOT NULL,
    church_id INTEGER NOT NULL
);

CREATE TABLE Intentions (
    id  SERIAL PRIMARY KEY,
    content VARCHAR(255),
    isPaid  BOOLEAN DEFAULT false,
    holy_mass_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL
);

CREATE TABLE Users (
    id  SERIAL PRIMARY KEY,
    firstName   VARCHAR(50) NOT NULL,
    lastName    VARCHAR(50) NOT NULL,
    login   VARCHAR(50) NOT NULL UNIQUE,
    password    VARCHAR(50) NOT NULL,
    enabled     BOOLEAN,
    token_expired   BOOLEAN,
    parish_id INTEGER DEFAULT NULL
);

CREATE TABLE Roles (
    id  SERIAL PRIMARY KEY,
    name    VARCHAR(30) NOT NULL
);

CREATE TABLE UsersRoles (
    user_id INTEGER REFERENCES Users(id),
    role_id INTEGER REFERENCES Roles(id)
);

ALTER TABLE Churches ADD CONSTRAINT fk_churches_parish FOREIGN KEY (parish_id) REFERENCES Parishes(id);

ALTER TABLE Parishes ADD CONSTRAINT fk_parishes_main_priest FOREIGN KEY (main_priest_id) REFERENCES Users(id);

ALTER TABLE Users ADD CONSTRAINT fk_users_parish FOREIGN KEY (parish_id) REFERENCES Parishes(id);

ALTER TABLE HolyMasses ADD CONSTRAINT fk_holyMasses_church FOREIGN KEY (church_id) REFERENCES Churches(id);

ALTER TABLE Intentions ADD CONSTRAINT fk_intentions_holyMass FOREIGN KEY (holy_mass_id) REFERENCES HolyMasses(id);
ALTER TABLE Intentions ADD CONSTRAINT fk_intentions_user FOREIGN KEY (user_id) REFERENCES Users(id);