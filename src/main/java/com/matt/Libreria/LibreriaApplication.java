package com.matt.Libreria;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SpringBootApplication
public class LibreriaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		LibreriaApplication libreriaApplication = new LibreriaApplication();
		libreriaApplication.connect();
		SpringApplication.run(LibreriaApplication.class, args);
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
	@GetMapping(value = "/libro")
	public List<Libro> getAllBooks() {
		String sql = "SELECT * FROM libro";
		List<Libro> libri = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Libro.class));
		//return libreriaService.findAllBooks();
		return  libri;
	}

	//GET
	@GetMapping(value = "/autore")
	public List<Autore> getAllAutori() {
		String sql = "SELECT * FROM autore";
		List<Autore> autori = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Autore.class));
		//return libreriaService.findAllBooks();
		return  autori;
	}

	//GET
	@GetMapping(value = "/genere")
	public List<Genere> getAllGeneri() {
		String sql = "SELECT * FROM genere";
		List<Genere> generi = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Genere.class));
		//return libreriaService.findAllBooks();
		return  generi;
	}


	//GET
	@GetMapping(value = "/libro/{id}")
	public Libro getBookByID(@PathVariable int id) {

		String sql = "SELECT * FROM libro WHERE id = " + id;
		List<Libro> libri = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Libro.class));
		return  libri.get(0);
	}

	//GET
	@GetMapping(value = "/autore/{id}")
	public Autore getAutoreByID(@PathVariable int id) {

		String sql = "SELECT * FROM autore WHERE id = ?";
		List<Autore> autori = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Autore.class), id);
		return  autori.get(0);
	}
	//GET
	@GetMapping(value = "/libriPerAutore/{id}")
	public List<Libro> getBookByAutoreID(@PathVariable int id) {

		String sql = "SELECT * FROM libro WHERE autore = ?";

		List<Libro> libri = jdbcTemplate.query(
				sql,
				BeanPropertyRowMapper.newInstance(Libro.class),
				getAutoreByID(id).getAutore()
		);

		return  libri;
	}
    @GetMapping(value = "/anno/{anno}")
    public Autore getAutoreByNascita(@PathVariable int anno) {

        String sql = "SELECT * FROM autore WHERE anno_nascita =" + anno;
        //List<Autore> autori = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Autore.class));

        List<Autore> autori = jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(Autore.class)
        );
        return  autori.get(0);
    }

	//GET
	@GetMapping(value = "/libriPerNascitaAutore/{anno}")
	public List<Libro> getBookByNascita(@PathVariable int anno) {

		String sql2 = "select b.id , b.titolo , b.autore , b.genere from libro b inner join autore a on b.autore = a.autore where a.anno_nascita >=" + anno;

		String sql = "SELECT * FROM libri WHERE autore = ?";

		List<Libro> libri = jdbcTemplate.query(
				sql2,
				BeanPropertyRowMapper.newInstance(Libro.class)
		);

		return  libri;
	}

	//GET
	@GetMapping(value = "/genere/{id}")
	public Genere getGenereByID(@PathVariable int id) {
		String sql = "SELECT * FROM genere WHERE id = " + id;
		List<Genere> generi = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Genere.class));
		return  generi.get(0);
	}


//	//POST
//    @PostMapping(value = "/libro/{titolo}-{autore}-{genere}")
//    public Libro postBook(@PathVariable String autore,
//						  @PathVariable String titolo,
//						  @PathVariable String genere) {
    @PostMapping(value = "/libro/{data}")
        public Libro postBook(@PathVariable String data) {
            String[] parts = data.split("-");
            String titolo = parts[0];
            String autore = parts[1];
            String genere = parts[2];
		Map<String,Object> params = new HashMap<>();
		params.put("titolo",titolo);
		params.put("autore",autore);
		params.put("genere",genere);

        SimpleJdbcInsert insertBook = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("libro")
				.usingColumns("titolo","autore","genere")
				.usingGeneratedKeyColumns("id");

        return getBookByID( (int) insertBook.executeAndReturnKey(params));
    }

	//POST
	@PostMapping(value = "/autore/{autore}-{anno_nascita}")
	public Autore postAutore(@PathVariable String autore,
							 @PathVariable int anno_nascita) {

		Map<String,Object> params = new HashMap<>();
		params.put("autore",autore);
		params.put("anno_nascita",anno_nascita);

		SimpleJdbcInsert insertAutore = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("autore")
				.usingColumns("autore","anno_nascita")
				.usingGeneratedKeyColumns("id");

		return getAutoreByID( (int) insertAutore.executeAndReturnKey(params));
	}

	//POST
	@PostMapping(value = "/genere/{genere}")
	public Genere postGenere(@PathVariable String genere) {

		Map<String,Object> params = new HashMap<>();
		params.put("genere",genere);

		SimpleJdbcInsert insertGenere = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("genere")
				.usingColumns("genere").usingGeneratedKeyColumns("id");

		return getGenereByID( (int) insertGenere.executeAndReturnKey(params));
	}

	//GET
	@GetMapping(value = "/libriPerGenere/{id}")
	public List<Libro> getBookByGenereID(@PathVariable int id) {

		String sql = "SELECT * FROM libro WHERE genere = '"+ getGenereByID(id).getGenere() + "'";
		List<Libro> libri = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Libro.class));
		return  libri;
	}

}
