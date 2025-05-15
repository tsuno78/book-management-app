package jp.co.tsuno.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jp.co.tsuno.data.entity.Book;

public interface BookDao extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
	/**
	 * 全ての条件に一致する書籍を検索
	 * @param title 書籍のタイトル
	 * @param author 著者
	 * @param publisher 出版社
	 * @return 書籍情報リスト
	 */
	List<Book> findByTitleContainingAndAuthorContainingAndPublisherContaining(String title, String author, String publisher);
}
