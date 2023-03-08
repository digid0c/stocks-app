--liquibase formatted sql

--changeset digid0c:populate_employee_test_data logicalFilePath:db/changelog/changes/40
INSERT INTO employee (first_name, last_name) VALUES ('John', 'Deere');
INSERT INTO employee (first_name, last_name) VALUES ('Clyde', 'Robson');
--rollback delete from employee;

--changeset digid0c:populate_share_test_data logicalFilePath:db/changelog/changes/40
INSERT INTO share (company_name, name, isin_code, country_code, activity_field)
VALUES ('Company 1', 'Share 1', 'US0120719985', 'USA', 'Technology');
INSERT INTO share (company_name, name, isin_code, country_code, activity_field)
VALUES ('Company 1', 'Share 2', 'US0120719996', 'USA', 'Technology');
INSERT INTO share (company_name, name, isin_code, country_code, activity_field)
VALUES ('Company 1', 'Share 3', 'US0120719974', 'USA', 'Technology');
INSERT INTO share (company_name, name, isin_code, country_code, activity_field)
VALUES ('Company 2', 'Share 1', 'US0120719963', 'USA', 'Healthcare');
INSERT INTO share (company_name, name, isin_code, country_code, activity_field)
VALUES ('Company 2', 'Share 2', 'US0120719952', 'USA', 'Healthcare');
--rollback delete from share;

--changeset digid0c:populate_stock_test_data logicalFilePath:db/changelog/changes/40
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (1, 2, 6, 6, '2022-12-01');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (1, 2, 8.5, 7, '2022-12-01');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (1, 1, 10, 5, '2023-01-01');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (1, 3, 8.5, 10, '2023-01-05');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (1, 5, 3, 15, '2023-01-08');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (1, 5, 3.3, 8, '2023-01-08');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (1, 1, 12, 4, '2023-02-01');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (1, 2, 7.5, 4, '2023-02-07');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (1, 2, 9.5, 3, '2023-02-07');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (2, 2, 8, 5, '2022-12-03');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (2, 2, 9.3, 9, '2022-12-03');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (2, 5, 6.5, 10, '2023-01-01');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (2, 4, 12, 2, '2023-01-06');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (2, 4, 9.8, 3, '2023-01-06');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (2, 2, 7, 3, '2023-01-12');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (2, 2, 6, 4, '2023-02-04');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (2, 2, 6.3, 6, '2023-02-04');
INSERT INTO stock (employee_id, share_id, price_eur, volume, reg_date) VALUES (2, 4, 10, 10, '2023-02-11');
--rollback delete from stock;