package jp.co.tsuno.data.spec;

import org.springframework.data.jpa.domain.Specification;

import jp.co.tsuno.data.entity.Book;

/**
 * 書籍検索用のSpecificationクラス
 * 各フィールド(タイトル、著者、出版社)に対する部分一致条件を動的に生成する
 */
public class BookSpecifications {
	
	/**
	 * タイトルが指定されていれば、「title LIKE %タイトル%」という条件を生成
     * 空やnullなら条件を無視（＝検索に影響しない）
	 * @param title 検索キーワード
	 * @return Specification<Book>
	 */
	public static Specification<Book> titleContains(String title){
		return (root, query, cb) -> title == null || title.isEmpty() ? null : cb.like(root.get("title"), "%" + title + "%");
	}
	/**
     * 著者が指定されていれば、「author LIKE %著者%」という条件を生成。
     * 空やnullなら条件を無視。
     * @param author 検索キーワード（著者）
     * @return Specification<Book>
     */
	public static Specification<Book> authorContains(String author){
		return (root, query, cb) -> author == null || author.isEmpty() ? null : cb.like(root.get("author"), "%" + author + "%");
	}
	
	 /**
     * 出版社が指定されていれば、「publisher LIKE %出版社%」という条件を生成。
     * 空やnullなら条件を無視。
     * @param publisher 検索キーワード（出版社）
     * @return Specification<Book>
     */
	public static Specification<Book> publisherContains(String publisher){
		return (root, query, cb) -> publisher == null || publisher.isEmpty() ? null : cb.like(root.get("publisher"), "%" + publisher + "%");
	}
}
