package jp.co.tsuno.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jp.co.tsuno.data.entity.ReadingStatus;
import jp.co.tsuno.form.BookForm;

public class FinishDateRequiredIfCompletedValidator implements ConstraintValidator<FinishDateRequiredIfCompleted, BookForm> {

	@Override
	public boolean isValid(BookForm form, ConstraintValidatorContext context) {
		if(form == null) return true;
		
		System.out.println("status = [" + form.getStatus() + "]");
		System.out.println("expected = [" + ReadingStatus.COMPLETED.getLabel() + "]");
		
		//ステータスが「読了」の場合、読書完了日が必須
		if(ReadingStatus.COMPLETED.name().equals(form.getStatus())) {
			return form.getFinishDate() != null;
		}
		
		return true;
	}

}
