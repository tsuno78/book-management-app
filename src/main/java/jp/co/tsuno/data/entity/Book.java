package jp.co.tsuno.data.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	
	/**
	 * お気に入り
	 */
	@Column(name = "is_favorite")
	private boolean isFavorite;
	
	/*
	 * 読書ステータス
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "reading_status")
	private ReadingStatus status;
	
	/**
	 * 読書開始日
	 */
	@Column(name = "start_date")
	private LocalDate startDate;
	
	/**
	 * 読書完了日
	 */
	@Column(name = "finish_date")
	private LocalDate finishDate;
}
