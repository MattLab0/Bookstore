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
	public List<Author> getAllAutori() {
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
		String sql = "SELECT * FROM books WHERE id = " + id;
		List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
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

        String sql = "SELECT * FROM authors WHERE birth_year =" + year;
        //List<Autore> autori = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Autore.class));

        List<Author> authors = jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(Author.class)
        );
        return  authors.get(0);
    }

	//GET
	@GetMapping(value = "/bookByBirth/{year}")
	public List<Book> getBookByBirth(@PathVariable int year) {

		String sql2 = "SELECT b.id , b.title , b.author , b.genre FROM books b INNER JOIN authors a on b.author = a.author WHERE a.birth_year >=" + year;

		String sql = "SELECT * FROM books WHERE autore = ?";

		List<Book> books = jdbcTemplate.query(
				sql2,
				BeanPropertyRowMapper.newInstance(Book.class)
		);

		return  books;
	}

	//GET
	@GetMapping(value = "/genre/{id}")
	public Genre getGenereByID(@PathVariable int id) {
		String sql = "SELECT * FROM genres WHERE id = " + id;
		List<Genre> genres = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Genre.class));
		return  genres.get(0);
	}


//	//POST
//    @PostMapping(value = "/Book/{titolo}-{autore}-{genere}")
//    public Book postBook(@PathVariable String autore,
//						  @PathVariable String titolo,
//						  @PathVariable String genere) {
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
	@PostMapping(value = "/author/{author}-{birth}")
	public Author postAuthor(@PathVariable String author,
							 @PathVariable int birth) {

		Map<String,Object> params = new HashMap<>();
		params.put("author",author);
		params.put("birth",birth);

		SimpleJdbcInsert insertAutore = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("author")
				.usingColumns("author","birth")
				.usingGeneratedKeyColumns("id");

		return getAuthorByID( (int) insertAutore.executeAndReturnKey(params));
	}

	//POST
	@PostMapping(value = "/genre/{genre}")
	public Genre postGenre(@PathVariable String genre) {

		Map<String,Object> params = new HashMap<>();
		params.put("genere",genre);

		SimpleJdbcInsert insertGenre = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("genre")
				.usingColumns("genre").usingGeneratedKeyColumns("id");

		return getGenreByID( (int) insertGenre.executeAndReturnKey(params));
	}
		
	public Genre getGenreByID(int executeAndReturnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getGenreByID'");
	}
		
	//GET
	@GetMapping(value = "/booksByGenreID/{id}")
	public List<Book> getBookByGenreID(@PathVariable int id) {

		String sql = "SELECT * FROM books WHERE genere = '"+ getGenereByID(id).getGenre() + "'";
		List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
		return  books;
	}

}
