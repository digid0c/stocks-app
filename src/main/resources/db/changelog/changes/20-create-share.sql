--liquibase formatted sql

--changeset digid0c:create_share_table logicalFilePath:db/changelog/changes/20
DROP TABLE IF EXISTS share CASCADE;

CREATE TABLE share
(
    id               BIGSERIAL      PRIMARY KEY,
    company_name     VARCHAR(200)   NOT NULL,
    name             VARCHAR(200)   NOT NULL,
    isin_code        VARCHAR(12)    NOT NULL,
    country_code     VARCHAR(3)     NOT NULL,
    activity_field   VARCHAR(200)   NOT NULL
);
--rollback drop table share cascade;