SET SEARCH_PATH TO artistdb;

COPY (
  SELECT *
  FROM Artists
  where Artist.covers = 0 AND
        Artist.albums > 0
  ORDER ASC
));
