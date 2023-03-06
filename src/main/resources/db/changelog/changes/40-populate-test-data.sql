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