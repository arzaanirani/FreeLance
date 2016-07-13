SET SEARCH_PATH TO artistdb;

COPY (
  SELECT (Artist.name, Band.capacity, Artist.genres)
  FROM Collaborators
  where Artist = (Collaborators.artist1, Collaborators.artist2)
  ORDER ASC
));
