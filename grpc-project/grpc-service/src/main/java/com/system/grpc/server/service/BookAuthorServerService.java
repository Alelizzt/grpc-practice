package com.system.grpc.server.service;

import java.util.ArrayList;
import java.util.List;

import com.system.grpc.core.generated.Author;
import com.system.grpc.core.generated.Book;
import com.system.grpc.core.generated.BookAuthorServiceGrpc.BookAuthorServiceImplBase;
import com.system.grpc.server.data.DataHandler;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BookAuthorServerService extends BookAuthorServiceImplBase {
	
	@Override
	public void getAuthor(Author request, StreamObserver<Author> responseObserver) {
		DataHandler.getAuthorsFromDataHandler()
			.stream()
			.filter(author -> author.getAuthorId() == request.getAuthorId())
			.findFirst()
			.ifPresent(responseObserver::onNext);
		responseObserver.onCompleted();
	}
	
	
	@Override
	public void getBooksByAuthor(Author request, StreamObserver<Book> responseObserver) {
		DataHandler.getBooksFromDataHandler()
        	.stream()
        	.filter(book -> book.getAuthorId() == request.getAuthorId())
        	.forEach(responseObserver::onNext);
		responseObserver.onCompleted();
	}

	
	@Override
	public StreamObserver<Book> getExpensiveBook(StreamObserver<Book> responseObserver) {
		return new StreamObserver<Book>() {
			Book expensiveBook = null;
			float priceTrack = 0;
			
			@Override
			public void onNext(Book book) {
				if (book.getPrice() > priceTrack) {
                    priceTrack = book.getPrice();
                    expensiveBook = book;
                }
			}
			
			@Override
			public void onError(Throwable throwable) {
				responseObserver.onError(throwable);
			}
			
			@Override
			public void onCompleted() {
				responseObserver.onNext(expensiveBook);
				responseObserver.onCompleted();
			}
		};
	}

	
	@Override
	public StreamObserver<Book> getBooksByGender(StreamObserver<Book> responseObserver) {
		return new StreamObserver<Book>() {
			List<Book> bookList = new ArrayList<>();
			
			@Override
			public void onNext(Book book) {
				DataHandler.getBooksFromDataHandler()
					.stream()
					.filter(bookFromDb -> bookFromDb.getAuthorId() == book.getAuthorId())
					.forEach(bookList::add);
			}
			
			@Override
			public void onError(Throwable throwable) {
				responseObserver.onError(throwable);				
			}
			
			@Override
			public void onCompleted() {
				bookList.forEach(responseObserver::onNext);
				responseObserver.onCompleted();
			}
		};
	}

}
