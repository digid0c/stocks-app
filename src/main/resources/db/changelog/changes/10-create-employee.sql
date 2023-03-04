--liquibase formatted sql

--changeset digid0c:create_employee_table logicalFilePath:db/changelog/changes/10
DROP TABLE IF EXISTS employee CASCADE;

CREATE TABLE employee
(
    id           BIGSERIAL      PRIMARY KEY,
    first_name   VARCHAR(200)   NOT NULL,
    last_name    VARCHAR(200)   NOT NULL
);
--rollback drop table employee cascade;