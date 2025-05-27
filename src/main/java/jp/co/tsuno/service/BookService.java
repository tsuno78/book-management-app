package jp.co.tsuno.service;

import static jp.co.tsuno.data.spec.BookSpecifications.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jp.co.tsuno.data.dao.BookDao;
import jp.co.tsuno.data.entity.Book;
import jp.co.tsuno.data.entity.ReadingStatus;
import jp.co.tsuno.form.BookForm;

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
	 * @return 書籍情報
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

		return this.dao.findAll(spec, Sort.by(Sort.Order.asc("id")));
	}

	/**
	 * 登録・更新
	 * @param form フォーム入力内容
	 * @return
	 */
	public void save(BookForm form) {
		//Book型のentityを宣言
		Book entity;
		
		//新規登録の場合のみcreatedAtを設定
		if(form.getBookId() == null) {
			//フォームをエンティティに変換
			entity = formToEntity(form);
		} else {
			//更新時
			Optional<Book> optExisting = this.dao.findById(form.getBookId());
			if(optExisting.isPresent()) {
				Book exisiting = optExisting.get();
				//既存エンティティを渡してformToEntityで変換
				entity = formToEntity(form, exisiting);
			} else {
				//万が一既存データがなければ新規として扱う
				entity = formToEntity(form);
			}	
		}
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
	 * フォーム→エンティティ変換(新規登録用)
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
		
		//追加: ステータスをEnumに変換してセット
		if(form.getStatus() != null && !form.getStatus().isEmpty()) {
			entity.setStatus(ReadingStatus.valueOf(form.getStatus()));
		} else {
			entity.setStatus(ReadingStatus.UNREAD);
		}
		
		return entity;
	}
	
	/**
	 * フォーム→エンティティ変換(更新用)
	 * @param form 書籍情報フォーム
	 * @param existing 既存のエンティティ
	 * @return 書籍情報エンティティ
	 */
	private Book formToEntity(BookForm form, Book existing) {
		//変換データ格納用エンティティ
		Book entity = new Book();
		//全プロパティコピー
		BeanUtils.copyProperties(form, entity);
		//bookIdのみプロパティ名が異なるため個別セット
		entity.setId(form.getBookId());
		
		if(form.getStatus() != null && !form.getStatus().isEmpty()) {
			entity.setStatus(ReadingStatus.valueOf(form.getStatus()));
		} else {
			//フォームのstatusが空なら既存のstatusをセット
			entity.setStatus(existing.getStatus());
		}
		
		return entity;
	}
	

	/**
	 * お気に入り更新機能
	 * @param id 書籍ID
	 * @param isFavorite お気に入り
	 */
	public void updateFavoriteStatus(Integer id, boolean isFavorite) {
		this.dao.updateFavoriteStatus(id, isFavorite);
	}
	
	/**
	 * お気に入り取得機能
	 * @return 書籍情報リスト
	 */
	public List<Book> getFavoriteBooks(){
		return dao.findByIsFavoriteTrue(Sort.by(Direction.ASC,"id"));
	}
}
