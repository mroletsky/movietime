### Back-end made with help from
https://www.youtube.com/watch?v=31KTdfRH6nY&ab_channel=freeCodeCamp.org



# API Documentation

# FilmController

## GET /api/films

This endpoint retrieves all the films from the database.

### Responses:

- `200 OK`: The request was successful. The response body contains a list of all the films in the database.

## GET /api/films/{id}

This endpoint retrieves the film from the database that has the specified id.

### Path Parameters:

- `id`: The id of the film to retrieve.

### Responses:

- `200 OK`: The request was successful. The response body contains a list of films that hav an age limit less than or equal to the specified age.
- `400 Bad Request`: The request was malformed. This could be due the film not being present in the database.

## GET /api/films/age/{age}

This endpoint retrieves a list of films from the database that have an age limit less than or equal to the specified age.

### Path Parameters:

- `age`: The maximum age limit of the films to retrieve. This should be a non-negative integer.

### Responses:

- `200 OK`: The request was successful. The response body contains a list of films that hav an age limit less than or equal to the specified age.
- `400 Bad Request`: The request was malformed. This could be due to a negative age.

## GET /api/films/genre/{genre}

This endpoint retrieves a list of films from the database that match the specified genre.

### Path Parameters:

- `genre`: The genre of the films to retrieve. This should be one of the values defined in the `Genre` enum.

### Responses:

- `200 OK`: The request was successful. The response body contains a list of films that match the specified genre.
- `400 Bad Request`: The request was malformed. This could be due to an invalid genre.


## GET /api/films/genre/{genre}/age/{age}

This endpoint retrieves a list of films from the database that match the specified genre and have an age limit less than or equal to the specified age.

### Path Parameters:

- `genre`: The genre of the films to retrieve. This should be one of the values defined in the `Genre` enum.
- `age`: The maximum age limit of the films to retrieve. This should be a non-negative integer.

### Responses:

- `200 OK`: The request was successful. The response body contains a list of films that match the specified genre and have an age limit less than or equal to the specified age.
- `400 Bad Request`: The request was malformed. This could be due to an invalid genre or a negative age.

## GET /api/films/recommend

This endpoint retrieves the most recommended film for the user based on the watch history .

### Responses:

- `200 OK`: The request was successful. The response body contains the film that the algorithm thinks is most likeable for the user.

## POST /api/films/watch

This endpoint adds a film to the watch history.

### Request Body:

The request body should contain a `Film` object with the following properties:

- `id`: The ID of the film.
- `title`: The title of the film.
- `directors`: The director(s) of the film.
- `genres`: A list of genres that the film belongs to.
- `age_limit`: The age limit of the film.
- `duration`: The duration of the film in minutes.

### Responses:

- `201 Created`: The request was successful and the film was added to the watch history.
- `400 Bad Request`: The request was malformed. This could be due to an invalid `Film` object in the request body.

# FilmRepository

## Method: findAll()

This method retrieves a list of all the films in the database.

### Returns:

A list of all `Film` objects that are present in the database.

## Method: findById(Integer id)

This method retrieves the film with the specified id from the database.

### Parameters:

- `id`: The id of the film to retrieve.

### Returns:

A `Film` object that has the specified id.

## Method: findByAgeLimit(Integer age)

This method retrieves a list of films from the database that have an age limit less than or equal to specified age.

### Parameters:

- `genre`: The genre of the films to retrieve. This should be one of the values defined in the `Genre` enum.

### Returns:

A list of `Film` objects that have an age limit less than or equal to specified age.

## Method: findByGenre(Genre genre)

This method retrieves a list of films from the database that match the specified genre.

### Parameters:

- `genre`: The genre of the films to retrieve. This should be one of the values defined in the `Genre` enum.

### Returns:

A list of `Film` objects that match the specified genre.


## Method: findByGenreAndAgeLimit(Genre genre, Integer age)

This method retrieves a list of films from the database that match the specified genre and have an age limit less than or equal to the specified age.

### Parameters:

- `genre`: The genre of the films to retrieve. This should be one of the values defined in the `Genre` enum.
- `age`: The maximum age limit of the films to retrieve. This should be a non-negative integer.

### Returns:

A list of `Film` objects that match the specified genre and have an age limit less than or equal to the specified age limit.

## Method: addFilmToWatchHistory(Film film)

This method adds a film to the watch history in the database.

### Parameters:

- `film`: The `Film` object to add to the watch history. This should contain the following properties:
    - `id`: The ID of the film.
    - `title`: The title of the film.
    - `directors`: The director(s) of the film.
    - `genres`: A list of genres that the film belongs to.
    - `age_limit`: The age limit of the film.
    - `duration`: The duration of the film in minutes.

## Method: recommendFilm()

This method retrieves the recommended film from the database based on the watch history.

### Returns:

A `Film` object that is chosen by the algorithm.

## Film Record

A `Film` record represents a film. It has the following fields:

- `id`: An `Integer` that represents the unique identifier of the film.
- `title`: A `String` that represents the title of the film. This field is annotated with `@NotEmpty`, which means it cannot be null or empty.
- `director`: A `String` that represents the director of the film.
- `genres`: A `List<Genre>` that represents the genres of the film.
- `ageLimit`: An `Integer` that represents the age limit for the film. This field is annotated with `@PositiveOrZero`, which means it cannot be negative.
- `duration`: An `Integer` that represents the duration of the film in minutes. This field is annotated with `@Positive`, which means it must be a positive integer.

---

# SessionController

## GET /api/sessions

Returns all sessions.

### Responses:

- `200 OK`: The request was successful. The response body contains a list of all sessions.

## GET /api/sessions/{id}

Returns the session with the specified ID.

### Path Parameters:

- `id`: The id of the session to retrieve.

### Responses:

- `200 OK`: The request was successful. The response body contains the session with the specified id.
- `400 Bad Request`: The request was malformed. This could be due the session with id not existing.

## GET /api/sessions/date/{date}

Returns all sessions on the specified date.

### Path Parameters:

- `date`: The date of the sessions to retrieve.

### Responses:

- `200 OK`: The request was successful. The response body contains the sessions on the specified date.

## GET /api/sessions/language/{language}

Returns all sessions in the specified language.

### Path Parameters:

- `language`: The language of the sessions to retrieve, this should be one of the values defined in the Language enum.

### Responses:

- `200 OK`: The request was successful. The response body contains the sessions with the specified language.
- `400 Bad Request`: The request was malformed. This could be due an invalid language.

## GET /api/sessions/date/{date}/language/{language}

Returns all sessions on the specified date and in the specified language.

### Path Parameters:

- `date`: The date of the sessions to retrieve.
- `language`: The language of the sessions to retrieve, this should be one of the values defined in the Language enum.

### Responses:

- `200 OK`: The request was successful. The response body contains the sessions with the specified language and date.
- `400 Bad Request`: The request was malformed. This could be due an invalid language or date.

## GET /api/sessions/{id}/recommend-seats

Finds the best seats for the specified number of seats in the session with the specified ID.

### Parameters:

- `numSeats`: The number of seats to recommend.

### Responses:

- `200 OK`: The request was successful. The response body contains the row and column (`int[]`) of the left most seat, start row and column count for 0. Returns `{-1, -1}` when no possible seating is found

# SessionRepository

## Method: findAll()

Returns all sessions.

## Method: findById(Integer id)

Returns the session with the specified ID.

## Method: findByTimeBetween(LocalDateTime start, LocalDateTime end)

Returns all sessions that start and end between the specified times.

## Method: findByLanguage(Language language)

Returns all sessions in the specified language.

## Method: findByDateAndLanguage(LocalDateTime start, LocalDateTime end, Language language)

Returns all sessions that start and end between the specified times and are in the specified language.

## Session Record

A `Session` record represents a film screening session. It has the following fields:

- `id`: An `Integer` that represents the unique identifier of the session.
- `time`: A `LocalDateTime` that represents the start time of the session.
- `isSeatFree`: A 2D `boolean` array that represents the seating arrangement of the session. Each element in the array corresponds to a seat, where `true` indicates that the seat is free, and `false` indicates that the seat is taken.
- `film`: A `Film` object that represents the film being screened in the session.
- `language`: A `Language` enum that represents the language in which the film is screened.

# SeatsManager

## Method: generateSeating(int rows, int seatsPerRow)

Generates random free seats. Returns a `boolean[][]` of random values.

## Method: findBestSeats(boolean[][] isSeatFree, int n)

Finds the best location of `n` number of free seats next to one another. Returns the row and column of the left most seats.

