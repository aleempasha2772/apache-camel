package com.example.apache_camel.Routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.example.apache_camel.Entity.Book;
import com.example.apache_camel.Service.BookService;



@Component
public class BookRestRoute extends RouteBuilder{
	
	private final BookService bookService;

    public BookRestRoute(BookService bookService) {
        this.bookService = bookService;
    }

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		restConfiguration()
	        .component("platform-http")
	        .contextPath("/rest")
	        .bindingMode(RestBindingMode.json);
		
		
		rest("/book")
		  .post()
		    .type(Book.class).outType(Book.class)
		    .to("direct:book-save");

		from("direct:book-save")
		  .bean(bookService, "save");
		
		// GET /book
		rest("/book")
		  .get()
		    .outType(Book[].class)
		    .to("direct:book-findAll");

		from("direct:book-findAll").bean(bookService, "findAll");

		// GET /book/{name}
		rest("/book")
		  .get("/{name}").outType(Book.class)
		    .to("direct:book-findByName");

		from("direct:book-findByName")
		  .bean(bookService, "findByName(${header.name})")
		  .choice()
		    .when(body().isNull())
		      .setHeader("CamelHttpResponseCode", constant(404))
		      .setBody().simple("{\"message\":\"Book not found\"}")
		  .end();

		// DELETE /book/{id}
		rest("/book")
		  .delete("/{id}")
		    .to("direct:book-deleteById");

		from("direct:book-deleteById")
		  .bean(bookService, "deleteById(${header.id})")
		  .setBody().simple("{\"deleted\": true}");

		
	}

}
