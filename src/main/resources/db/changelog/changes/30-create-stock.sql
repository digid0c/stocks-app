--liquibase formatted sql

--changeset digid0c:create_stock_table logicalFilePath:db/changelog/changes/30
DROP TABLE IF EXISTS stock CASCADE;

CREATE TABLE stock
(
    employee_id   BIGINT         NOT NULL,
    share_id      BIGINT         NOT NULL,
    price_eur     NUMERIC(8,2)   NOT NULL,
    volume        INTEGER        NOT NULL,
    reg_date      DATE           NOT NULL
);
--rollback drop table stock cascade;

--changeset digid0c:create_stock_table_employee_table_fk logicalFilePath:db/changelog/changes/30
ALTER TABLE stock
ADD CONSTRAINT fk_stock_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE ON UPDATE CASCADE;
--rollback alter table stock drop foreign key if exists fk_stock_employee;

--changeset digid0c:create_stock_table_share_table_fk logicalFilePath:db/changelog/changes/30
ALTER TABLE stock
ADD CONSTRAINT fk_stock_share FOREIGN KEY (share_id) REFERENCES share (id) ON DELETE CASCADE ON UPDATE CASCADE;
--rollback alter table stock drop foreign key if exists fk_stock_share;