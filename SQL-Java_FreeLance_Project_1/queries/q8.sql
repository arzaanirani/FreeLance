SET SEARCH_PATH TO artistdb;

FOR Artists.ACDC (
  INSERT Artists (
    (Artist.name, Artist.year)
    where Artist.year > 2015
  )
)

COPY (
  SELECT (Song.name, Artist.*)
  FROM Songs
  where Song.cover_artists > 0 AND
        Song.year > EXTRACT (Song.year | Artist.year)
  ORDER ASC
));
