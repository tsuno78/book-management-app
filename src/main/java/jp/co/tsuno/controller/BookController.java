package jp.co.tsuno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.tsuno.data.entity.Book;
import jp.co.tsuno.form.BookForm;
import jp.co.tsuno.form.BookForm.UpdateDeleteSelectGroup;
import jp.co.tsuno.service.BookService;

/**
 * 書籍管理アプリコントローラー
 */
@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	/**
	 * ログインページ表示
	 * @param mav
	 * @return
	 */
	@GetMapping("/login")
	public ModelAndView login(ModelAndView mav) {
		//ログインページへ移動
		mav.setViewName(BookPageEnum.LOGIN.getPath());
		return mav;
	}

	/**
	 * 初期表示
	 * @param mav
	 * @return
	 */
	@GetMapping("/home")
	public ModelAndView index(ModelAndView mav) {
		//書籍情報全件検索結果をセッション情報登録
		mav.addObject("bookList", this.bookService.getBookList());
		//DBアウトプットページへ遷移
		mav.setViewName(BookPageEnum.OUTPUT.getPath());
		return mav;
	}

	/**
	 * 書籍情報検索結果表示
	 * @param mav
	 * @param title
	 * @param author
	 * @param publisher
	 * @return 
	 */
	@GetMapping("/search")
	public ModelAndView searchBooks(
			ModelAndView mav,
			@RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "author", required = false) String author,
			@RequestParam(name = "publisher", required = false) String publisher) {
		//全て空かどうかチェック
		boolean isAllEnpty = (title == null || title.isEmpty()) &&
				(author == null || author.isEmpty()) &&
				(publisher == null || publisher.isEmpty());

		//書籍の情報を格納するためのリストを作成
		List<Book> bookList;

		if (isAllEnpty) {
			//全件検索
			bookList = bookService.getBookList();
		} else {
			//条件検索
			bookList = bookService.getBookListByMultiKeyword(title, author, publisher);
		}

		// 検索ワードを返す
		mav.addObject("title", title);
		mav.addObject("author", author);
		mav.addObject("publisher", publisher);

		//複数条件で検索した結果を取得
		mav.addObject("bookList", bookList);

		//出力ページへ遷移
		mav.setViewName(BookPageEnum.OUTPUT.getPath());
		return mav;
	}

	/**
	 * インプットページ表示
	 * @param mav
	 * @reutrn
	 */
	@GetMapping("/input")
	public ModelAndView input(ModelAndView mav) {
		//インプットページ表示
		mav.setViewName(BookPageEnum.INPUT.getPath());
		return mav;
	}

	/**
	 * アップデートページ表示
	 * @param mav
	 * @param bookForm 更新対象書籍情報
	 * @param br
	 * @return
	 */
	@PostMapping("/update")
	public ModelAndView update(ModelAndView mav,
			@Validated(UpdateDeleteSelectGroup.class) BookForm bookForm, BindingResult br) {
		//入力エラーありの場合
		if (br.hasErrors()) {
			mav.setViewName(BookPageEnum.ERROR.getPath());
		} else {
			//更新対象の社員情報を取得してセッション情報登録
			mav.addObject("book", this.bookService.getBookById(bookForm.getBookId()));
			//アップデートページへ遷移
			mav.setViewName(BookPageEnum.UPDATE.getPath());
		}
		return mav;
	}

	/**
	 * 登録・更新内容保存
	 * @param mav
	 * @param bookForm 書籍情報の登録・更新内容
	 * @param br
	 * @return
	 */
	@PostMapping("/save")
	public ModelAndView save(ModelAndView mav, @Validated BookForm bookForm, BindingResult br) {
		//入力エラーありの場合
		if (br.hasErrors()) {
			//エラーページへ遷移
			mav.setViewName(BookPageEnum.ERROR.getPath());
		} else {
			//フォームに入力したデータを新規登録
			this.bookService.save(bookForm);
			//登録後の社員情報一覧をセッション情報登録
			mav.addObject("bookList", this.bookService.getBookList());
			//DBアウトプットページへ遷移
			mav.setViewName(BookPageEnum.OUTPUT.getPath());
		}
		return mav;
	}

	/**
	 * 削除
	 * @param mav
	 * @param bookForm 書籍情報
	 * @param br
	 * @return
	 */
	@PostMapping("/delete")
	public ModelAndView delete(ModelAndView mav, @Validated(UpdateDeleteSelectGroup.class) BookForm bookForm,
			BindingResult br) {
		//入力エラーありの場合
		if (br.hasErrors()) {
			//エラーページ遷移
			mav.setViewName(BookPageEnum.ERROR.getPath());
		} else {
			//書籍ID指定で削除
			this.bookService.delete(bookForm.getBookId());
			//削除後の社員情報一覧をセッション情報登録
			mav.addObject("bookList", this.bookService.getBookList());
			//DBアウトプットページへ遷移
			mav.setViewName(BookPageEnum.OUTPUT.getPath());
		}
		return mav;
	}

	/**
	 * お気に入り更新機能
	 * @param id 書籍id
	 * @param isFavorite お気に入り
	 * @param mav
	 * @return mav
	 */
	@PostMapping("/favorite")
	public ModelAndView toggleFavorite(@RequestParam("id") Integer id, @RequestParam("isFavorite") boolean isFavorite,
			ModelAndView mav) {

		//お気に入り状態を更新
		this.bookService.updateFavoriteStatus(id, isFavorite);

		//最新の一覧を表示
		mav.addObject("bookList", this.bookService.getBookList());
		mav.setViewName(BookPageEnum.OUTPUT.getPath());

		return mav;
	}

	/**
	 * お気に入り表示機能
	 * @param mav
	 * @return mav
	 */
	@GetMapping("/favorites")
	public ModelAndView showFavorites(ModelAndView mav) {
		//お気に入りの書籍リストを取得
		List<Book> favoriteBooks = this.bookService.getFavoriteBooks();

		//お気に入りの書籍情報をセッション登録
		mav.addObject("bookList", favoriteBooks);

		//DBアウトプットページへ遷移
		mav.setViewName(BookPageEnum.OUTPUT.getPath());

		return mav;
	}
}
