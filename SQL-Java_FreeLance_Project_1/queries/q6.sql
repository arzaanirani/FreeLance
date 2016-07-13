SET SEARCH_PATH TO artistdb;

COPY (
  SELECT Artist.name
  FROM Artists
  where Artist.nationality = "Canadian"
        and Artist.record_label.nationality at any = "US"
  ORDER ASC
));
