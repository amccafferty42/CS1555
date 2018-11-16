/* GROUP 16 INSERT.SQL*/

INSERT INTO ourSysDATE VALUES (sysdate);

INSERT INTO Customer VALUES ('tammy4life', 'starwars', 'Tammy Jones', '420 Dirt St, Birmingham AL 21520', 'taminatorxx@yahoo.com');
INSERT INTO Customer VALUES ('juice26', 'playhard', 'LeVeon Bell', '8 Sunny Breeze Ln, Miami FL 12836', 'juice@nfl.com');
INSERT INTO Customer VALUES ('prucker_', '12345', 'Jean Prucker', '222 Chesterfield Rd, Pittsburgh PA 15213', 'jprucker@aol.com');

INSERT INTO Administrator VALUES ('admin', 'root', 'Barry McCockner ', '142 Oak St, Chicago IL 63302', 'barry@gmail.com');

INSERT INTO Product VALUES (1, 'Ball', 'a baseball', 'tammy4life', '2018-05-06', 50, 1, 'under auction', null, null, null);
INSERT INTO Product VALUES (2, 'Book', null, 'tammy4life', '2018-01-01', 12, 5, 'sold', 'prucker_', '2018-01-02', 20);
INSERT INTO Product VALUES (3, 'Bat', 'a baseball bat', 'tammy4life', '2018-10-10', 5, 7, 'under auction', null, null, null);
INSERT INTO Product VALUES (4, 'Broom', 'slightly swept', 'prucker_', '2018-11-03', 10, 9, 'sold', 'tammy4life', '2018-11-06', 20);
INSERT INTO Product VALUES (5, 'Jet Ski', null, 'juice26', '2018-11-10', 17000, 20, 'under auction', null, null, null);

INSERT INTO Bidlog VALUES (1, 1, 'juice26', '2018-05-06', 50);
INSERT INTO Bidlog VALUES (2, 1, 'prucker_', '2018-05-06', 55);
INSERT INTO Bidlog VALUES (3, 2, 'juice26', '2018-01-02', 12);
INSERT INTO Bidlog VALUES (4, 2, 'prucker_', '2018-01-02', 13);
INSERT INTO Bidlog VALUES (5, 2, 'juice26', '2018-01-02', 19);
INSERT INTO Bidlog VALUES (6, 2, 'prucker_', '2018-01-02', 7);
INSERT INTO Bidlog VALUES (7, 3, 'juice26', '2018-10-10', 20);
INSERT INTO Bidlog VALUES (8, 4, 'juice26', '2018-11-03', 10);
INSERT INTO Bidlog VALUES (9, 4, 'tammy4life', '2018-11-06', 12);
INSERT INTO Bidlog VALUES (10, 4, 'juice26', '2018-11-06', 20);

INSERT INTO Category VALUES ('Equipment', 'Sports');
INSERT INTO Category VALUES ('Motor Sports', 'Sports');
INSERT INTO Category VALUES ('Sports', null);
INSERT INTO Category VALUES ('Cleaning', 'Household Objects');
INSERT INTO Category VALUES ('Household Objects', null);

INSERT INTO BelongsTo VALUES (1, 'Equipment');
INSERT INTO BelongsTo VALUES (2, 'Household Objects');
INSERT INTO BelongsTo VALUES (3, 'Equipment');
INSERT INTO BelongsTo VALUES (4, 'Cleaning');
INSERT INTO BelongsTo VALUES (5, 'Motor Sports');