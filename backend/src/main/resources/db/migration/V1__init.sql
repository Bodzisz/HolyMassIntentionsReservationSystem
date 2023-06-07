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