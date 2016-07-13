SET SEARCH_PATH TO artistdb;

COPY (
  SELECT *
  FROM Artists AVG(Artist.total_sales) > AVG(Artist.record_label.total_sales)
  ORDER ASC
));