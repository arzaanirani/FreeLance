SET SEARCH_PATH TO artistdb;

COPY (
  SELECT (Record.record_label, Record.year, Record.total_sales)
  FROM RecordLabels where Record.total_sales > 0
  ORDER ASC
));
