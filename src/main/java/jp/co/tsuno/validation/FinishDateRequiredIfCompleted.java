package jp.co.tsuno.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FinishDateRequiredIfCompletedValidator.class)
public @interface FinishDateRequiredIfCompleted {
	String message() default "ステータスが完了の場合、読書完了日を入力してください。";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default {};
}
