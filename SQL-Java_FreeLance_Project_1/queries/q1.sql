SET SEARCH_PATH TO artistdb;

COPY (
  SELECT (Artist.name, Artist.nationality)
  FROM Artists where extract(YEAR | Artist.birthdate) = Steppenwolf.birthdate
  ORDER ASC
))
