package it.uniroma3.siw.siweventsservice.validators;

import it.uniroma3.siw.siweventsservice.models.Tool;
import it.uniroma3.siw.siweventsservice.services.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ToolValidator implements Validator {

	@Autowired
	private ToolService toolService;

	/**
	 * Can this {@link Validator} {@link #validate(Object, Errors) validate}
	 * instances of the supplied {@code clazz}?
	 * <p>This method is <i>typically</i> implemented like so:
	 * <pre class="code">return Foo.class.isAssignableFrom(clazz);</pre>
	 * (Where {@code Foo} is the class (or superclass) of the actual
	 * object instance that is to be {@link #validate(Object, Errors) validated}.)
	 *
	 * @param clazz the {@link Class} that this {@link Validator} is
	 *              being asked if it can {@link #validate(Object, Errors) validate}
	 * @return {@code true} if this {@link Validator} can indeed
	 * {@link #validate(Object, Errors) validate} instances of the
	 * supplied {@code clazz}
	 */
	@Override
	public boolean supports (Class<?> clazz) {
		return Tool.class.equals(clazz);
	}

	/**
	 * Validate the supplied {@code target} object, which must be
	 * of a {@link Class} for which the {@link #supports(Class)} method
	 * typically has (or would) return {@code true}.
	 * <p>The supplied {@link Errors errors} instance can be used to report
	 * any resulting validation errors.
	 *
	 * @param target the object that is to be validated
	 * @param errors contextual state about the validation process
	 */
	@Override
	public void validate (Object target, Errors errors) {
		if (this.toolService.hasDuplicate((Tool) target)){
			errors.reject("tool.duplicate", "duplicate tool");
		}
	}
}
