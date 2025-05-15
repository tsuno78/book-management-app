package jp.co.nagatake.service;

import static jp.co.nagatake.data.spec.BookSpecifications.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jp.co.nagatake.data.dao.BookDao;
import jp.co.nagatake.data.entity.Book;
import jp.co.nagatake.form.BookForm;

/**
 * 書籍情報操作ビジネスロジッククラス
 */
@Service
public class BookService {
	@Autowired
	private BookDao dao;

	/**
	 * 書籍情報一覧取得
	 * @param
	 * @return 書籍情報リスト
	 */
	public List<Book> getBookList() {
		//　全件検索結果を返す(書籍ID昇順ソート）
		return this.dao.findAll(Sort.by(Direction.ASC, "id"));
	}

	/**
	 * 社員情報取得_ID検索
	 * @param id 書籍id
	 * @returm 書籍情報
	 */
	public Book getBookById(Integer id) {
		//書籍情報格納用エンティティ
		Book result = new Book();
		//ID指定検索
		Optional<Book> optBook = this.dao.findById(id);
		//検索結果が取得できた場合
		if (optBook.isPresent()) {
			//検索結果を書籍情報エンティティへ
			result = optBook.get();
		}
		return result;
	}

	/**
	 * 社員情報取得　複数項目検索
	 * @param title　書籍タイトル
	 * @param author 著者
	 * @param publisher 出版社
	 * @return 書籍情報
	 */
	public List<Book> getBookListByMultiKeyword(String title, String author, String publisher) {
		
		Specification<Book> spec = Specification.where(titleContains(title))
				.and(authorContains(author))
				.and(publisherContains(publisher));
		
		return this.dao.findAll(spec,Sort.by(Sort.Order.asc("id")));
	}

	/**
	 * 登録・更新
	 * @param form フォーム入力内容
	 * @return
	 */
	public void save(BookForm form) {
		//フォームをエンティティに変換
		Book entity = formToEntity(form);
		//エンティティをDB保存(即時反映)
		entity = this.dao.save(entity);
	}

	/**
	 * 書籍情報削除
	 * @param id 書籍id
	 * @return
	 */
	public void delete(Integer id) {
		//書籍ID指定で削除
		this.dao.deleteById(id);
	}

	/**
	 * フォーム→エンティティ変換
	 * @param form 書籍情報フォーム
	 * @return 書籍情報エンティティ
	 */
	private Book formToEntity(BookForm form) {
		//変換データ格納用エンティティ
		Book entity = new Book();
		//全プロパティコピー
		BeanUtils.copyProperties(form, entity);
		//bookIdのみプロパティ名が異なるため個別セット
		entity.setId(form.getBookId());
		return entity;
	}
}
