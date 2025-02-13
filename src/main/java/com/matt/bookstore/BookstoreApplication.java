package com.matt.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		BookstoreApplication bookstoreApplication = new BookstoreApplication();
		bookstoreApplication.connect();
		SpringApplication.run(BookstoreApplication.class, args);
	}

	public Connection connect() {
		Connection coon = null;
		try {
			// Retrieve the database password from the environment variable
			String dbPassword = System.getenv("DB_PASSWORD");
			if (dbPassword == null) {
				throw new RuntimeException("Environment variable DB_PASSWORD is not set.");
			}

			// Establish the connection using the password from the environment variable
			coon = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore",
					"postgres",
					dbPassword);
			System.out.println("Connected to server successfully");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return coon;
	}

	@Autowired // injecting beans at runtime
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {

//      SimpleJdbcInsert insertBook = new SimpleJdbcInsert(jdbcTemplate);
//		insertBook.withTableName("autore");
//		Autore autore = new  Autore(1,"Agatha Christie");
//		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(autore);
//		insertBook.execute(parameterSource);
//
//		insertBook.withTableName("genere");
//		Genere genere = new Genere(1,"Giallo");
//		BeanPropertySqlParameterSource parameterSource2 = new BeanPropertySqlParameterSource(genere);
//		insertBook.execute(parameterSource2);

	}

	//GET
	@GetMapping(value = "/book")
	public List<Book> getAllBooks() {
		String sql = "SELECT * FROM books";
		List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
		//return libreriaService.findAllBooks();
		return  books;
	}

	//GET
	@GetMapping(value = "/author")
	public List<Author> getAllAuthors() {
		String sql = "SELECT * FROM authors";
		List<Author> authors = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Author.class));
		//return libreriaService.findAllBooks();
		return  authors;
	}

	//GET
	@GetMapping(value = "/genre")
	public List<Genre> getAllGenres() {
		String sql = "SELECT * FROM genres";
		List<Genre> genres = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Genre.class));
		//return libreriaService.findAllBooks();
		return  genres;
	}


	//GET
	@GetMapping(value = "/book/{id}")
	public Book getBookByID(@PathVariable int id) {
		String sql = "SELECT * FROM books WHERE id = ?";
		List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class), id);
		return  books.get(0);
	}

	//GET
	@GetMapping(value = "/author/{id}")
	public Author getAuthorByID(@PathVariable int id) {
		String sql = "SELECT * FROM authors WHERE id = ?";
		List<Author> authors = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Author.class), id);
		return  authors.get(0);
	}

	//GET
	@GetMapping(value = "/bookByAuthor/{id}")
	public List<Book> getBookByAuthorID(@PathVariable int id) {
		String sql = "SELECT * FROM books WHERE author = ?";

		List<Book> books = jdbcTemplate.query(
				sql,
				BeanPropertyRowMapper.newInstance(Book.class),
				getAuthorByID(id).getAuthor()
		);

		return  books;
	}

	//GET
    @GetMapping(value = "/year/{year}")
    public Author getAuthorByBirth(@PathVariable int year) {

        String sql = "SELECT * FROM authors WHERE birth_year = ?";
        
        List<Author> authors = jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(Author.class),
				year
        );
        return  authors.get(0);
    }

	//GET
	@GetMapping(value = "/bookByBirth/{year}")
	public List<Book> getBookByBirth(@PathVariable int year) {

		String sql2 = "SELECT b.id , b.title , b.author , b.page_number, b.publication_year, b.genre FROM books b "+ 
				"INNER JOIN authors a ON b.author = a.author WHERE a.birth_year >= ? " +
				"ORDER BY b.publication_year DESC";

		List<Book> books = jdbcTemplate.query(
				sql2,
				BeanPropertyRowMapper.newInstance(Book.class),
				year
		);

		return  books;
	}

	//GET
	@GetMapping(value = "/genre/{id}")
	public Genre getGenreByID(@PathVariable int id) {
		String sql = "SELECT * FROM genres WHERE id = ?";
		List<Genre> genres = jdbcTemplate.query(
			sql, 
			BeanPropertyRowMapper.newInstance(Genre.class),
			id);
		return  genres.get(0);
	}

	//GET
	@GetMapping(value = "/booksByGenreID/{id}")
	public List<Book> getBookByGenreID(@PathVariable int id) {

		String sql = "SELECT * FROM books WHERE genre = '"+ getGenreByID(id).getGenre() + "'";
		List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
		return  books;
	}


	//POST, value = "/book/{titolo}-{autore}-{genere}")

    @PostMapping(value = "/book/{data}")
        public Book postBook(@PathVariable String data) {
            String[] parts = data.split("-");
            String title = parts[0];
            String author = parts[1];
            String genre = parts[2];
			int page_number = Integer.parseInt(parts[3]);
    		int publication_year = Integer.parseInt(parts[4]);

		Map<String,Object> params = new HashMap<>();
		params.put("title",title);
		params.put("author",author);
		params.put("genre",genre);
		params.put("page_number", page_number);
    	params.put("publication_year", publication_year);

        SimpleJdbcInsert insertBook = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("books")
				.usingColumns("title","author","genre","page_number", "publication_year")
				.usingGeneratedKeyColumns("id");

        return getBookByID( (int) insertBook.executeAndReturnKey(params));
    }

	//POST
	@PostMapping(value = "/author/{author}-{birth_year}")
	public Author postAuthor(@PathVariable String author,
							 @PathVariable int birth_year) {

		Map<String,Object> params = new HashMap<>();
		params.put("author",author);
		params.put("birth_year",birth_year);

		SimpleJdbcInsert insertAutore = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("authors")
				.usingColumns("author","birth_year")
				.usingGeneratedKeyColumns("id");

		return getAuthorByID( (int) insertAutore.executeAndReturnKey(params));
	}

	//POST
	@PostMapping(value = "/genre/{genre}")
	public Genre postGenre(@PathVariable String genre) {

		Map<String,Object> params = new HashMap<>();
		params.put("genre",genre);

		SimpleJdbcInsert insertGenre = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("genres")
				.usingColumns("genre").usingGeneratedKeyColumns("id");

		return getGenreByID( (int) insertGenre.executeAndReturnKey(params));
	}
		

}
