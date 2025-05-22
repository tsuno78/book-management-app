package jp.co.tsuno.data.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
	
	/**
	 * お気に入りの書籍を取得
	 * @return お気に入りの書籍情報リスト
	 */
	List<Book> findByIsFavoriteTrue(Sort sort);
	
	/**
	 * お気に入りフラグの更新
	 * @param id 書籍ID
	 * @param isFavorite お気に入り情報
	 */
	@Transactional
	@Modifying
	@Query("UPDATE books b SET b.isFavorite = :isFavorite WHERE b.id = :id")
	void updateFavoriteStatus(@Param("id") Integer id, @Param("isFavorite") boolean isFavorite);
	
	
}
