SET SEARCH_PATH TO artistdb;

COPY (
  SELECT (Song.name, Artist.*)
  FROM Songs
  where Song.cover_artists > 0 AND
        Song.year > EXTRACT (Song.year | Artist.year)
  ORDER ASC
));
