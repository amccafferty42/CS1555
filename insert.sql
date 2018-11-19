/* GROUP 16 INSERT.SQL*/

--inserting everything
INSERT INTO ourSysDATE VALUES (sysdate);

INSERT INTO Customer VALUES ('tammy4life', 'starwars', 'Tammy Jones', '420 D St, Bham AL 21', 'taminator@yahoo.com');
INSERT INTO Customer VALUES ('juice26', 'playhard', 'LeVeon Bell', '8 B Ln, Miami FL 1', 'juice@nfl.com');
INSERT INTO Customer VALUES ('prucker_', '12345', 'Jean Prucker', '22 C Rd, Pburgh PA 5', 'jprucker@aol.com');

INSERT INTO Administrator VALUES ('admin', 'root', 'Barry McCockner ', '1 O St, Chicago IL 6', 'barry@gmail.com');

INSERT INTO Product VALUES (1, 'Ball', 'a baseball', 'tammy4life', sysdate, 50, 1, 'under auction', null, null, null);
INSERT INTO Product VALUES (2, 'Book', null, 'tammy4life', sysdate, 12, 5, 'close', 'prucker_', sysdate, 20);
INSERT INTO Product VALUES (3, 'Bat', 'a baseball bat', 'tammy4life', sysdate, 5, 7, 'under auction', null, null, null);
INSERT INTO Product VALUES (4, 'Broom', 'slightly swept', 'prucker_', sysdate, 10, 9, 'close', 'tammy4life', sysdate, 20);
INSERT INTO Product VALUES (5, 'Jet Ski', null, 'juice26', sysdate, 17000, 20, 'under auction', null, sysdate, null);

INSERT INTO Bidlog VALUES (1, 1, 'juice26', sysdate, 50);
INSERT INTO Bidlog VALUES (2, 1, 'prucker_', sysdate, 55);
INSERT INTO Bidlog VALUES (3, 2, 'juice26', sysdate, 12);
INSERT INTO Bidlog VALUES (4, 2, 'prucker_', sysdate, 13);
INSERT INTO Bidlog VALUES (5, 2, 'juice26', sysdate, 19);
INSERT INTO Bidlog VALUES (6, 2, 'prucker_', sysdate, 7);
INSERT INTO Bidlog VALUES (7, 3, 'juice26', sysdate, 20);
INSERT INTO Bidlog VALUES (8, 4, 'juice26', sysdate, 10);
INSERT INTO Bidlog VALUES (9, 4, 'tammy4life', sysdate, 12);
INSERT INTO Bidlog VALUES (10, 4, 'juice26', sysdate, 20);

INSERT INTO Category VALUES ('Equipment', null);
INSERT INTO Category VALUES ('Motor Sports', null);
INSERT INTO Category VALUES ('Sports', null);
INSERT INTO Category VALUES ('Cleaning', null);
INSERT INTO Category VALUES ('Household Objects', null);

INSERT INTO BelongsTo VALUES (1, 'Equipment');
INSERT INTO BelongsTo VALUES (2, 'Household Objects');
INSERT INTO BelongsTo VALUES (3, 'Equipment');
INSERT INTO BelongsTo VALUES (4, 'Cleaning');
INSERT INTO BelongsTo VALUES (5, 'Motor Sports');

