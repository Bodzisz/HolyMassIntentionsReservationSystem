ALTER TABLE Churches ADD CONSTRAINT fk_churches_parish FOREIGN KEY (parish_id) REFERENCES Parishes(id);

ALTER TABLE Parishes ADD CONSTRAINT fk_parishes_main_priest FOREIGN KEY (main_priest_id) REFERENCES Users(id);

ALTER TABLE Holy_Masses ADD CONSTRAINT fk_holyMasses_church FOREIGN KEY (church_id) REFERENCES Churches(id);

ALTER TABLE Intentions ADD CONSTRAINT fk_intentions_holyMass FOREIGN KEY (holy_mass_id) REFERENCES Holy_Masses(id);

ALTER TABLE Intentions ADD CONSTRAINT fk_intentions_user FOREIGN KEY (user_id) REFERENCES Users(id);

INSERT INTO Users VALUES (1, 'Kacper', 'Wójcicki', 'Kapi', '1234', 'priest');

INSERT INTO Parishes VALUES (1, 'Mariusza Pudzianowskiego', 1);

INSERT INTO Churches VALUES (1, 'Church nr 1', 'Wrocław', 50, 1);

INSERT INTO Holy_Masses VALUES (1, '2023-05-23', '12:00:00', 2, 1);

INSERT INTO Intentions VALUES (1, 'Na piwo', true, 1, 1);

INSERT INTO Donations VALUES (1, 1, 1, 123);

INSERT INTO Goals VALUES(1, 1, 0, 1000, 'Dobrobyt parafii');