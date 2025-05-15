package jp.co.tsuno.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 書籍情報エンティティ
 */
@Entity(name = "books")
@Table(name = "books")
@Getter
@Setter
public class Book {
	/**
	 * 書籍ID
	 */
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "books_id_seq", sequenceName = "books_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "books_id_seq", strategy = GenerationType.SEQUENCE)
	private Integer id;
	/**
	 * 書籍のタイトル
	 */
	@Column(name = "title")
	private String title;
	
	/**
	 * 書籍の著者
	 */
	@Column(name = "author")
	private String author;
	/**
	 * 出版年
	 */
	@Column(name = "publication_year")
	private Integer publishedYear;
	/**
	 * 書籍の感想
	 */
	@Column(name = "feedback",columnDefinition = "TEXT")
	private String review;
	
	/**
	 *出版社
	 */
	@Column(name = "publisher")
	private String publisher;
}
