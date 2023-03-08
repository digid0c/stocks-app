--liquibase formatted sql

--changeset digid0c:create_stock_statistics_view logicalFilePath:db/changelog/changes/70
DROP VIEW IF EXISTS stock_statistics_view CASCADE;

CREATE OR REPLACE VIEW stock_statistics_view AS
    SELECT employee_id, month, year, share_id, company_name, name, isin_code, country_code, activity_field,
           AVG(price_eur) AS average_price, SUM(volume) AS total_volume, SUM(total_price) AS total_price
    FROM dev.acquired_shares_view
    GROUP BY employee_id, month, year, share_id, company_name, name, isin_code, country_code, activity_field;
--rollback drop view stock_statistics_view cascade;