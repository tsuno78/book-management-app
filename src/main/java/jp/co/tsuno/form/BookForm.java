package jp.co.tsuno.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jp.co.tsuno.validation.FinishDateRequiredIfCompleted;
import lombok.Getter;
import lombok.Setter;

/**
 * 書籍情報フォーム
 */
@Getter
@Setter
@FinishDateRequiredIfCompleted
public class BookForm {
	
	/**
	 * バリデーション用グループ
	 */
	public interface UpdateDeleteSelectGroup{}
	
	/**
	 * 書籍ID
	 */
	@NotNull(groups = UpdateDeleteSelectGroup.class)
	private Integer bookId;
	
	/**
	 * 書籍のタイトル
	 */
	@NotBlank
	private String title;
	
	/**
	 * 書籍の著者
	 */
	private String author;
	
	/**
	 * 出版年
	 */
	private Integer publishedYear;
	
	/**
	 * 書籍の感想
	 */
	private String review;
	
	/**
	 * 書籍の出版社
	 */
	private String publisher;
	
	/**
	 * 読書ステータス
	 */
	private String status;
	
	/**
	 * 読書開始日
	 */
	private LocalDate startDate;
	
	/**
	 * 読書完了日
	 */
	private LocalDate finishDate;
}
