SET SEARCH_PATH TO artistdb;


COPY (
  SELECT (Artist.name, Band.capacity, Artist.genres)
  FROM Collaborators
  where Artist = (
        Song.cover_artists > 0 AND
            Song.year > EXTRACT (Song.year | Artist.year)
        AS Artist.name == "Jagger" or Artist.lead_singer = "Avril Levine"
  )
  ORDER ASC
));

COPY (

));
